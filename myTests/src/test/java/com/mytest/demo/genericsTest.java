package com.mytest.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

	public void getFirst(List<?> list){
		System.out.println(list.get(0));
	}

	@Test
	public void testFirst(){
		List<String> list = new ArrayList<>();
		list.add("Java");
		list.add("Php");
		list.add("Ruby");
		getFirst(list);
		list = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		getFirst(list);
		list = list.stream().sorted().collect(Collectors.toList());
		getFirst(list);
	}
}

