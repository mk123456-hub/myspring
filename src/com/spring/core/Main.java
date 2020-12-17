package com.spring.core;

/**
 * @Author Administrator
 * @create 2020/12/17 0017 11:58
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        Object zhao=ctx.getBean("zhao");
        System.out.println(zhao);
        Object qian=ctx.getBean("qian");
        System.out.println(qian);
        Object sun=ctx.getBean("sun");
        System.out.println(sun);
        Object hh=ctx.getBean("hr");
        System.out.println(hh);
    }
}
