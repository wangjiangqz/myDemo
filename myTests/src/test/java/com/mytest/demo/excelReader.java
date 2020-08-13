package com.mytest.demo;

import com.mytest.demo.Model.ExcelDataVO;
import com.mytest.demo.Model.ExcelDataVOForPartner;
import com.mytest.demo.Model.ExcelReaderUtil;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.util.List;

public class excelReader {

    @Test
    public void excelTest() throws Exception {
        FileWriter fileWriter = new FileWriter("/Users/wangjiangqz/Documents/JustDoIt.sql", false);
        StringBuilder ioBuilder = new StringBuilder();
        List<ExcelDataVO> readResult = ExcelReaderUtil.readExcel("/Users/wangjiangqz/Documents/111.xlsx");
        for (ExcelDataVO vo : readResult) {
            if (StringUtils.hasText(vo.getWantSettlement()) && !vo.getWantSettlement().trim().equals(vo.getNowSettlement().trim())) {
                int i = 0;
                if (vo.getWantSettlement().trim().equals("月结")) {
                    i = 0;
                } else if (vo.getWantSettlement().trim().equals("一单一结")) {
                    i = 1;
                } else if (vo.getWantSettlement().trim().equals("普通预付")) {
                    i = 2;
                }
                ioBuilder.append("update hotel set settlement_type = ").append(i).append(" where activity_id = '").append(vo.getProductId()).append("';\r\n");
            }
        }
        List<ExcelDataVOForPartner> readResultForPartner = ExcelReaderUtil.readPartnerExcel("/Users/wangjiangqz/Documents/111.xlsx");
        for (ExcelDataVOForPartner vo : readResultForPartner) {
            if (StringUtils.hasText(vo.getCheckMode())) {
                int i = 0;
                if (vo.getCheckMode().trim().equals("BD")) {
                    i = 2;
                } else if (vo.getCheckMode().trim().equals("eBooking")) {
                    i = 1;
                }
                ioBuilder.append("update partner set use_statement = ").append(i).append(", use_statement_time = '2020-09-01' where partner_id = '").append(vo.getPartnerId()).append("';\r\n");
            }
        }
        fileWriter.write(ioBuilder.toString());
        fileWriter.close();
    }
}
