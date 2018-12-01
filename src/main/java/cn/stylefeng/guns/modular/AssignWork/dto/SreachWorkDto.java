package cn.stylefeng.guns.modular.AssignWork.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("SreachWorkDto")
public class SreachWorkDto {
    /**
     * 督办事项
     */
    @ApiModelProperty("督办事项名称")
    private String title;
    /**
     * 交办事件类型
     */
    @ApiModelProperty("交办事件类型id")
    private Integer workType;
    @ApiModelProperty("交办人")
    private String brokerName;
    @ApiModelProperty("order")
    private String order;

    /**
     * 督办人
     */
    @ApiModelProperty("督办负责人id")
    private Integer agent;
    @ApiModelProperty("创建人id")
    private Integer created_id;

    /**
     * 当前状态
     */
    @ApiModelProperty("当前状态数组")
    private Integer[] status;
    /**
     * 延期状态
     */
    @ApiModelProperty("延期状态")
    private Integer delayStatus;

    @ApiModelProperty("开始时间")
    @JSONField(format = "yyyy-MM-dd")
    private Date beforeTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    private Date afterTime;
    @ApiModelProperty("查询延期")
    private Integer isExceed;
    @ApiModelProperty("页码")
    private int page = 1;
    @ApiModelProperty("每页条数")
    private int limit = 12;
    private int ismore = 0;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    public Date getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(Date beforeTime) {
        this.beforeTime = beforeTime;
    }

    public Date getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(Date afterTime) {
        this.afterTime = afterTime;
    }

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

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public Integer getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(Integer delayStatus) {
        this.delayStatus = delayStatus;
    }

    public Integer getCreated_id() {
        return created_id;
    }

    public void setCreated_id(Integer created_id) {
        this.created_id = created_id;
    }

    public int getIsmore() {
        return ismore;
    }

    public void setIsmore(int ismore) {
        this.ismore = ismore;
    }

    public Integer getIsExceed() {
        return isExceed;
    }

    public void setIsExceed(Integer isExceed) {
        this.isExceed = isExceed;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
