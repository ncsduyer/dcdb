package cn.stylefeng.guns.modular.tdtask.vo;

import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Component
public class TaskVo implements Serializable {
    @Autowired
    private IUserService userService;
    public static TaskVo taskVo;
    private Integer id;
    private Integer taid;


    private String name;
    private String workType;
    private List<TaskUntiVo> taskUntiVo;

    private String createtime;
    private String assigntime;
    private String assignmemo;
    private String step;
    private Integer status;
    private String usetime;
    private String is_exceed;
    public TaskVo() {
        super();
    }
    @PostConstruct
    public void init(){
        taskVo=this;
        taskVo.userService=this.userService;
    }

    public TaskVo(Task task, Taskassign taskassign) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.name = task.getTitle();
        this.id = task.getId();
        this.taid = taskassign.getId();
        this.workType = taskassign.getWorkType().getTitle();
        this.step = taskassign.getEventStep().getStep();
        this.taskUntiVo = new ArrayList<>();

        for (TaskassignUnit tu:taskassign.getTaskassignUnits()){
            TaskUntiVo taskUntiVo=new TaskUntiVo(taskVo.userService.selectById(tu.getPersonid()).getName(),tu.getCompany().getTitle());
            taskUntiVo.setEndTime(sdf.format(tu.getEndtime()));
            taskUntiVo.setTaskassignUnitdeals(tu.getTaskassignUnitdeals());
            this.taskUntiVo.add(taskUntiVo);
        }

            this.createtime = sdf.format(taskassign.getCreatetime());
            this.assigntime = sdf.format(taskassign.getAssigntime());

        this.assignmemo = taskassign.getAssignmemo();
        this.status = taskassign.getStatus();
        this.usetime = taskassign.getUseTime();
        this.is_exceed = taskassign.getIs_exceed();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaid() {
        return taid;
    }

    public void setTaid(Integer taid) {
        this.taid = taid;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAssigntime() {
        return assigntime;
    }

    public void setAssigntime(String assigntime) {
        this.assigntime = assigntime;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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


    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
