
package com.mytest.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;

public class TableUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TableUtil.class);
	
	public static String genPK(String skey, String tableName) {
		if (!StringUtils.hasText(skey)) {
			LOGGER.error("服务器标识KEY不能为空！");
			return null;
		}
		if (!StringUtils.hasText(tableName)) {
			LOGGER.error("表名不能为空！");
			return null;
		}
		
		String pkCode = "O08";
		
		if (pkCode.length() < 2 || pkCode.length() > 3) {
			LOGGER.error("表： " + tableName + " 字典表定义错误，必需2位或3位的字符!");
			return null;
		}
		
		StringBuffer temp = new StringBuffer();
		temp.append(pkCode.toUpperCase());
		// 主键标识位 三位
		if (pkCode.length() == 2) {
			// 补充位数
			temp.append("0");
		}
		temp.append(com.mytest.demo.utils.DateUtil.getDFPatternPK().format(new Date()));
		temp.append(skey);
		temp.append(getPrimaryKeyNumber());
		return temp.toString();
	}
	
	
	private final static String[] SA = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private static int SI = 0;
	private static int SJ = 0;
	static long flgTime = System.currentTimeMillis();
	
	private synchronized static String getPrimaryKeyNumber() {
		String ret = SA[SI] + SA[SJ];
		SJ++;
		if (SJ >= SA.length) {
			SJ = 0;
			SI++;
			if (SI >= SA.length) {
				SI = 0;
				if ((System.currentTimeMillis() - flgTime) < 1000) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				flgTime = System.currentTimeMillis();
			}
		}
		return ret;
	}
	
	
}
