package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.api.dto.SreachDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class ExportUtil {
    /**
     * 导出Excel
     *
     * @param template template名称
     * @param sheetName sheet名称
     * @param values    内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String template, String sheetName, List<ExportRowVo> exportRowVos) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb=new Excel(template, exportRowVos).getHssfWorkbook();
        return wb;
    }
    public static XWPFDocument getXWPFDocument (String template, String sheetName, List<ExportRowVo> exportRowVos) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        XWPFDocument wb=new Word(template, exportRowVos).getXWPFDocument();
        return wb;
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
    public static void outExport(SreachDto sreachDto, HttpServletResponse response, String template, String sheetName, List<ExportRowVo> exportRowVos) {
        String fileName;
        switch (sreachDto.getExportType()) {
            case 1:
                //创建HSSFWorkbook
                fileName = new Date().toString() + ".xls";
                HSSFWorkbook wb = ExportUtil.getHSSFWorkbook(template, sheetName, exportRowVos);

                //响应到客户端
                try {
                    ExportUtil.setResponseHeader(response, fileName);
                    OutputStream os = response.getOutputStream();
                    wb.write(os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    break;
                }
            case 2:
                fileName = new Date().toString() + ".doc";
                //创建HSSFWorkbook
                XWPFDocument wd = ExportUtil.getXWPFDocument(template, sheetName, exportRowVos);

                //响应到客户端
                try {
                    ExportUtil.setResponseHeader(response, fileName);
                    OutputStream os = response.getOutputStream();
                    wd.write(os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    break;
                }
        }
    }
}
