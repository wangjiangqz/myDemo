package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class IOTest
{
    @Test
    public void BioTest() throws IOException{
        Date beginTime = new Date();
        for (int i =0;i <20;i++) {
            Reader reader = new FileReader("/Users/wangjiangqz/Documents/es_tables");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }
            bufferedReader.close();
            reader.close();
            System.out.println(builder.toString());
        }
        Date endTime = new Date();
        System.out.printf("共计花费时间：%s\n",(endTime.getTime() - beginTime.getTime()));

    }

    @Test
    public void NioTest() throws IOException{
        Date beginTime = new Date();
        for (int i =0;i <20;i++) {
            byte[] data = Files.readAllBytes(Paths.get("/Users/wangjiangqz/Documents/es_tables"));
            System.out.println(new String(data, StandardCharsets.UTF_8));
        }
        Date endTime = new Date();
        System.out.printf("共计花费时间：%s\n",(endTime.getTime() - beginTime.getTime()));

    }


}
