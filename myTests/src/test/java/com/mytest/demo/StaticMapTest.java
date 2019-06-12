package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class StaticMapTest
{
    @Test
    public void haveFun(){
        StaticMap.setMap("test1","your father");
        HashMap map = StaticMap.getMap();
        if (map.size() > 0 ){
            System.out.println(StaticMap.getByKey("test1"));
        }

    }


}
