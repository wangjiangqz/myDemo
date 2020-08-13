package com.mytest.demo;

import com.mytest.demo.Model.Columns;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class ExportExcelTest
{
    @Autowired
    private DataSource druidDataSource;

    private String excelTitle = "光炎牛逼";


    @Test
    public void exportExcel(){
        Date beginTime = new Date();
        try {
            QueryRunner queryRunner = new QueryRunner(druidDataSource);

            ResultSetHandler<List<Columns>> stringSetHandler = new BeanListHandler<>(Columns.class);
            String colSql = "SHOW FULL COLUMNS from orders";
            List<Columns> colnums = queryRunner.query(colSql,stringSetHandler);

            ArrayListHandler resultSetHandler = new ArrayListHandler();
            String sql = "select * from orders order by order_id desc limit ?";
            List<Object[]> objects = queryRunner.query(sql, resultSetHandler,65500);

            HSSFWorkbook workbook = new HSSFWorkbook();
            //设置标题名称
            HSSFSheet sheet = workbook.createSheet(excelTitle);
            //创建标题位置
            HSSFRow rowTop = sheet.createRow(0);
            HSSFCell cellTitle = rowTop.createCell(0);
            //设置样式
            HSSFCellStyle topStyle = getTopStyle(workbook);
            HSSFCellStyle cellStyle = getCellStyle(workbook);
            //合并标题的单元格
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,colnums.size() - 1));
            cellTitle.setCellStyle(topStyle);
            cellTitle.setCellValue(excelTitle);
            //定义需要的列数
            int columnNum = colnums.size();
            HSSFRow rowCloumnName = sheet.createRow(2);
            //设置列头的内容
            for (int i = 0;i< columnNum; i++){
                HSSFCell cell = rowCloumnName.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(colnums.get(i).getField());
            }
            //设置具体的内容
            for (int j = 0;j < objects.size(); j++){
                HSSFRow contentRow = sheet.createRow(3 + j);
                for (int m = 0;m < columnNum; m++){
                    HSSFCell contentCell = contentRow.createCell(m);
                    contentCell.setCellValue(objects.get(j)[m] == null ? "" : objects.get(j)[m].toString());
                    contentCell.setCellStyle(cellStyle);
                }
            }
            //写入文件
            File file = new File("/Users/wangjiangqz/Documents/Excel-光炎牛逼.xls");
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        Date endTime = new Date();

        System.out.printf("共计耗时：%s",(endTime.getTime() - beginTime.getTime()));

    }


    /**
     * 设置excel标题样式
     * @param workbook
     * @return
     */
    public HSSFCellStyle getTopStyle(HSSFWorkbook workbook){
        //设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBold(true);
        //字体名称
        font.setFontName("Courier New");
        //设置样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        //设置底边框
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        //设置左边框
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        //设置右边框
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色
        style.setRightBorderColor(IndexedColors.BLACK.index);
        //设置顶边框
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色
        style.setTopBorderColor(IndexedColors.BLACK.index);
        //在样式中使用字体
        style.setFont(font);
        //设置自动换行
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 设置单元格样式
     * @param workbook
     * @return
     */
    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
        //设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)8);
        //字体加粗
        font.setBold(false);
        //字体名称
        font.setFontName("Courier New");
        //设置样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //设置底边框
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        //设置左边框
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式中使用字体
        style.setFont(font);
        //设置自动换行
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


}
