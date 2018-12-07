package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
@TableName("t_tb_report")
public class Report extends Model<Report> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空")
    private Integer id;
    @TableField("group_id")
    private Integer groupId;
    @TableField("event_type")
    private Integer eventType;
    @TableField(exist = false)
    private EventType eventTypeObj;
    @TableField("company_id")
    private Integer company_id;
    @TableField(exist = false)
    private Company company;



    @TableField(exist = false)
    private ReportGroup group;
    @TableField("row_name")
    @NotNull(message = "列名不能为空")
    private String rowName;
    private String val;
    @TableField("created_time")
    private Date createdTime;

    public Report() {

    }

    public Report(Integer eventType, int company_id, @NotNull(message = "列名不能为空") String rowName, String val) {
        this.eventType = eventType;
        this.company_id = company_id;
        this.rowName = rowName;
        this.val = val;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public ReportGroup getGroup() {
        return group;
    }

    public void setGroup(ReportGroup group) {
        this.group = group;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Report{" +
        ", id=" + id +
        ", groupId=" + groupId +
        ", eventType=" + eventType +
        ", company=" + company +
        ", rowName=" + rowName +
        ", val=" + val +
        ", createdTime=" + createdTime +
        "}";
    }

    public EventType getEventTypeObj() {
        return eventTypeObj;
    }

    public void setEventTypeObj(EventType eventTypeObj) {
        this.eventTypeObj = eventTypeObj;
    }
}
