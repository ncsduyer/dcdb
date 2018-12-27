package cn.stylefeng.guns.core.util.vo;

import java.util.ArrayList;
import java.util.List;

public class ExportRowVo {
    private int total;
    List<ExportColVo>  colVos;

    public ExportRowVo() {
        this.colVos = new ArrayList<>();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ExportColVo> getColVos() {
        return colVos;
    }

    public void setColVos(List<ExportColVo> rowVos) {
        this.colVos = rowVos;
    }
}
