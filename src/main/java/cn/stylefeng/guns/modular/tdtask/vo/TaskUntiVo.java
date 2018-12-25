package cn.stylefeng.guns.modular.tdtask.vo;

import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskUntiVo implements Serializable {
    private String campany;
    private String agent;
    private String endTime;
    private List<TaskassignUnitdealVo> taskassignUnitdeals;
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

    public List<TaskassignUnitdealVo> getTaskassignUnitdeals() {
        return taskassignUnitdeals;
    }

    public void setTaskassignUnitdeals(List<TaskassignUnitdeal> taskassignUnitdeals) {
        List<TaskassignUnitdealVo> taskassignUnitdealVos= new ArrayList<>();
        for (TaskassignUnitdeal td:taskassignUnitdeals
             ) {
            TaskassignUnitdealVo ta=new TaskassignUnitdealVo();
            BeanUtils.copyProperties(td, ta);
            if (ta.getIsdelay()==1&&ta.getStatus()==1){
                ta.setDelaydesc(ta.getDealdesc());
                ta.setDealdesc("");
            }
            taskassignUnitdealVos.add(ta);
        }
        Collections.sort(taskassignUnitdealVos,new Comparator<TaskassignUnitdealVo>() {
                    @Override
                    public int compare(TaskassignUnitdealVo h1, TaskassignUnitdealVo h2) {
                        if (h1.getCreatetime().before(h2.getCreatetime())){
                            return -1;
                        }
                        return 1;
                    }
                });

        this.taskassignUnitdeals = taskassignUnitdealVos;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
