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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = druidDataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "insert into router values(0,0,0,null,?)";
        //sql中的变量可以在这里替换
        int update = queryRunner.update(connection,sql,new Date());
        connection.commit();
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

    /**
     * 构建pojo的属性
     * @throws SQLException
     */
    @Test
    public void writeSomeThing() throws SQLException{
        ResultSetHandler<List<Columns>> stringSetHandler = new BeanListHandler<>(Columns.class);
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        String colSql = "SHOW FULL COLUMNS from mto_biz_ccard_item";
        List<Columns> colnums = queryRunner.query(colSql,stringSetHandler);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<colnums.size();i++){
            Columns coco = colnums.get(i);
            stringBuilder.append("\n").append("// ").append(coco.getComment()).append("\n");
            if ("PRI".equals(coco.getKey())){
                stringBuilder.append("@Id").append("\n");
            }
            stringBuilder.append("@Column(name = \"").append(coco.getField()).append("\")").append("\n");
            stringBuilder.append("private ");
            if (coco.getType().startsWith("int")){
                stringBuilder.append("Integer ");
            }else if (coco.getType().startsWith("bigin")){
                stringBuilder.append("Integer ");
            }else if (coco.getType().startsWith("date")){
                stringBuilder.append("Date ");
            }else if (coco.getType().startsWith("var")){
                stringBuilder.append("String ");
            }else if (coco.getType().startsWith("char")){
                stringBuilder.append("String ");
            }else if (coco.getType().startsWith("json")){
                stringBuilder.append("Json ");
            }else if (coco.getType().startsWith("tiny")){
                stringBuilder.append("Integer ");
            }else if (coco.getType().startsWith("decimal")){
                stringBuilder.append("BigDecimal ");
            }else if (coco.getType().startsWith("double")){
                stringBuilder.append("Double ");
            }else {
                stringBuilder.append("XXX ");
            }
            stringBuilder.append(Utils.changeType(coco.getField())).append(";\n\n");
        }

        System.out.println(stringBuilder.toString());
    }


    @Test
    public void writeToFile() throws Exception{
        String[] strings = {"mto_biz_booking","mto_biz_broker","mto_biz_ccard","mto_biz_comment","mto_biz_disney","mto_biz_groupon","mto_biz_hotel","mto_biz_member","mto_biz_normal","mto_biz_present","mto_biz_transfer","mto_biz_xet","mto_biz_ziwoyoo","mto_fav_coupon","mto_fav_presell","mto_fav_tuan","mto_main","mto_order_biz","mto_order_fav","mto_order_statis"};
        for (String s : Arrays.asList(strings)){
            String pathNameBefore = "/Users/wangjiangqz/Downloads/";
            String pathNameEnd = Utils.changeType(s).substring(0,1).toUpperCase() + Utils.changeType(s).substring(1) + "Repository";
            File file = new File(pathNameBefore + pathNameEnd + ".java");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package cn.com.maitao.repository.mysql.mto;\n\n");
            stringBuilder.append("import cn.com.maitao.entity.mysql.mto.")
                    .append(Utils.changeType(s).substring(0,1).toUpperCase() + Utils.changeType(s).substring(1)).append(";\n");
            stringBuilder.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
            stringBuilder.append("import org.springframework.stereotype.Repository;\n\n");

            stringBuilder.append("@Repository(\"").append(Utils.changeType(s)).append("Repository\")\n");
            stringBuilder.append("public interface ").append(pathNameEnd).append(" extends JpaRepository<")
                    .append(Utils.changeType(s).substring(0,1).toUpperCase() + Utils.changeType(s).substring(1)).append(", String> {\n\n");
            stringBuilder.append("}\n");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(stringBuilder.toString());
            bw.flush();
            bw.close();
        }
    }


}
