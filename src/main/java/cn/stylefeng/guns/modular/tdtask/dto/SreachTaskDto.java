package cn.stylefeng.guns.modular.tdtask.dto;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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



    /**
     * 督办人
     */
    @ApiModelProperty("督办负责人id")
    private Integer[] agent;
    @ApiModelProperty("创建人id")
    private Integer creatorid;
    @ApiModelProperty("导出文件类型，默认为excel 1：excel，2：doc")
    private Integer type=1;
    @ApiModelProperty("图表类型，默认为柱状图 1：柱状图，2：饼图")
    private Integer ChartType=1;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChartType() {
        return ChartType;
    }

    public void setChartType(Integer chartType) {
        ChartType = chartType;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }
}
