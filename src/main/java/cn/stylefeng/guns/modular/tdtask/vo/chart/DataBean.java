package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;

public class DataBean implements Serializable {
        /**
         * value : 字段值
         * name : 字段名称
         */

        private int value;
        private String name;

    public DataBean() {
    }

    public DataBean(String name,int value) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}
