package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//个人对jdk8新特性的测试类
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class Jdk8Test
{
	//lambda表达式的测试方法
	@Test
	public void LambdaTest(){
		List<Integer> lists = new ArrayList<>();
		int m =0;
		do{
			Integer i = Integer.parseInt(new DecimalFormat("0").format(Math.random()*100));
			lists.add(i);
			m++;
		}while (m<10);

		lists.forEach(o ->System.out.print(o));

		lists.forEach(System.out::println);
	}



}

