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
 * @since 2018-11-05
 */
@TableName("t_tb_work_flow_log")
public class WorkFlowLog extends Model<WorkFlowLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 事务id
     */
    @TableField("a_w_id")
    private Integer aWId;
    /**
     * 流程事务类型
     */
    @TableField("event_type")
    private Integer eventType;
    /**
     * 流程流转时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 当前流程步骤
     */
    private Integer step;
    /**
     * 步骤状态
     */
    @TableField(exist = false)
    private EventStep eventStep;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public EventStep getEventStep() {
        return eventStep;
    }

    public void setEventStep(EventStep eventStep) {
        this.eventStep = eventStep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getaWId() {
        return aWId;
    }

    public void setaWId(Integer aWId) {
        this.aWId = aWId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
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
        return "WorkFlowLog{" +
                ", id=" + id +
                ", aWId=" + aWId +
                ", eventType=" + eventType +
                ", cretaedTime=" + createdTime +
                ", step=" + step +
                "}";
    }
}
