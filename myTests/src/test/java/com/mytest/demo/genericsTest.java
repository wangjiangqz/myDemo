package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//泛型的测试类
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class genericsTest
{
	public <T> String showValue(T t){
		return t.getClass().getSimpleName().toString();
	}

	@Test
	public void testShow(){
		Integer x = 9988;
		double y = 3333.3;
		String z = "i love u";
		System.out.println(showValue(x));
		System.out.println(showValue(y));
		System.out.println(showValue(z));
	}

	public <T extends Comparable> T findMax(T[] t){
		if (t.length > 0){
			T max = t[0];
			for (T o : t){
				if (o.compareTo(max) > 0){
					max = o;
				}
			}
			return max;
		}else
			return null;
	}

	@Test
	public void testMax(){
		Integer[] ints = {1,5,7,3,9,44};
		Double[] doubles = {1.3,2.6,99.3,42.3,88.5};
		System.out.println(findMax(ints));
		System.out.println(findMax(doubles));
	}
}

