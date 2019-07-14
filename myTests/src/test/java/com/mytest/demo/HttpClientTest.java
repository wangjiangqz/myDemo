package com.mytest.demo;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class)
public class HttpClientTest
{
    @Autowired
    private DataSource druidDataSource;

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Test
    public void httpTest(){
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        ArrayListHandler resultSetHandler = new ArrayListHandler();
        String sql = "select order_id from orders order by order_id desc limit ? , ?";
        Integer begin = 0;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        try {
            while (true){
                List<Object[]> objects = queryRunner.query(sql, resultSetHandler,begin,begin + 100);
                if (objects.size() == 0){
                    break;
                }else {
                    begin += 100;
                }
                System.out.println("当前进度 : " + begin);
                for (Object[] order : objects){
                    if (order.length > 0){
                        Future<?> submit = fixedThreadPool.submit(() -> {
                            System.out.println("orderId : " + order[0].toString());
                            doGet(order[0].toString());
                            System.out.printf("pool name - %s - ", Thread.currentThread().getName());
                        });
                        System.out.printf("submit#get method return - %s\n", submit.get());

                    }
                }
            }
            fixedThreadPool.shutdown();
            try {
                if (httpClient != null){
                    httpClient.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


}


    public void doGet(String orderId){

        URI uri = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("orderId",orderId));
            uri = new URIBuilder().setScheme("http")
                    .setHost("192.168.1.23")
                    .setPort(8014)
                    .setPath("/rest_order/transferAndSaveToEs")
                    .setParameters(params)
                    .build();

        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        System.out.println("URI : " + uri + "~优秀~");

        CloseableHttpResponse response = null;
        try{
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(true)
                    .build();

            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);

            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null){
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (response != null){
                    response.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
