package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class Axis implements Serializable {

    /**
     * type :
     * data : []
     */

    private String type;
    private LinkedHashSet<String> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedHashSet<String> getData() {
        return data;
    }

    public void setData(LinkedHashSet<String> data) {
        this.data = data;
    }
}
