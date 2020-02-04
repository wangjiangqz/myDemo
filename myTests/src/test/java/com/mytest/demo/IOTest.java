package com.mytest.demo;

import com.mytest.demo.Model.Orders;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

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

    public static String code(String coco) {
        int[] map = new int[]{3, 7, 9, 4, 1, 6, 0, 5, 2, 8};
        int orderCode = Integer.valueOf(coco);
        int[] digits = new int[8];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = map[orderCode % 10];
            orderCode = orderCode / 10;
        }
        StringBuffer sb = new StringBuffer();
        sb.append((int) (Math.random() * 10));
        for (int i = 0; i < digits.length / 2; i++) {
            sb.append(digits[i]);
        }
        sb.append((int) (Math.random() * 10));
        for (int i = digits.length / 2; i < digits.length; i++) {
            sb.append(digits[i]);
        }
        sb.append((int) (Math.random() * 10));
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(code("9607055"));

    }

    @Autowired
    private DataSource druidDataSource;

    /**
     * 用来修复买券订单，券码缺失的bug
     * @throws Exception
     */
    @Test
    public void getCode() throws Exception{
        FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/sql.sql",true);
        ResultSetHandler<List<Orders>> resultSetHandler = new BeanListHandler<>(Orders.class);
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("o.order_id as orderId, o.is_sale_data as isSaleData,o.activity_id as activityId,o.act_group_id as actGroupId,o.user_id as userId,o.total_fee as totalFee,o.act_item_id as actItemId,g.groupon_code as grouponCode,o.order_code as orderCode,o.order_time as orderTime");
        stringBuilder.append(" FROM orders o LEFT JOIN groupon g ON o.order_id = g.order_id \n" +
                "WHERE\n" +
                "\to.act_item_id = 'A0219062611320101758' AND o.order_id not in  ('O08190629124924OZVXS') \n"+
                "\tAND o.`status` = 6 \t\n" +
                "ORDER BY\n" +
                "\to.user_id,o.order_id DESC");
        String sql = stringBuilder.toString();
        List<Orders> list = queryRunner.query(sql,resultSetHandler);
        StringBuilder ioBuilder = new StringBuilder();
        for (int i=0;i<list.size();i+=2){
            Orders o1 = list.get(i+1);
            if (StringUtils.hasText(o1.getGrouponCode())){
                continue;
            }
            if ("0".equalsIgnoreCase(o1.getIsSaleData())){
                System.out.println(o1.getOrderId()+"----");
                continue;

            }
            Orders o = list.get(i);
            if (StringUtils.hasText(o.getGrouponCode())){
                continue;
            }
            if ("1".equalsIgnoreCase(o.getIsSaleData())){
                System.out.println(o.getOrderId()+"====");
                continue;

            }
            String grouponOrderId = o.getOrderId();
            String code = code(o1.getOrderCode());
            ioBuilder.append("INSERT INTO `maitao`.`groupon` (`groupon_code`,`activity_id`,`act_group_id`,`groupon_type`,`partner_id`,`act_item_id`,`act_price_id`,`price`,`order_id`,`groupon_order_id`,`booking_order_id`,`user_id`,`get_at`,`verify_person`,`verify_at`,`status`) VALUES ('");
            ioBuilder.append(code).append("','")
                    .append(o1.getActivityId())
                    .append("','")
                    .append(o1.getActGroupId())
                    .append("',0,'O0919052910383201586','")
                    .append(o1.getActItemId())
                    .append("','")
                    .append("A0419062611320101759")
                    .append("','")
                    .append(o1.getTotalFee())
                    .append("','")
                    .append(o1.getOrderId())
                    .append("','")
                    .append(grouponOrderId)
                    .append("',NULL,'")
                    .append(o1.getUserId())
                    .append("','")
                    .append(o1.getOrderTime().toString())
                    .append("',NULL,NULL,'0');\n");
        }
        fileWriter.write(ioBuilder.toString());
        fileWriter.close();
    }

    /**
     * 用来修复券表中买券订单编号与主订单编号相同的bug
     * @throws Exception
     */
    @Test
    public void changeCode() throws Exception {
        FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/changeCode.sql", true);
        ResultSetHandler<List<Orders>> resultSetHandler = new BeanListHandler<>(Orders.class);
        ArrayListHandler resultSet = new ArrayListHandler();
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT g.order_id as orderId,COUNT(g.order_id) as orderCount,g.user_id as userId,g.act_item_id as actItemId from groupon g where g.order_id = g.groupon_order_id and g.get_at > '2019-06-30 23:59:59' GROUP BY g.order_id order by g.order_id asc");
        String sql = stringBuilder.toString();
        List<Orders> list = queryRunner.query(sql,resultSetHandler);
        StringBuilder ioBuilder = new StringBuilder();
        for (Orders o : list) {
            StringBuilder grouponIds = new StringBuilder();
            grouponIds.append("SELECT o.order_id from orders o where o.order_id > '" + o.getOrderId() + "' and o.act_item_id = '" + o.getActItemId() + "' and o.is_sale_data = '0' and o.user_id = '" + o.getUserId() + "' order by o.order_id limit " + o.getOrderCount());
            List<Object[]> grouponIdsForUpdate = queryRunner.query(grouponIds.toString(), resultSet);
            StringBuilder grouponCodes = new StringBuilder();
            grouponCodes.append("SELECT groupon_code from groupon where order_id = '" + o.getOrderId() + "'");
            List<Object[]> grouponCodesForUpdate = queryRunner.query(grouponCodes.toString(), resultSet);
            for (int i = 0; i < grouponIdsForUpdate.size(); i++) {
                ioBuilder.append("UPDATE groupon set groupon_order_id = '" + grouponIdsForUpdate.get(i)[0] + "' where groupon_code = '" + grouponCodesForUpdate.get(i)[0] + "';\n");
            }
        }
        fileWriter.write(ioBuilder.toString());
        fileWriter.close();
    }

    /**
     * 用来修复团购券酒店连住子订单券码不一致的bug
     * @throws Exception
     */
    @Test
    public void updateOrders() throws Exception {
        FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/updateOrders.sql", true);
        ResultSetHandler<List<Orders>> resultSetHandler = new BeanListHandler<>(Orders.class);
        ArrayListHandler resultSet = new ArrayListHandler();
        QueryRunner queryRunner = new QueryRunner(druidDataSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT\n" +
                "\tgroupon_code as grouponCode,\n" +
                "\tbooking_order_id as orderId\n" +
                "FROM\n" +
                "\tgroupon g \n" +
                "WHERE\n" +
                "\tg.order_id IN (\n" +
                "\tSELECT\n" +
                "\t\torder_id \n" +
                "\tFROM\n" +
                "\t\tgroupon \n" +
                "\tWHERE\n" +
                "\t\tgroupon_code IN (\n" +
                "\t\tSELECT\n" +
                "\t\t\tgroupon_code \n" +
                "\t\tFROM\n" +
                "\t\t\torders \n" +
                "\t\tWHERE\n" +
                "\t\t\torder_type = 11 \n" +
                "\t\t\tAND STATUS IN ( 1, 6, 8 ) \n" +
                "\t\t\tAND pay_time > '2019-05-01' \n" +
                "\t\t\tAND pay_time < '2019-08-01' \n" +
                "\t\t\tAND groupon_code IS NOT NULL \n" +
                "\t\t\tGROUP BY groupon_code HAVING count( * ) > 1 \n" +
                "\t\t) \n" +
                "\t) \n" +
                "\tAND g.booking_order_id is not NULL\n" +
                "\tAND g.groupon_code NOT IN (\n" +
                "\tSELECT\n" +
                "\t\tgroupon_code \n" +
                "\tFROM\n" +
                "\t\torders \n" +
                "\tWHERE\n" +
                "\t\torder_type = 11 \n" +
                "\t\tAND STATUS IN ( 1, 6, 8 ) \n" +
                "\t\tAND pay_time > '2019-05-01' \n" +
                "\t\tAND pay_time < '2019-08-01' \n" +
                "\t\tAND groupon_code IS NOT NULL \n" +
                "\t\tGROUP BY groupon_code HAVING count( * ) > 1 \n" +
                "\t)");
        String sql = stringBuilder.toString();
        List<Orders> list = queryRunner.query(sql,resultSetHandler);
        StringBuilder ioBuilder = new StringBuilder();
        for (Orders o : list) {
            StringBuilder grouponCodes = new StringBuilder();
            grouponCodes.append("SELECT o.groupon_code from orders o where o.order_id = '" + o.getOrderId() +"' AND o.order_type =11;\n");
            List<Object[]> grouponCodesForUpdate = queryRunner.query(grouponCodes.toString(), resultSet);
            System.out.println(grouponCodes.toString());
            if (grouponCodesForUpdate.size() >0 && o.getGrouponCode().equals(grouponCodesForUpdate.get(0)[0])){
                continue;
            }
            ioBuilder.append("UPDATE orders set groupon_code = '" + o.getGrouponCode() + "' where order_id = '" + o.getOrderId() + "';\n");
            }
        fileWriter.write(ioBuilder.toString());
        fileWriter.close();
    }

}