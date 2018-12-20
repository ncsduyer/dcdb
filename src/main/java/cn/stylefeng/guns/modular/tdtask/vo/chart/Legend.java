package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;
import java.util.List;

public class Legend implements Serializable {
    /**
     * orient : vertical
     * left : left
     * data : ["String"]
     */

    private String orient;
    private String left;
    private List<String> data;

    public String getOrient() {
        return orient;
    }

    public void setOrient(String orient) {
        this.orient = orient;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
