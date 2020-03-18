package com.mytest.demo;

import com.mytest.demo.Model.Car;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        methodProxy.invokeSuper(o, objects);
        return null;
    }


    @Test
    public void testCglib() {
        //初始化代理类
        CglibProxy cglibProxy = new CglibProxy();
        //cglib加强器
        Enhancer enhancer = new Enhancer();
        //设置被代理类，这里被代理的是Car
        enhancer.setSuperclass(Car.class);
        //设置回调（代理）
        enhancer.setCallback(cglibProxy);
        //通过create创建被代理的对象
        Car proxy = (Car) enhancer.create();
        //调用被代理对象的方法
        proxy.setName("丰田");
        proxy.setMadeCountry("日本");
        proxy.setPrice("500,000");
        proxy.show();
    }

}
