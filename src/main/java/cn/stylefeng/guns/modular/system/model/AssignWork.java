package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交办事项
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_assign_work")
@ApiModel
public class AssignWork extends Model<AssignWork> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 督办事项
     */
    @ApiModelProperty("督办事项名称")
    private String title;
    /**
     * 交办事件类型
     */
    @ApiModelProperty("交办事件类型id")
    @TableField("work_type")
    private Integer workType;
    @TableField("broker_name")
    @ApiModelProperty("交办人")
    private String brokerName;
    /**
     * 交办人
     */
    @TableField("broker_id")
    private Integer brokerId;
    /**
     * 督办人
     */
    @ApiModelProperty("督办负责人id")
    private Integer agent;
    @TableField(exist = false)
    private User agent_user;

    @ApiModelProperty("是否超期")
    @TableField(exist = false)
    private String is_exceed;
    @ApiModelProperty("已用时间")
    @TableField(exist = false)
    private String useTime;

    public String getIs_exceed() {
        return is_exceed;
    }
    /**
     * 创建人
     */
    @TableField("created_id")
    private Integer createdId;
    /**
     * 交办时间
     */
    @TableField("created_time")
    @ApiModelProperty("交办时间")
    private Date createdTime;
    /**
     * 完成时间
     */
    @TableField("end_time")
    @ApiModelProperty("完成时间")
    private Date endTime;
    /**
     * 限期时间
     */
    @TableField("deadline")
    @ApiModelProperty("最后期限")
    private Date deadline;
    /**
     * 办理要求
     */
    @ApiModelProperty("交办事件要求")
    private String requirement;
    /**
     * 总结
     */
    @ApiModelProperty("交办事件总结")
    private String remarks;
    /**
     * 当前状态
     */
    private Integer status;
    /**
     * 延期状态
     */
    @TableField("delay_status")
    private Integer delayStatus;

    /**
     * 督办单位列表
     */
    @ApiModelProperty("督办单位列表")
    @TableField(exist = false)
    private List<WorkCompany> workCompanies;

    @TableField(exist = false)
    private WorkType workTypeName;

    public void setIs_exceed(String is_exceed) {
        this.is_exceed = is_exceed;
    }
    public WorkType getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(WorkType workTypeName) {
        this.workTypeName = workTypeName;
    }
    /**
     * 步骤状态
     */
    @TableField(exist = false)
    private EventStep eventStep;
    @TableField(exist = false)
    private List<WorkFlowLog> workFlowLogs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }

    public Integer getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(Integer delayStatus) {
        this.delayStatus = delayStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AssignWork{" +
                "id=" + id +
                ", title=" + title +
                ", workType=" + workType +
                ", brokerName=" + brokerName +
                ", brokerId=" + brokerId +
                ", agent=" + agent +
                ", createdId=" + createdId +
                ", createdTime=" + createdTime +
                ", deadline=" + deadline +
                ", requirement=" + requirement +
                ", remarks=" + remarks +
                ", status=" + status +
                ", delayStatus=" + delayStatus +
                "}";
    }

    public List<WorkCompany> getWorkCompanies() {
        return workCompanies;
    }

    public void setWorkCompanies(List<WorkCompany> workCompanies) {
        this.workCompanies = workCompanies;
    }

    public EventStep getEventStep() {
        return eventStep;
    }

    public void setEventStep(EventStep eventStep) {
        this.eventStep = eventStep;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public User getAgent_user() {
        return agent_user;
    }

    public void setAgent_user(User agent_user) {
        this.agent_user = agent_user;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<WorkFlowLog> getWorkFlowLogs() {
        return workFlowLogs;
    }

    public void setWorkFlowLogs(List<WorkFlowLog> workFlowLogs) {
        this.workFlowLogs = workFlowLogs;
    }
}
