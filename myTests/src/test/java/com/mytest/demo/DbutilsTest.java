package com.mytest.demo;

import com.alibaba.fastjson.JSONObject;
import com.mytest.demo.Model.Columns;
import com.mytest.demo.Model.Router;
import com.mytest.demo.utils.Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//Dbutils的测试
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class DbutilsTest
{
    @Autowired
    private DataSource druidDataSource;

    private Connection getCon() throws SQLException{
        return druidDataSource.getConnection();
    }

    /**
     * 简单查询，测试QueryRunner
     * @throws SQLException
     */
    @Test
    public void testEasyQuery() throws SQLException{
        QueryRunner queryRunner = new QueryRunner(druidDataSource);

        String sql = "insert into router values(0,0,0,null,?)";
        //sql中的变量可以在这里替换
        int update = queryRunner.update(sql,new Date());
        System.out.println(update == 1 ? "执行成功！" : "执行失败哟~");

    }

    /**
     * 测试查询结果直接转换成对象的List
     * @throws SQLException
     */
    @Test
    public void testHandlerQuery() throws SQLException{
        ResultSetHandler<List<Columns>> stringSetHandler = new BeanListHandler<>(Columns.class);
        ResultSetHandler<List<Router>> resultSetHandler = new BeanListHandler<>(Router.class);
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        String colSql = "SHOW FULL COLUMNS from router";
        List<Columns> colnums = queryRunner.query(colSql,stringSetHandler);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        for (int i = 0;i<colnums.size();i++){
            stringBuilder.append(colnums.get(i).getField()).append(" AS ").append(Utils.changeType(colnums.get(i).getField()));
            if (i != colnums.size() -1){
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" FROM router where router_id < ?");
        String sql = stringBuilder.toString();
        List<Router> list = queryRunner.query(sql,resultSetHandler,10);
        list.stream().map(o-> JSONObject.toJSON(o)).forEach(System.out::println);
    }

    /**
     * 测试查询结果直接转换成List
     * @throws SQLException
     */
    @Test
    public void testNomalHandlerQuery() throws SQLException{
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        ArrayListHandler resultSetHandler = new ArrayListHandler();
        String sql = "select * from router where router_id < ?";
        List<Object[]> objects = queryRunner.query(sql, resultSetHandler,10);
        objects.stream().map(o-> JSONObject.toJSON(o)).forEach(System.out::println);
    }

    /**
     * 测试聚合函数结果
     * ScalarHandler:封装类似count、avg、max、min、sum等函数的执行结果
     * @throws SQLException
     */
    @Test
    public void testScalar() throws SQLException{
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        String sql = "select count(*) from router where 1 = 1 ";
        ScalarHandler scalarHandler = new ScalarHandler();
        Object object = queryRunner.query(sql,scalarHandler);
        System.out.println(object);

    }
}
