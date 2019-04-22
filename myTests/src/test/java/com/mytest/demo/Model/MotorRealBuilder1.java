package com.mytest.demo.Model;

//Motor建造者的实例1
public class MotorRealBuilder1 extends MotorBuilder
{
    @Override
    public void buildMadeCuntry()
    {
        motor.setMadeCountry("Japan");
    }

    @Override
    public void buildName()
    {
        motor.setName("Honda");
    }

    @Override
    public void buildPrice()
    {
        motor.setPrice("170000");
    }
}
