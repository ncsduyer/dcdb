package cn.stylefeng.guns.core.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ExcelUtil {
    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param wb        HSSFWorkbook对象
     * @param title     标题
     * @param values    内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String template, String sheetName, String[][] values) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb=new Excel(template).getHssfWorkbook();
        return wb;
    }

    private static void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = URLEncoder.encode(fileName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setContentType("application/octet-stream;charset=UTF-8");
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
