package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class StaticMapTest
{
    @Test
    public void haveFun(){
        StaticMap.setMap("test1","your father");
        HashMap map = StaticMap.getMap();
        if (map.size() > 0 ){
            map.entrySet().stream()
                    .map(o -> ((Map.Entry)o).getKey() + ":" + ((Map.Entry) o).getValue().toString())
                    .forEach(System.out::println);
        }

        if (map.size() >0){
            Set<Map.Entry> set = map.entrySet();
            for (Map.Entry e : set){
                System.out.println(e.getKey());
                System.out.println(e.getValue());

            }
        }

    }


}
