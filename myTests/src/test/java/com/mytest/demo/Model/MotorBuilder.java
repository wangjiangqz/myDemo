package com.mytest.demo.Model;

//摩托车制造的抽象类
public abstract class MotorBuilder
{
    protected Motor motor = new Motor();

    public abstract void buildName();

    public abstract void buildMadeCuntry();

    public abstract void buildPrice();

    public Motor getMotor()
    {
        return motor;
    }
}
