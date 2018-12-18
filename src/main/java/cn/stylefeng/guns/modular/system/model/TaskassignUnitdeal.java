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
 * 交办事项时间-责任单位责任人-处理登记表
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_taskassign_unitdeal")
@ApiModel
public class TaskassignUnitdeal extends Model<TaskassignUnitdeal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交办责任单位ID
     */
    @ApiModelProperty("交办责任单位ID")
//    @NotNull(message = "督办事项名称不能为空")
    private Integer taunitid;
    /**
     * 处理情况描述
     */
    @ApiModelProperty("处理情况描述")
    private String dealdesc;
    /**
     * 状态（0-未完成；1-完成）
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 是否延期（0-未延期；1-延期）
     */
    @ApiModelProperty("是否延期")
    private Integer isdelay;
    /**
     * 延期前办结时限
     */
    @ApiModelProperty("延期前办结时限")
    private Date pretime;
    /**
     * 延期时间做为新的办结时限
     */
    @ApiModelProperty("延期时间")
    private Date delaytime;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Integer creatorid;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createtime;

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    /**
     * 完成时间
     */
    @TableField("finishtime")
    @ApiModelProperty("完成时间")
    private Date finishtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaunitid() {
        return taunitid;
    }

    public void setTaunitid(Integer taunitid) {
        this.taunitid = taunitid;
    }

    public String getDealdesc() {
        return dealdesc;
    }

    public void setDealdesc(String dealdesc) {
        this.dealdesc = dealdesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsdelay() {
        return isdelay;
    }

    public void setIsdelay(Integer isdelay) {
        this.isdelay = isdelay;
    }

    public Date getPretime() {
        return pretime;
    }

    public void setPretime(Date pretime) {
        this.pretime = pretime;
    }

    public Date getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(Date delaytime) {
        this.delaytime = delaytime;
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



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TaskassignUnitdeal{" +
        ", id=" + id +
        ", taunitid=" + taunitid +
        ", dealdesc=" + dealdesc +
        ", status=" + status +
        ", isdelay=" + isdelay +
        ", pretime=" + pretime +
        ", delaytime=" + delaytime +
        ", creatorid=" + creatorid +
        ", createtime=" + createtime +
        ", finishTime=" + finishtime +
        "}";
    }
}
