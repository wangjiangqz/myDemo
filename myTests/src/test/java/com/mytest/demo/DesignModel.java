package com.mytest.demo;

import com.mytest.demo.Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//个人对设计模式的测试
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class DesignModel
{
    @Autowired
    MotorBuilder motorBuilder;
    @Test
    public void 原型模式() throws CloneNotSupportedException{
        Car car = new Car("Honda","Japan","170000");
        car.show();
        Car car1 = car.clone();
        car1.setName("Toyota");
        car1.setPrice("160000");
        car1.show();
    }

    @Test
    public void 建造者模式(){
        System.out.println(motorBuilder.getClass().getSimpleName());

        MotorBuilder motorBuilder = new MotorRealBuilder1();
        MotorDirector motorDirector = new MotorDirector(motorBuilder);
        Motor motor = motorDirector.makeMotor();
        motor.show();
    }
}
