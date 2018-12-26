package cn.stylefeng.guns.core.util.vo;

import java.util.List;

public class ExportVo {
    private int total;
    List<ExportRowVo>  rowVos;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ExportRowVo> getRowVos() {
        return rowVos;
    }

    public void setRowVos(List<ExportRowVo> rowVos) {
        this.rowVos = rowVos;
    }
}
