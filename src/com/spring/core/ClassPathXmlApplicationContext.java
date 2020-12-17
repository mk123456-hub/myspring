package com.spring.core;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Administrator
 * @create 2020/12/17 0017 13:31
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private String configName;

    public ClassPathXmlApplicationContext(String configName) {
        this.configName = configName;
    }

    public ClassPathXmlApplicationContext() {
        this.configName="applicationContext.xml";
    }
     public Map<String,EntityBean> springXml() throws Exception{
        //1.创建解析器
         XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
         //2.读入xml文件
         InputStream in=ClassPathXmlApplicationContext.class.getClassLoader().getResourceAsStream(configName);
         pullParser.setInput(in,"utf-8");
         //基于事件机制编写xml解析，并且组装目标对象
         int eventType = pullParser.getEventType();
         Map<String,EntityBean> beans=null;
         EntityBean bean=null;
         while (eventType!=XmlPullParser.END_DOCUMENT){
             switch (eventType){
                 case XmlPullParser.START_DOCUMENT:
                     beans=new HashMap<String,EntityBean>();
                     break;
                     case XmlPullParser.START_TAG:
                         if("bean".equals(pullParser.getName())){
                             bean=new EntityBean();
                             bean.setId(pullParser.getAttributeValue(null,"id"));
                             bean.setClassName(pullParser.getAttributeValue(null,"class"));
                         }
                         if("property".equals(pullParser.getName())){
                             String attrName=pullParser.getAttributeValue(null,"name");
                             String attValue=pullParser.getAttributeValue(null,"value");
                             bean.getPropx().put(attrName,attValue);
                         }
                         break;
                         case XmlPullParser.END_TAG:
                             if("bean".equals(pullParser.getName())){
                                 beans.put(bean.getId(),bean);
                             }
                             break;
             }
             eventType=pullParser.next();
         }
         return beans;
     }
     public Map<String,Object> getIOC(Map<String,EntityBean> beansInfo) throws Exception{
        Map<String,Object> results=new HashMap<String, Object>();
        for(Map.Entry<String,EntityBean> beanInfo:beansInfo.entrySet()){
            String resultId=beanInfo.getKey();
            EntityBean bean=beanInfo.getValue();
            String className=bean.getClassName();
            Map<String,String> props=bean.getPropx();
            Class clazz=Class.forName(className);
            Object obj=clazz.newInstance();
            for(Map.Entry<String,String> prop:props.entrySet()){
                String propName=prop.getKey();
                String propValue=prop.getValue();
                StringBuilder builder=new StringBuilder("set");
                builder.append(propName.substring(0,1).toUpperCase());
                builder.append(propName.substring(1));
                String setterMethodName=builder.toString();
                Field field=clazz.getDeclaredField(propName);
                Method method=clazz.getDeclaredMethod(setterMethodName,field.getType());
                if("int".equals(field.getType().getName())){
                    method.invoke(obj,Integer.parseInt(propValue));
                }
                else if("java.lang.String".equals(field.getType().getName())){
                    method.invoke(obj,propValue);
                }
            }
            results.put(resultId,obj);
        }

        return results;
     }

//    public static void main(String[] args) throws Exception{
//        ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext();
//        Map<String,EntityBean> beans=ctx.springXml();
//        for(Map.Entry<String,EntityBean> entry:beans.entrySet()){
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//            System.out.println("**************************");
//        }
//    }
    @Override
    public Object getBean(String beanId) {
        Object obj=null;
        try {
            Map<String, EntityBean> beansInfo = springXml();
            Map<String,Object> results=getIOC(beansInfo);
            obj=results.get(beanId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }
}
