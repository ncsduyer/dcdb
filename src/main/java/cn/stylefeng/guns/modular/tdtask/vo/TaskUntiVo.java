package cn.stylefeng.guns.modular.tdtask.vo;

import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;

import java.io.Serializable;
import java.util.List;

public class TaskUntiVo implements Serializable {
    private String campany;
    private String agent;
    private List<TaskassignUnitdeal> taskassignUnitdeals;
    public TaskUntiVo() {
        super();
    }

    public TaskUntiVo(String agent,String campany) {
        this.campany = campany;
        this.agent = agent;
    }

    public String getCampany() {
        return campany;
    }

    public void setCampany(String campany) {
        this.campany = campany;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public List<TaskassignUnitdeal> getTaskassignUnitdeals() {
        return taskassignUnitdeals;
    }

    public void setTaskassignUnitdeals(List<TaskassignUnitdeal> taskassignUnitdeals) {
        this.taskassignUnitdeals = taskassignUnitdeals;
    }
}
