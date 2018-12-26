package cn.stylefeng.guns.core.util.vo;

public class ExportColVo {
    private int rowspan;
    private String content;

    public ExportColVo() {
    }

    public ExportColVo(int rowspan, String content) {
        this.rowspan = rowspan;
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
}
