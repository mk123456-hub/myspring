package com.spring.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Administrator
 * @create 2020/12/17 0017 13:36
 */
public class EntityBean {
    private String id;
    private String className;
    private Map<String,String> propx;

    public EntityBean() {
        propx=new HashMap<String,String>();
    }

    public EntityBean(String id, String className, Map<String, String> propx) {
        this.id = id;
        this.className = className;
        this.propx = propx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getPropx() {
        return propx;
    }

    public void setPropx(Map<String, String> propx) {
        this.propx = propx;
    }

    @Override
    public String toString() {
        return "EntityBean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", propx=" + propx +
                '}';
    }
}
