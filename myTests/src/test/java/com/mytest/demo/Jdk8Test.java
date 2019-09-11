package com.mytest.demo;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.mytest.demo.Model.A;
import com.mytest.demo.Model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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

	//测试stream中的groupingBy方法，很实用
	@Test
	public void testGroup(){
		Car motor1 = new Car("zhangsan", "beijing", "10");
		Car motor2 = new Car("zhangsan", "beijing", "20");
		Car motor3 = new Car("lisi", "shanghai", "30");
		List<Car> list = new ArrayList<Car>();
		list.add(motor1);
		list.add(motor2);
		list.add(motor3);
		Map<String, List<Car>> collect = list.stream().collect(Collectors.groupingBy(o -> groupFilter(o)));
		System.out.println(collect);
		Map<String, Long> size = list.stream().collect(Collectors.groupingBy(o -> groupFilter(o),Collectors.counting()));
		System.out.println(size);
	}

	public String groupFilter(Car car){
		return car.getName() + "&&" + car.getMadeCountry();
	}

	@Test
	public void testFactory(){
		FactoryBuilder factoryBuilder = new ChildBuilderA(new A());
		factoryBuilder.doSomeThing();
	}

}

