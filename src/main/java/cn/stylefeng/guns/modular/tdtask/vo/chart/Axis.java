package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;
import java.util.List;

public class Axis implements Serializable {

    /**
     * type :
     * data : []
     */

    private String type;
    private List<String> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
