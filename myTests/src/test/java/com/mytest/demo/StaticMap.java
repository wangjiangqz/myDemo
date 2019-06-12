package com.mytest.demo;

import java.util.HashMap;


public class StaticMap
{

    private final static HashMap<String,Object> map = new HashMap<>();

    public static HashMap getMap(){
        return map;
    }

    public static Object getByKey(String key){
        return map.get(key);
    }

    public static void setMap(String key,Object obj){
        map.put(key,obj);
    }

}
