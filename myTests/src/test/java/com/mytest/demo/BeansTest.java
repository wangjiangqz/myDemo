package com.mytest.demo;

import com.alibaba.fastjson.JSONObject;
import com.mytest.demo.Model.Router;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//BeanUtils的测试
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class BeansTest
{
    private Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.1.23:3306/maitao";
        String username = "root";
        String password = "maitao108dev";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Test
    public void copyTest() throws Exception{
        //获取数据库连接
        Connection con = getConn();
        //获取数据库连接的元数据
        DatabaseMetaData databaseMetaData = con.getMetaData();
        //获取数据库连接的主版本和副版本
        String productName = databaseMetaData.getDatabaseProductName();
        int majorVersion = databaseMetaData.getDatabaseMajorVersion();
        int minorVersion = databaseMetaData.getDatabaseMinorVersion();
        System.out.println("数据库版本为："+ productName + " " + majorVersion + "." + minorVersion);

        String sql = "SELECT * from router r ";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet set = statement.executeQuery();

        //获取查询结果的元数据
        ResultSetMetaData resultSetMetaData = set.getMetaData();
        //获取查询结果
        List<Object> resultList = new ArrayList<>();
        //获取返回结果的列的数目
        int count = resultSetMetaData.getColumnCount();
        //循环返回结果，赋值给实体类，塞值到返回结果list
        while (set.next()){
            Router router = new Router();
            for (int i = 1;i <= count;i++){
                String cloName = resultSetMetaData.getColumnName(i);
                Object value = set.getObject(cloName);
                BeanUtils.setProperty(router,changeType2(cloName),value);

            }
            resultList.add(router);
        }
        //打印返回结果
        resultList.stream()
                .map(o-> JSONObject.toJSON(o))
                .forEach(o->System.out.println("查询结果如下：" + o));

    }

    /**
     * author wj
     * @param string
     * 采用转换成char数组的方式转换成驼峰型的字符串
     * @return
     */
    private String changeType1(String string){
        if (string != null && !string.equals("")){
            char[] chars = string.toCharArray();
            for (int i = 0;i < chars.length;i++){
                if (chars[i] == '_' && i < chars.length - 1){
                    char x = chars[i+1];
                   int ix = (int)x;
                    if(ix >= 97 && ix <= 122){
                        ix -= 32;
                        char y = (char)ix;
                        chars[i+1] = y;
                    }
                }
            }
            string = String.valueOf(chars).replaceAll("_","");
            return string;
        }else {
            return "";
        }
    }

    /**
     * author wj
     * @param string
     * 采用拆分成字符串数组的方式转换成驼峰型的字符串
     * @return
     */
    private String changeType2(String string){
        if (string != null && !string.equals("")){
            String[] strings = string.split("_");
            if (strings.length > 1){
                for (int i=1;i<strings.length;i++){
                    strings[i] = strings[i].substring(0,1).toUpperCase() + strings[i].substring(1);
                }
            }
            StringBuilder builder = new StringBuilder();
            for (String str : strings){
                builder.append(str);
            }
            return builder.toString();
        }else {
            return "";
        }
    }


    @Test
    public void changeTypeTest(){
        System.out.println(changeType2("ee_rr"));

    }
}
