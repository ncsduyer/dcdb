package cn.stylefeng.guns.modular.tdtask.vo;

import java.io.Serializable;
import java.util.List;

public class ReportsVo implements Serializable {
    private String name;
    private List<TaskVo> taskVoss;

    public String getName() {
        return name;
    }

    public ReportsVo() {
    }

    public ReportsVo(String name, List<TaskVo> taskVoss) {
        this.name = name;
        this.taskVoss = taskVoss;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskVo> getTaskVoss() {
        return taskVoss;
    }

    public void setTaskVoss(List<TaskVo> taskVoss) {
        this.taskVoss = taskVoss;
    }
}
