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
 * 交办事项时间表
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_taskassign")
@ApiModel
public class Taskassign extends Model<Taskassign> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交办事项表ID
     */
    @ApiModelProperty("交办事项表ID")
    private Integer taskid;
    @ApiModelProperty("事项类型id")
    private Integer worktype;

    public Date getAssigntime() {
        return assigntime;
    }

    public void setAssigntime(Date assigntime) {
        this.assigntime = assigntime;
    }

    /**
     * 交办时间
     */
    @ApiModelProperty("交办截止时间")
    private Date assigntime;
    /**
     * 分派说明
     */
    @ApiModelProperty("分派说明")
    private String assignmemo;
    /**
     * 状态（1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭；）
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Integer creatorid;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createtime;
    /**
     * 归档说明
     */
    @ApiModelProperty("归档说明")
    private String closememo;
    @ApiModelProperty("总负责人")
    private String charge;
    @ApiModelProperty("总负责人电话")
    private String phone;
    @ApiModelProperty("归档时间")
    private Date endtime;
    @TableField(exist = false)
    private User createuser;
    @TableField(exist = false)
    private WorkType workType;
    @TableField(exist = false)
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @TableField(exist = false)
    private EventStep eventStep;
    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public EventStep getEventStep() {
        return eventStep;
    }

    public void setEventStep(EventStep eventStep) {
        this.eventStep = eventStep;
    }
    public User getCreateuser() {
        return createuser;
    }

    public void setCreateuser(User createuser) {
        this.createuser = createuser;
    }
    public List<TaskassignUnit> getTaskassignUnits() {
        return taskassignUnits;
    }

    public void setTaskassignUnits(List<TaskassignUnit> taskassignUnits) {
        this.taskassignUnits = taskassignUnits;
    }
    @ApiModelProperty("督办单位列表")
    @TableField(exist = false)
    private List<TaskassignUnit> taskassignUnits;
    @ApiModelProperty("是否超期")
    @TableField(exist = false)
    private String is_exceed;
    @ApiModelProperty("已用时间")
    @TableField(exist = false)
    private String useTime;

    public String getIs_exceed() {
        return is_exceed;
    }

    public void setIs_exceed(String is_exceed) {
        this.is_exceed = is_exceed;
    }

    public String getUseTime() {
        return useTime;
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

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
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

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getClosememo() {
        return closememo;
    }

    public void setClosememo(String closememo) {
        this.closememo = closememo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Taskassign{" +
        ", id=" + id +
        ", taskid=" + taskid +
        ", workType=" + worktype +
        ", tatime=" + assigntime +
        ", assignmemo=" + assignmemo +
        ", status=" + status +
        ", creatorid=" + creatorid +
        ", createtime=" + createtime +
        ", closememo=" + closememo +
        "}";
    }

    public Integer getWorktype() {
        return worktype;
    }

    public void setWorktype(Integer worktype) {
        this.worktype = worktype;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
