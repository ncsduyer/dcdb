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

/**
 * <p>
 * 督办单位进度记录
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_wc_infos")
@ApiModel
public class WcInfos extends Model<WcInfos> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 督办事件关联单位id
     */
    private Integer pid;
    /**
     * 办理情况
     */
    @ApiModelProperty("办理情况")
    private String situation;
    /**
     * 提交人
     */
    @TableField("submitter")
    private String submitter;
    /**
     * 提交时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 是否超期
     */
    @ApiModelProperty("是否超期")
    @TableField("situation_type")
    private Integer situationType;
    /**
     * 是否可以延期
     */
    @ApiModelProperty("是否可以延期")
    @TableField("delay_status")
    private Integer delayStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getSituationType() {
        return situationType;
    }

    public void setSituationType(Integer situationType) {
        this.situationType = situationType;
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
        return "WcInfos{" +
                "id=" + id +
                ", pid=" + pid +
                ", situation=" + situation +
                ", userId=" + submitter +
                ", createdTime=" + createdTime +
                ", situationType=" + situationType +
                ", delayStatus=" + delayStatus +
                "}";
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
}
