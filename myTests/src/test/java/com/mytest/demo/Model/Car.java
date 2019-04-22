package com.mytest.demo.Model;

//汽车类，用于测试原型模式
public class Car implements Cloneable
{
    private String name;
    private String madeCountry;
    private String price;

    public Car clone() throws CloneNotSupportedException{
        System.out.println("复制汽车成功。");
        return (Car)super.clone();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMadeCountry()
    {
        return madeCountry;
    }

    public void setMadeCountry(String madeCountry)
    {
        this.madeCountry = madeCountry;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void show(){
        System.out.println("汽车信息如下：品牌" + name +",产地"+madeCountry +",价格"+price+"元");
    }

    public Car(String name,String madeCountry,String price){
        this.name = name;
        this.madeCountry = madeCountry;
        this.price = price;
        System.out.println("汽车创建成功。");

    }
}
