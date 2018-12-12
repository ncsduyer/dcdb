package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 交办事项时间-责任单位责任人表
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_taskassign_unit")
@ApiModel
public class TaskassignUnit extends Model<TaskassignUnit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交办事项时间表ID
     */
    @ApiModelProperty("交办事项时间表ID")
    private Integer tassignid;
    /**
     * 责任单位ID
     */
    @ApiModelProperty("责任单位ID")
    private Integer unitid;
    /**
     * 督办责任人ID
     */
    @ApiModelProperty("督办责任人ID")
    private Integer personid;
    /**
     * 办结时限
     */
    @ApiModelProperty("办结时限")
    private Date endtime;
    /**
     * 办理要求
     */
    @ApiModelProperty("办理要求")
    private String requirements;
    /**
     * 状态（1-新建未反馈；2-已反馈限期办理中；3-已反馈超期办理中；4-办理完成；）
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatetime;
    @ApiModelProperty("创建时间")
    private Date createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTassignid() {
        return tassignid;
    }

    public void setTassignid(Integer tassignid) {
        this.tassignid = tassignid;
    }



    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TaskassignUnit{" +
        ", id=" + id +
        ", tassignid=" + tassignid +
        ", unitId=" + unitid +
        ", personid=" + personid +
        ", endtime=" + endtime +
        ", requirements=" + requirements +
        ", status=" + status +
        ", updatetime=" + updatetime +
        ", createTime=" + createtime +
        "}";
    }
}
