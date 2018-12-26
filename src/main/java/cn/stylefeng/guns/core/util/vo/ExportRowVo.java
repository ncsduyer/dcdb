package cn.stylefeng.guns.core.util.vo;

import java.util.List;

public class ExportRowVo {
   private List<ExportColVo> cols;

    public ExportRowVo() {
    }

    public ExportRowVo(List<ExportColVo> cols) {
        this.cols = cols;
    }

    public List<ExportColVo> getCols() {
        return cols;
    }

    public void setCols(List<ExportColVo> cols) {
        this.cols = cols;
    }
}
