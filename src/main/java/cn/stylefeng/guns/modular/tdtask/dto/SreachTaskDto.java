package cn.stylefeng.guns.modular.tdtask.dto;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("SreachReportDto")
public class SreachTaskDto extends SreachDto {
    /**
     * 督办事项
     */
    @ApiModelProperty("督办事项名称")
    private String title;
    /**创建时间范围 办结时间范围 超时时间范围  发布人 责任人 单位 状态 分页
     * 交办事件类型
     */
    @ApiModelProperty("交办事件类型id")
    private Integer[] workType;

    @ApiModelProperty("order")
    private String order;

    @ApiModelProperty("限期完结时间开始时间")
    @JSONField(format = "yyyy-MM-dd")
    private Date beforeTuEndTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty("限期完结时间结束时间")
    private Date afterTuEndTime;

    /**
     * 督办人
     */
    @ApiModelProperty("督办负责人id")
    private Integer[] agent;
    @ApiModelProperty("创建人id")
    private Integer creatorid;

    /**
     * 当前状态
     */
    @ApiModelProperty("当前状态数组")
    private Integer[] status;
    @ApiModelProperty("责任单位数组")
    private Integer[] companyIds;


    @ApiModelProperty("查询延期")
    private Integer isExceed=0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getWorkType() {
        return workType;
    }

    public void setWorkType(Integer[] workType) {
        this.workType = workType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer[] getAgent() {
        return agent;
    }

    public void setAgent(Integer[] agent) {
        this.agent = agent;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public Integer[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Integer[] companyIds) {
        this.companyIds = companyIds;
    }

    public Integer getIsExceed() {
        return isExceed;
    }

    public void setIsExceed(Integer isExceed) {
        this.isExceed = isExceed;
    }


    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public Date getBeforeTuEndTime() {
        return beforeTuEndTime;
    }

    public void setBeforeTuEndTime(Date beforeTuEndTime) {
        this.beforeTuEndTime = beforeTuEndTime;
    }

    public Date getAfterTuEndTime() {
        return afterTuEndTime;
    }

    public void setAfterTuEndTime(Date afterTuEndTime) {
        this.afterTuEndTime = afterTuEndTime;
    }
}
