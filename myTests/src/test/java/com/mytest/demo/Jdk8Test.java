package com.mytest.demo;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

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

		lists.forEach(o->{
			System.out.println(haveFun(String.valueOf(o.compareTo(50)>=0?o:0)));
		});

		lists.forEach(Jdk8Test::printTest);
		System.out.println("////////");

		IntSummaryStatistics statistics = lists.stream().sorted().filter(o->o%2 ==0).mapToInt((x)->x).summaryStatistics();
		System.out.println(statistics.getCount());
		System.out.println(statistics.getAverage());
		System.out.println(statistics.getMax());
		System.out.println(statistics.getMin());
		System.out.println(statistics.getSum());


		Integer op = Optional.ofNullable(statistics.getMax()).filter(o->o>50).map(o->o*o).get();
		System.out.println("_______-------"+op);

	}


	@Test
	public void testReflectASM(){
		Cats cat = new Cats();
		MethodAccess methodAccess = MethodAccess.get(Cats.class);
		methodAccess.invoke(cat,"showCatName","lucy");
	}

	@Test
	public void testJDKreflect(){
		Cats cat = new Cats();
		try
		{
			Method method = cat.getClass().getMethod("showCatName",String.class);
			method.invoke(cat,"Lucky");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public String haveFun(String s){
		s = s+" for fun";
		return s;
	}

	public static void printTest(Object o){
		System.out.println(o.toString());

	}

}

