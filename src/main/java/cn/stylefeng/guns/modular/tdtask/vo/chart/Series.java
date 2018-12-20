package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;
import java.util.List;

public class Series<T> implements Serializable {

    /**
     * name :
     * type :
     * data : []
     */

    private String name;
    private String type;
    private List<T> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
