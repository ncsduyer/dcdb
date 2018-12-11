package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 督察督办流程记录
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_taskassign_log")
public class TaskassignLog extends Model<TaskassignLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 事务id
     */
    @TableField("task_id")
    private Integer taskId;
    /**
     * 流程事务描述
     */
    private Integer conternt;
    /**
     * 流程流转时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 当前流程步骤
     */
    private Integer step;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getConternt() {
        return conternt;
    }

    public void setConternt(Integer conternt) {
        this.conternt = conternt;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TaskassignLog{" +
        ", id=" + id +
        ", taskId=" + taskId +
        ", conternt=" + conternt +
        ", createdTime=" + createdTime +
        ", step=" + step +
        "}";
    }
}
