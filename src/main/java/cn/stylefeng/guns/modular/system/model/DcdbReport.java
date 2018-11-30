package cn.stylefeng.guns.modular.system.model;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 督察督办报表
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-21
 */
@TableName("t_tb_dcdb_report")
public class DcdbReport extends Model<DcdbReport> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 报表统计时间
     */
    @TableField("date_group")
    private String dateGroup;
    /**
     * 交办事项
     */
    private String title;
    /**
     * 责任单位列表
     */
    private String company;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 责任人
     */
    private String agent;
    /**
     * 办理要求
     */
    private String requirement;
    /**
     * 完成总结
     */
    private String remarks;
    /**
     * 任务类型
     */
    @TableField("work_type")
    private String workType;
    /**
     * 是否延期
     */
    @TableField("delay_status")
    private String delayStatus;
    /**
     * 状态
     */
    private String status;
    @ApiModelProperty("已用时间")
    @TableField(exist = false)
    private String useTime;

    public DcdbReport(AssignWork assignWork, String dateGroup) {
        this.title = assignWork.getTitle();
        this.dateGroup = dateGroup.trim();
        this.company = "";
        for (WorkCompany company1 : assignWork.getWorkCompanies()) {
            if (ToolUtil.isNotEmpty(company1.getCompany().getTitle())) {
                company += company1.getCompany().getTitle() + ",";
            }
        }
        if (ToolUtil.isNotEmpty(company)) {
            this.company = company.substring(0, company.length() - 1);
        }
        this.createdTime = assignWork.getCreatedTime();
        this.endTime = assignWork.getEndTime();
        this.agent = assignWork.getAgent_user().getName();
        this.requirement = assignWork.getRequirement();
        this.remarks = assignWork.getRemarks();
        this.workType = assignWork.getWorkTypeName().getTitle();
        this.delayStatus = assignWork.getDelayStatus() == 1 ? "同意延期" : "禁止延期";
        this.status = assignWork.getEventStep().getStep();
    }

    public String getUseTime() {
        return useTime;
    }

    public DcdbReport() {
        super();
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateGroup() {
        return dateGroup;
    }

    public void setDateGroup(String dateGroup) {
        this.dateGroup = dateGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(String delayStatus) {
        this.delayStatus = delayStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DcdbReport{" +
                ", id=" + id +
                ", dateGroup=" + dateGroup +
                ", title=" + title +
                ", company=" + company +
                ", createdTime=" + createdTime +
                ", endTime=" + endTime +
                ", agent=" + agent +
                ", requirement=" + requirement +
                ", remarks=" + remarks +
                ", workType=" + workType +
                ", delayStatus=" + delayStatus +
                ", status=" + status +
                "}";
    }
}
