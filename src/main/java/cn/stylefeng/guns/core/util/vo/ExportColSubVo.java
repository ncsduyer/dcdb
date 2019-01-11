package cn.stylefeng.guns.core.util.vo;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExportColSubVo {
    private int rowspan=1;
    private int colspan=1;
    private String content;
    private Boolean isSetStyle=false;
    private HSSFCellStyle style;


    public ExportColSubVo(int rowspan, String content) {
        this.rowspan = rowspan;
        this.content = content;
        initStyle();
    }

    public ExportColSubVo(int rowspan, int colspan, String content) {
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.content = content;
        initStyle();
    }
    private void initStyle(){
//        this.isSetStyle=true;
        HSSFWorkbook  hssfWorkbook=new HSSFWorkbook();
        style=hssfWorkbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font= hssfWorkbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
    }
    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public Boolean getSetStyle() {
        return isSetStyle;
    }

    public void setSetStyle(Boolean setStyle) {
        isSetStyle = setStyle;
    }

    public HSSFCellStyle getStyle() {
        return style;
    }

    public void setStyle(HSSFCellStyle style) {
        this.isSetStyle=true;
        this.style = style;
    }
}
