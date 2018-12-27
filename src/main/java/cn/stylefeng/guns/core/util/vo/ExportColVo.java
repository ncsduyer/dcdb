package cn.stylefeng.guns.core.util.vo;

import java.util.ArrayList;
import java.util.List;

public class ExportColVo {
   private List<ExportColSubVo> cols;

    public ExportColVo() {
        this.cols=new ArrayList<>();
    }

    public ExportColVo(ExportColSubVo cols) {
       this.cols=new ArrayList<>();
        this.cols.add(cols);
    }
    public ExportColVo(ExportColSubVo cols,int index) {
       this.cols=new ArrayList<>();
        this.cols.add(index,cols);
    }

    public List<ExportColSubVo> getCols() {
        return cols;
    }

    public void setCols(List<ExportColSubVo> cols) {
        this.cols = cols;
    }
}
