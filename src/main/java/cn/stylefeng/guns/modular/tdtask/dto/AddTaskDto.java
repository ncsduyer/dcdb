package cn.stylefeng.guns.modular.tdtask.dto;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel
public class AddTaskDto{
    /**
     * 督办事项
     */
    @ApiModelProperty("督办事项id")
    private Integer pid;
    @ApiModelProperty("督办事项名称")
    @NotNull(message = "督办事项名称不能为空")
    private String title;
    /**
     * 交办事件类型
     */
    @ApiModelProperty("交办事件类型id")
    @NotNull(message = "交办事件类型不能为空")
    private Integer worktype;

//    @ApiModelProperty("交办人")
////    @NotNull(message = "交办人不能为空")
//    private String brokerName;

    /**
     * 限期时间
     */

    @ApiModelProperty("最后期限")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @NotNull(message = "最后期限不能为空")
    private Date assigntime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createtime;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @ApiModelProperty("归档时间")
    private Date endtime;
    /**
     * 办理要求
     */
    @ApiModelProperty("交办事件要求")
    @NotBlank(message = "交办事件要求不能为空")
    private String assignmemo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWorktype() {
        return worktype;
    }

    public void setWorktype(Integer worktype) {
        this.worktype = worktype;
    }

    public Date getAssigntime() {
        return assigntime;
    }

    public void setAssigntime(Date assigntime) {
        this.assigntime = assigntime;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }



    @ApiModelProperty("责任单位id 对应责任人id 数组")
    private List<TaskassignUnit> companyIds;


    public List<TaskassignUnit> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<TaskassignUnit> companyIds) {
        this.companyIds = companyIds;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
