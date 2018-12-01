package cn.stylefeng.guns.modular.AssignWork.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
public class AddWorkDto {
    /**
     * 督办事项
     */
    @ApiModelProperty("督办事项名称")
    @NotNull(message = "督办事项名称不能为空")
    private String title;
    /**
     * 交办事件类型
     */
    @ApiModelProperty("交办事件类型id")
    @NotNull(message = "交办事件类型不能为空")
    private Integer workType;

    @ApiModelProperty("交办人")
//    @NotNull(message = "交办人不能为空")
    private String brokerName;
    /**
     * 督办人
     */
    @ApiModelProperty("督办负责人id")
    @NotNull(message = "督办责任人不能为空")
    private Integer agent;
    /**
     * 限期时间
     */

    @ApiModelProperty("最后期限")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @NotNull(message = "最后期限不能为空")
    @Future
    private Date deadline;
    /**
     * 办理要求
     */
    @ApiModelProperty("交办事件要求")
    @NotBlank(message = "交办事件要求不能为空")
    private String requirement;
    private Integer status;
    /**
     * 延期状态
     */

    private Integer delayStatus;
    @ApiModelProperty("责任单位id数组")
    @NotNull(message = "责任单位不能为空")
    private Integer[] companyIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(Integer delayStatus) {
        this.delayStatus = delayStatus;
    }

    public Integer[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Integer[] companyIds) {
        this.companyIds = companyIds;
    }
}
