package com.example.demo;

import com.example.demo.dao.stuDao;
import com.example.demo.entity.stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wangjiang on 2019/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = SpringMVCApplication.class) /*指定Springboot启动类启动*/
public class TestMybatis {

    @Autowired
    private stuDao studao;

    @Test
    public void test(){
        List<stu> list = studao.findList();
        System.out.println(list);
    }


}
