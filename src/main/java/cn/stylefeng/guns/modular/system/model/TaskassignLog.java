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
 * 督察督办流程记录
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_taskassign_log")
@ApiModel
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
    @ApiModelProperty("事务id")
    private Integer taskid;
    @ApiModelProperty("交办事项时间表ID")
    private Integer tassignid;

    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 流程事务描述
     */
    @ApiModelProperty("流程事务描述")
    private String logcontent;
    /**
     * 流程流转时间
     */
    @ApiModelProperty("流程流转时间")
    @TableField("created_time")
    private Date createtime;
    /**
     * 当前流程步骤
     */
    @ApiModelProperty("当前流程步骤")
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public Integer getTassignid() {
        return tassignid;
    }

    public void setTassignid(Integer tassignid) {
        this.tassignid = tassignid;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
