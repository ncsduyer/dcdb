package cn.stylefeng.guns.modular.tdtaskassignUnit.vo;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.roses.core.util.ToolUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class TaskAssignUnitVo implements Serializable {
    private Integer id;
    private Integer taid;
    private String campany;
    private String agent;
    private Integer agentId;
    private String title;
    private String workType;
    private String createtime;
    private String assigntime;
    private String assignmemo;
    private String step;
    private String unitStep;
    private Integer status;
    private Integer createrId;
    private String endtime;
    private String usetime;
    private String is_exceed;
    private Integer tuid;
    public TaskAssignUnitVo(TaskassignUnit tu) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         id=tu.getTaskassign().getTaskid();
         tuid=tu.getId();
         taid=tu.getTassignid();
         campany=tu.getCompany().getTitle();
         agent=tu.getPerson().getName();
         agentId=tu.getPerson().getId();
         title=tu.getTaskassign().getTask().getTitle();
         workType=tu.getTaskassign().getWorkType().getTitle();
         createtime=sdf.format(tu.getTaskassign().getCreatetime());
         assigntime=sdf.format(tu.getTaskassign().getAssigntime());
         createrId=tu.getTaskassign().getCreatorid();
         if (ToolUtil.isNotEmpty(tu.getEndtime())){
            endtime=sdf.format(tu.getEndtime());
         }
         assignmemo=tu.getTaskassign().getAssignmemo();
         step=tu.getTaskassign().getEventStep().getStep();
         status=tu.getTaskassign().getStatus();
         usetime=tu.getTaskassign().getUseTime();
         is_exceed=tu.getTaskassign().getIs_exceed();
         switch (tu.getStatus()){
             case 1:
                 unitStep="新建未反馈";
                 break;
             case 2:
                 unitStep="已反馈限期办理中";
                 break;
             case 3:
                 unitStep="已反馈超期办理中";
                 break;
             case 4:
                 unitStep="办理完成";
                 break;
             default:
                 unitStep="新建未反馈";
                 break;
         }
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getTuid() {
        return tuid;
    }

    public void setTuid(Integer tuid) {
        this.tuid = tuid;
    }

    public String getUnitStep() {
        return unitStep;
    }

    public void setUnitStep(String unitStep) {
        this.unitStep = unitStep;
    }
}
