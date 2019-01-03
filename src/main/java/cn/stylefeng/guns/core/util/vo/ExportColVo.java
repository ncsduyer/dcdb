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
                int key=1;
                int countRow=0;
            List<ExportColSubVo> cols1=new ArrayList<>();
            for (int i = 0; i < cols.size(); i++) {
                if(key>= cols.size()) {
                    break;
                }
                if(i==0){
                    ExportColSubVo z1 = new ExportColSubVo(cols.get(i).getRowspan(),cols.get(i).getColspan(),cols.get(i).getContent());
                    cols1.add(z1);
                }
                for (int j=key; j < cols.size(); j++){
                    if(cols.get(j).getContent().equals(cols1.get(i).getContent())){
                        int z=cols1.get(i).getRowspan()+cols.get(j).getRowspan();
                        cols1.get(i).setRowspan(z);
                        key++;
                    }else{
                            ExportColSubVo z1 = new ExportColSubVo(cols.get(j).getRowspan(),cols.get(j).getColspan(),cols.get(j).getContent());
                            cols1.add(z1);
                            key++;
                        }

                    }
                }
            for (int b=0;b<cols.size();b++){
                countRow+=cols.get(b).getRowspan();
            }
            for (int a=0;a<cols1.size();a++){
                if (cols1.get(a).getRowspan()>1){
                    if (a+cols1.get(a).getRowspan()<countRow){
                        for (int j1=1;j1<cols1.get(a).getRowspan();j1++){
                            cols1.add(a+j1,new ExportColSubVo(1,1,""));
                        }
                    }
                }
            }
            this.cols=cols1;
        }
    }


}
