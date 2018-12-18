package cn.stylefeng.guns.modular.tdtask.vo;

import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class TaskVo implements Serializable {
    @Autowired
    private IUserService userService;
    public static TaskVo taskVo;

    private String name;
    private String workType;
    private List<TaskUntiVo> taskUntiVo;

    private Date createtime;
    private Date assigntime;
    private String assignmemo;

    public TaskVo() {
        super();
    }
    @PostConstruct
    public void init(){
        taskVo=this;
        taskVo.userService=this.userService;
    }
    public TaskVo(String name, List<TaskUntiVo> taskUntiVo, Date createtime, Date assigntime, String assignmemo, String status, String usetime, String overdue) {
        this.name = name;
        this.taskUntiVo = taskUntiVo;
        this.createtime = createtime;
        this.assigntime = assigntime;
        this.assignmemo = assignmemo;
        this.status = status;
        this.usetime = usetime;
        this.is_exceed = overdue;
    }

    public TaskVo(String title, Taskassign taskassign) {
        this.name = title;
        this.workType = taskassign.getWorkType().getTitle();
        this.taskUntiVo = new ArrayList<>();

        for (TaskassignUnit tu:taskassign.getTaskassignUnits()){
            TaskUntiVo taskUntiVo=new TaskUntiVo(taskVo.userService.selectById(tu.getPersonid()).getName(),tu.getCompany().getTitle());
            taskUntiVo.setTaskassignUnitdeals(tu.getTaskassignUnitdeals());
            this.taskUntiVo.add(taskUntiVo);
        }

        this.createtime = taskassign.getCreatetime();
        this.assigntime = taskassign.getAssigntime();
        this.assignmemo = taskassign.getAssignmemo();
        this.status = taskassign.getEventStep().getStep();
        this.usetime = taskassign.getUseTime();
        this.is_exceed = taskassign.getIs_exceed();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskUntiVo> getTaskUntiVo() {
        return taskUntiVo;
    }

    public void setTaskUntiVo(List<TaskUntiVo> taskUntiVo) {
        this.taskUntiVo = taskUntiVo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getAssigntime() {
        return assigntime;
    }

    public void setAssigntime(Date assigntime) {
        this.assigntime = assigntime;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    public String getIs_exceed() {
        return is_exceed;
    }

    public void setIs_exceed(String is_exceed) {
        this.is_exceed = is_exceed;
    }

    private String status;
    private String usetime;
    private String is_exceed;

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}
