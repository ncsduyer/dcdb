package cn.stylefeng.guns.core.util.vo;

public class ExportColSubVo {
    private int rowspan=1;
    private int colspan=1;
    private String content;

    public ExportColSubVo() {
    }

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
}
