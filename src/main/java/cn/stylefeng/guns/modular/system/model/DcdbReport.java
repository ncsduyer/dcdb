package cn.stylefeng.guns.modular.system.model;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public DcdbReport() {
        super();
    }

    public DcdbReport(AssignWork assignWork) {
        BeanUtils.copyProperties(assignWork, this);
        DateFormat format = new SimpleDateFormat("yyyy年MM月");
        this.dateGroup = format.format(assignWork.getCreatedTime());
        String company = "";
        for (WorkCompany company1 : assignWork.getWorkCompanies()) {
            if (ToolUtil.isNotEmpty(company1.getCompany().getTitle())) {
                company += company1.getCompany().getTitle() + ",";
            }
        }
        this.company = company;
        this.agent = assignWork.getAgent_user().getName();
        this.workType = assignWork.getWorkTypeName().getTitle();
        this.delayStatus = assignWork.getDelayStatus() == 1 ? "同意延期" : "禁止延期";
        this.status = assignWork.getEventStep().getStep();
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
