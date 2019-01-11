package cn.stylefeng.guns.core.util.vo;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class ExportColSubVo {
    private int rowspan=1;
    private int colspan=1;
    private String content;
    private Boolean isSetStyle;
    private HSSFCellStyle style;


    public ExportColSubVo(int rowspan, String content) {
        this.rowspan = rowspan;
        this.content = content;
    }

    public ExportColSubVo(int rowspan, int colspan, String content) {
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.content = content;
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
