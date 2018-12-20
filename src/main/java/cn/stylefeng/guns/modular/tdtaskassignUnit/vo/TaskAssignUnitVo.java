package cn.stylefeng.guns.modular.tdtaskassignUnit.vo;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class TaskAssignUnitVo implements Serializable {
    private Integer id;
    private Integer taid;
    private String campany;
    private String agent;
    private String title;
    private String workType;
    private String createtime;
    private String assigntime;
    private String assignmemo;
    private String step;
    private Integer status;
    private String usetime;
    private String is_exceed;
    public TaskAssignUnitVo(TaskassignUnit tu) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         id=tu.getTaskassign().getTaskid();
         taid=tu.getTassignid();
         campany=tu.getCompany().getTitle();
         agent=tu.getPerson().getName();
         title=tu.getTaskassign().getTask().getTitle();
         workType=tu.getTaskassign().getWorkType().getTitle();
         createtime=sdf.format(tu.getTaskassign().getCreatetime());
         assigntime=sdf.format(tu.getTaskassign().getAssigntime());
         assignmemo=tu.getTaskassign().getAssignmemo();
         step=tu.getTaskassign().getEventStep().getStep();
         status=tu.getTaskassign().getStatus();
         usetime=tu.getTaskassign().getUseTime();
         is_exceed=tu.getTaskassign().getIs_exceed();
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
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

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
