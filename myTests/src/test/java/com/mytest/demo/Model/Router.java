package com.mytest.demo.Model;import java.io.Serializable;import java.util.Date;/** * 测试用的实体类 */public class Router implements Serializable {    private String routerId;    private String routerType;    private String routerVal;    private String routerBak;    private Date lastTime;    public String getRouterId() {        return routerId;    }    public void setRouterId(String routerId) {        this.routerId = routerId;    }    public String getRouterType() {        return routerType;    }    public void setRouterType(String routerType) {        this.routerType = routerType;    }    public String getRouterVal() {        return routerVal;    }    public void setRouterVal(String routerVal) {        this.routerVal = routerVal;    }    public String getRouterBak() {        return routerBak;    }    public void setRouterBak(String routerBak) {        this.routerBak = routerBak;    }    public Date getLastTime() {        return lastTime;    }    public void setLastTime(Date lastTime) {        this.lastTime = lastTime;    }}