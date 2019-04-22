package com.mytest.demo.Model;

//motor建造的指导者
public class MotorDirector
{
    private MotorBuilder motorBuilder;

    public MotorDirector(MotorBuilder motorBuilder){
        this.motorBuilder = motorBuilder;
    }

    public Motor makeMotor(){
        motorBuilder.buildName();
        motorBuilder.buildMadeCuntry();
        motorBuilder.buildPrice();
        return  motorBuilder.getMotor();
    }
}
