package com.mytest.demo;

import com.mytest.demo.Model.Order;
import com.mytest.demo.Model.OrderDetail;
import com.mytest.demo.Model.Orders;
import com.mytest.demo.utils.TableUtil;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class IOTest {
	@Test
	public void BioTest() throws IOException {
		Date beginTime = new Date();
		for (int i = 0; i < 20; i++) {
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
		System.out.printf("共计花费时间：%s\n", (endTime.getTime() - beginTime.getTime()));
		
	}
	
	@Test
	public void NioTest() throws IOException {
		Date beginTime = new Date();
		for (int i = 0; i < 20; i++) {
			byte[] data = Files.readAllBytes(Paths.get("/Users/wangjiangqz/Documents/es_tables"));
			System.out.println(new String(data, StandardCharsets.UTF_8));
		}
		Date endTime = new Date();
		System.out.printf("共计花费时间：%s\n", (endTime.getTime() - beginTime.getTime()));
		
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
		System.out.println(code("12082679"));
		
	}
	
	@Autowired
	private DataSource druidDataSource;
	
	/**
	 * 用来修复买券订单，券码缺失的bug
	 *
	 * @throws Exception
	 */
	@Test
	public void getCode() throws Exception {
		FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/sql.sql", true);
		ResultSetHandler<List<Orders>> resultSetHandler = new BeanListHandler<>(Orders.class);
		QueryRunner queryRunner = new QueryRunner(druidDataSource);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");
		stringBuilder.append("o.order_id as orderId, o.is_sale_data as isSaleData,o.activity_id as activityId,o.act_group_id as actGroupId,o.user_id as userId,o.total_fee as totalFee,o.act_item_id as actItemId,g.groupon_code as grouponCode,o.order_code as orderCode,o.order_time as orderTime");
		stringBuilder.append(" FROM orders o LEFT JOIN groupon g ON o.order_id = g.order_id \n" +
				"WHERE\n" +
				"\to.act_item_id = 'A0219062611320101758' AND o.order_id not in  ('O08190629124924OZVXS') \n" +
				"\tAND o.`status` = 6 \t\n" +
				"ORDER BY\n" +
				"\to.user_id,o.order_id DESC");
		String sql = stringBuilder.toString();
		List<Orders> list = queryRunner.query(sql, resultSetHandler);
		StringBuilder ioBuilder = new StringBuilder();
		for (int i = 0; i < list.size(); i += 2) {
			Orders o1 = list.get(i + 1);
			if (StringUtils.hasText(o1.getGrouponCode())) {
				continue;
			}
			if ("0".equalsIgnoreCase(o1.getIsSaleData())) {
				System.out.println(o1.getOrderId() + "----");
				continue;
				
			}
			Orders o = list.get(i);
			if (StringUtils.hasText(o.getGrouponCode())) {
				continue;
			}
			if ("1".equalsIgnoreCase(o.getIsSaleData())) {
				System.out.println(o.getOrderId() + "====");
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
	
	@Test
	public void insertGrouponCode() throws Exception {
		FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/sql.sql", true);
		ResultSetHandler<List<Order>> resultSetHandler = new BeanListHandler<>(Order.class);
		QueryRunner queryRunner = new QueryRunner(druidDataSource);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT\n" +
				"\torder_id AS orderId,\n" +
				"\torder_code AS orderCode,\n" +
				"\tuser_id AS userId,\n" +
				"\tactivity_id AS activityId,\n" +
				"\tact_group_id AS actGroupId,\n" +
				"\trealname,\n" +
				"\tmobile,\n" +
				"\temail,\n" +
				"\tremark,\n" +
				"\torder_time AS orderTime,\n" +
				"\tpay_time AS payTime,\n" +
				"\tip,\n" +
				"\tadult_cnt AS adultCnt,\n" +
				"\tchild_cnt AS childCnt,\n" +
				"\ttotal_fee AS totleFee,\n" +
				"\tcoupon_fee AS couponFee,\n" +
				"\ttuan_discount_fee AS tuanDiscountFee,\n" +
				"\tpaid_fee AS paidFee,\n" +
				"\tcost_fee AS costFee,\n" +
				"\tact_item_id AS actItemId,\n" +
				"\tis_candidate AS isCandidate,\n" +
				"\tcoupon_item_id AS couponItemId,\n" +
				"\torder_origin AS orderOrigin,\n" +
				"\torder_channel AS orderChannel,\n" +
				"\tfrom_uid AS fromUid,\n" +
				"\trefer,\n" +
				"\twx_from AS wxFrom,\n" +
				"\tfromclient,\n" +
				"\tfromguid,\n" +
				"\tconfirm_time AS confirmTime,\n" +
				"\tverify_time AS verifyTime,\n" +
				"\tdept_id AS deptId,\n" +
				"\tteam_id AS teamId,\n" +
				"\teditor_staff_id AS editorStaffId,\n" +
				"\tbd_staff_id AS bdStaffId,\n" +
				"\tcs_staff_id AS csStaffId,\n" +
				"\tis_selfbz AS isSelfbz,\n" +
				"\tbcity_id AS bcityId,\n" +
				"\tact_cancellation_pid AS actCancellationPid,\n" +
				"\tcan_refund AS canRefund,\n" +
				"\ttuan_cost_type AS tuanCostType,\n" +
				"\tfrom_uid_credit_is_sent AS fromUidCreditIsSent,\n" +
				"\ttransfer_cnt AS transferCnt,\n" +
				"\tuser_addr_id AS userAddrId,\n" +
				"\tcomment_invited AS commentInvited,\n" +
				"\tcomplete_flg AS completeFlg,\n" +
				"\tbroker_fee_rate AS brokerFeeRate,\n" +
				"\tcontract_subject AS contractSubject,\n" +
				"\tccard_item_id AS ccardItemId,\n" +
				"\tccard_act_id AS ccardActId,\n" +
				"\tccard_deduction_cnt AS ccardDeductionCnt,\n" +
				"\tccard_deduction_fee AS ccardDeductionFee,\n" +
				"\tccard_offset_fee AS ccardOffsetFee,\n" +
				"\tis_booking AS isBooking,\n" +
				"\tis_baopiao AS isBaopiao,\n" +
				"\tpresell_type AS presellType,\n" +
				"\tpunchin_type AS punchinType,\n" +
				"\tneed_settlement AS needSettlement,\n" +
				"\tgroupon_code AS grouponCode,\n" +
				"\tdisplay_client AS displayClient,\n" +
				"\torder_type AS orderType,\n" +
				"\tis_sale_data AS isSaleData,\n" +
				"\tpresell_deduction_fee AS presellDeductionFee,\n" +
				"\tSTATUS,\n" +
				"\tsettlement_status AS settlementStatus,\n" +
				"\tbroker_settlement_status AS brokerSettlementStatus,\n" +
				"\trefund_status AS refundStatus,\n" +
				"\tcomment_status AS commentStatus,\n" +
				"\tcreate_person AS createPerson,\n" +
				"\tcreate_date AS createDate,\n" +
				"\tupdate_person AS updatePerson,\n" +
				"\tupdate_date AS updateDate,\n" +
				"\tversion \n" +
				"FROM\n" +
				"\torders \n" +
				"WHERE\n" +
				"\tactivity_id = 'A0720011617470001478' \n" +
				"\tAND `status` = 6 \n" +
				"\tAND is_sale_data = 1 \n" +
				"\tAND order_id NOT IN ( SELECT order_id FROM groupon ) \n" +
				"ORDER BY\n" +
				"\torder_id DESC");
		String sql = stringBuilder.toString();
		List<Order> list = queryRunner.query(sql, resultSetHandler);
		StringBuilder ioBuilder = new StringBuilder();
		List<String> orderIds = new ArrayList<>();
		Map<String,String> orderIdMap = new HashMap<>();
		for (Order o : list) {
			/*String grouponOrderId = o.getOrderId();
			String code = code(o.getOrderCode().toString());
			ioBuilder.append("INSERT INTO `maitao`.`groupon` (`groupon_code`,`activity_id`,`act_group_id`,`groupon_type`,`partner_id`,`act_item_id`,`act_price_id`,`price`,`order_id`,`groupon_order_id`,`booking_order_id`,`user_id`,`get_at`,`verify_person`,`verify_at`,`status`) VALUES ('");
			ioBuilder.append(code).append("','")
					.append(o.getActivityId())
					.append("','")
					.append(o.getActGroupId())
					.append("',0,'O0919052910383201586','")
					.append(o.getActItemId())
					.append("','")
					.append("A0419062611320101759")
					.append("','")
					.append(o.getTotalFee())
					.append("','")
					.append(o.getOrderId())
					.append("','")
					.append(grouponOrderId)
					.append("',NULL,'")
					.append(o.getUserId())
					.append("','")
					.append(o.getOrderTime().toString())
					.append("',NULL,NULL,'0');\n");*/
			
			orderIds.add(o.getOrderId());
			String orderId = TableUtil.genPK("010", "orders");
			ioBuilder.append("INSERT INTO `maitao`.`orders`(`order_id`, `order_code`, `user_id`, `activity_id`, `act_group_id`, `realname`, `mobile`, `email`, `remark`, `order_time`, `pay_time`, `ip`, `adult_cnt`, `child_cnt`, `total_fee`, `coupon_fee`, `tuan_discount_fee`, `paid_fee`, `cost_fee`, `act_item_id`, `is_candidate`, `coupon_item_id`, `order_origin`, `order_channel`, `from_uid`, `refer`, `wx_from`, `fromclient`, `fromguid`, `confirm_time`, `verify_time`, `dept_id`, `team_id`, `editor_staff_id`, `bd_staff_id`, `cs_staff_id`, `is_selfbz`, `bcity_id`, `act_cancellation_pid`, `can_refund`, `tuan_cost_type`, `from_uid_credit_is_sent`, `transfer_cnt`, `user_addr_id`, `comment_invited`, `complete_flg`, `broker_fee_rate`, `contract_subject`, `ccard_item_id`, `ccard_act_id`, `ccard_deduction_cnt`, `ccard_deduction_fee`, `ccard_offset_fee`, `is_booking`, `is_baopiao`, `presell_type`, `punchin_type`, `need_settlement`, `groupon_code`, `display_client`, `order_type`, `is_sale_data`, `presell_deduction_fee`, `status`, `settlement_status`, `broker_settlement_status`, `refund_status`, `comment_status`, `create_person`, `create_date`, `update_person`, `update_date`, `version`) VALUES (");
			ioBuilder.append("'").append(orderId).append("',");
			//修改ordercode，数据库自增
			ioBuilder.append(0).append(",");
			ioBuilder.append("'").append(o.getUserId()).append("',");
			ioBuilder.append("'").append(o.getActivityId()).append("',");
			ioBuilder.append("'").append(o.getActGroupId()).append("',");
			ioBuilder.append("'").append(o.getRealname()).append("',");
			ioBuilder.append("'").append(o.getMobile()).append("',");
			ioBuilder.append("'").append(o.getEmail()).append("',");
			ioBuilder.append("'").append(o.getRemark()).append("',");
			ioBuilder.append(o.getOrderTime()).append(",");
			ioBuilder.append(o.getPayTime()).append(",");
			ioBuilder.append("'").append(o.getIp()).append("',");
			ioBuilder.append("'").append(o.getAdultCnt()).append("',");
			ioBuilder.append("'").append(o.getChildCnt()).append("',");
			ioBuilder.append(o.getTotalFee()).append(",");
			ioBuilder.append(o.getCouponFee()).append(",");
			ioBuilder.append(o.getTuanDiscountFee()).append(",");
			ioBuilder.append(o.getPaidFee()).append(",");
			ioBuilder.append(o.getCostFee()).append(",");
			ioBuilder.append("'").append(o.getActItemId()).append("',");
			ioBuilder.append("'").append(o.getIsCandidate()).append("',");
			//修改coupon_item_id
			ioBuilder.append("'").append("").append("',");
			ioBuilder.append("'").append(o.getOrderOrigin()).append("',");
			ioBuilder.append("'").append(o.getOrderChannel()).append("',");
			ioBuilder.append("'").append(o.getFromUid()).append("',");
			ioBuilder.append("'").append(o.getRefer()).append("',");
			ioBuilder.append("'").append(o.getWxFrom()).append("',");
			ioBuilder.append("'").append(o.getFromclient()).append("',");
			ioBuilder.append("'").append(o.getFromUid()).append("',");
			ioBuilder.append(o.getConfirmTime()).append(",");
			ioBuilder.append(o.getVerifyTime()).append(",");
			ioBuilder.append("'").append(o.getDeptId()).append("',");
			ioBuilder.append("'").append(o.getTeamId()).append("',");
			ioBuilder.append("'").append(o.getEditorStaffId()).append("',");
			ioBuilder.append("'").append(o.getBdStaffId()).append("',");
			ioBuilder.append("'").append(o.getCsStaffId()).append("',");
			ioBuilder.append("'").append(o.getIsSelfbz()).append("',");
			ioBuilder.append("'").append(o.getBcityId()).append("',");
			ioBuilder.append("'").append(o.getActCancellationPid()).append("',");
			ioBuilder.append("'").append(o.getCanRefund()).append("',");
			ioBuilder.append("'").append(o.getTuanCostType()).append("',");
			//修改from_uid_credit_is_sent ： 邀请人积分是否发放
			ioBuilder.append("'").append(1).append("',");
			ioBuilder.append("'").append(o.getTransferCnt()).append("',");
			ioBuilder.append("'").append(o.getUserAddrId()).append("',");
			ioBuilder.append("'").append(o.getCommentInvited()).append("',");
			ioBuilder.append("'").append(o.getCompleteFlg()).append("',");
			ioBuilder.append("'").append(o.getBrokerFeeRate()).append("',");
			ioBuilder.append("'").append(o.getContractSubject()).append("',");
			ioBuilder.append("'").append(o.getCcardItemId()).append("',");
			ioBuilder.append("'").append(o.getCcardActId()).append("',");
			ioBuilder.append("'").append(o.getCcardDeductionCnt()).append("',");
			ioBuilder.append("'").append(o.getCcardDeductionFee()).append("',");
			ioBuilder.append("'").append(o.getCcardOffsetFee()).append("',");
			ioBuilder.append("'").append(o.getIsBooking()).append("',");
			ioBuilder.append("'").append(o.getIsBaopiao()).append("',");
			ioBuilder.append("'").append(o.getPresellType()).append("',");
			ioBuilder.append("'").append(o.getPunchinType()).append("',");
			//修改need_settlement
			ioBuilder.append("'").append(1).append("',");
			ioBuilder.append("'").append(o.getGrouponCode()).append("',");
			ioBuilder.append("'").append(1).append("',");
			ioBuilder.append("'").append(o.getOrderType()).append("',");
			//修改is_sale_data
			ioBuilder.append("'").append(0).append("',");
			ioBuilder.append("'").append(o.getPresellDeductionFee()).append("',");
			ioBuilder.append("'").append(o.getStatus()).append("',");
			ioBuilder.append("'").append(o.getSettlementStatus()).append("',");
			ioBuilder.append("'").append(o.getBrokerSettlementStatus()).append("',");
			ioBuilder.append("'").append(o.getRefundStatus()).append("',");
			ioBuilder.append("'").append(o.getCommentStatus()).append("',");
			ioBuilder.append("'").append(o.getCreatePerson()).append("',");
			ioBuilder.append(o.getCreateDate()).append("");
			ioBuilder.append("'").append(o.getUpdatePerson()).append("',");
			ioBuilder.append(o.getUpdatePerson()).append(",");
			ioBuilder.append("'").append(o.getVersion()).append("';\n");
			
			//存储新数据和子订单的主键关系
			orderIdMap.put(o.getOrderId(),orderId);
		}
		
		StringBuilder stringBuilder1 = new StringBuilder();
		stringBuilder1.append("\n" +
				"SELECT order_detail_id as orderDetailId,\n" +
				"order_id as orderId,\n" +
				"act_price_id as actPriceId,\n" +
				"order_qty as orderQty,\n" +
				"status,\n" +
				"create_person as createPerson,\n" +
				"create_date as createDate,\n" +
				"update_person as updatePerson,\n" +
				"update_date as updateDate\n" +
				"from order_detail where status = 0 and order_id in (");
		for (String orderId : orderIds){
			stringBuilder1.append("'").append(orderId).append("',");
		}
		stringBuilder1.append("'');");
		ResultSetHandler<List<OrderDetail>> resultSetHandlerDetail = new BeanListHandler<>(OrderDetail.class);
		List<OrderDetail> detailList = queryRunner.query(stringBuilder1.toString(), resultSetHandlerDetail);
		for (OrderDetail detail : detailList){
			ioBuilder.append("INSERT INTO `maitao`.`order_detail`(`order_detail_id`, `order_id`, `act_price_id`, `order_qty`, `status`, `create_person`, `create_date`, `update_person`, `update_date`) VALUES (");
			ioBuilder.append("'").append(TableUtil.genPK("020", "order_detail")).append("',");
			ioBuilder.append("'").append(orderIdMap.get(detail.getOrderId())).append("',");
			ioBuilder.append("'").append(detail.getActPriceId()).append("',");
			ioBuilder.append("'").append(detail.getOrderQty()).append("',");
			ioBuilder.append("'").append(detail.getStatus()).append("',");
			ioBuilder.append("'").append(detail.getCreatePerson()).append("',");
			ioBuilder.append("'").append(detail.getCreateDate()).append("',");
			ioBuilder.append("'").append(detail.getUpdatePerson()).append("',");
			ioBuilder.append("'").append(detail.getUpdateDate()).append("')\n");
		}
		

		fileWriter.write(ioBuilder.toString());
		fileWriter.close();
	}
	
	/**
	 * 用来修复券表中买券订单编号与主订单编号相同的bug
	 *
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
		List<Orders> list = queryRunner.query(sql, resultSetHandler);
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
	 *
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
		List<Orders> list = queryRunner.query(sql, resultSetHandler);
		StringBuilder ioBuilder = new StringBuilder();
		for (Orders o : list) {
			StringBuilder grouponCodes = new StringBuilder();
			grouponCodes.append("SELECT o.groupon_code from orders o where o.order_id = '" + o.getOrderId() + "' AND o.order_type =11;\n");
			List<Object[]> grouponCodesForUpdate = queryRunner.query(grouponCodes.toString(), resultSet);
			System.out.println(grouponCodes.toString());
			if (grouponCodesForUpdate.size() > 0 && o.getGrouponCode().equals(grouponCodesForUpdate.get(0)[0])) {
				continue;
			}
			ioBuilder.append("UPDATE orders set groupon_code = '" + o.getGrouponCode() + "' where order_id = '" + o.getOrderId() + "';\n");
		}
		fileWriter.write(ioBuilder.toString());
		fileWriter.close();
	}
	
}
