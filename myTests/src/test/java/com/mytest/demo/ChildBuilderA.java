package com.mytest.demo;

import com.mytest.demo.Model.A;
import com.mytest.demo.Model.MO;

public class ChildBuilderA extends FactoryBuilder{

    private MO mo;

    public ChildBuilderA(A a){
        this.mo = a;
    }

    public void doSomeThing() {
        mo.showName();
    }

}