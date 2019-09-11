package com.mytest.demo;

import com.mytest.demo.Model.B;
import com.mytest.demo.Model.MO;

public class ChildBuilderB extends FactoryBuilder{

    private MO mo;

    public ChildBuilderB(B b){
        this.mo = b;
    }

    public void doSomeThing() {
        mo.showName();
    }

}