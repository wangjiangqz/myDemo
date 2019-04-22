package com.mytest.demo.Model;

//摩托车类，用于测试创建者模式
public class Motor
{
    private String name;
    private String madeCountry;
    private String price;

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
        System.out.println("摩托车信息如下：品牌" + name +",产地"+madeCountry +",价格"+price+"元");
    }
}
