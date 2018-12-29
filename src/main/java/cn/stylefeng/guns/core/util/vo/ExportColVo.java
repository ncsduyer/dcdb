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

    public void removeDuplication(){
        if (cols.size()>1){
            List<ExportColSubVo> cols1=new ArrayList<>();
            for (int i = 0; i < cols.size(); i++) {
                if(i==0){
                    ExportColSubVo z1 = new ExportColSubVo(cols.get(i).getRowspan(),cols.get(i).getColspan(),cols.get(i).getContent());
                    cols1.add(z1);

                }
                for (int j = 0; j < cols1.size(); j++) {
                    if(cols.get(i).getContent().equals(cols1.get(j).getContent())){
                        int a=cols.get(i).getRowspan()+cols1.get(j).getRowspan();
                        cols1.get(i).setRowspan(a);
                    }else{
                        ExportColSubVo z1 = new ExportColSubVo(cols.get(i).getRowspan(),cols.get(i).getColspan(),cols.get(i).getContent());
                        cols1.add(z1);
                    }
                }
            }
            this.cols=cols1;
        }
    }


}
