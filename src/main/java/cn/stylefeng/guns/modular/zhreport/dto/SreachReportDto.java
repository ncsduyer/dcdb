package cn.stylefeng.guns.modular.zhreport.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("SreachReportDto")
public class SreachReportDto {

    @ApiModelProperty("报表分组名称id")
    private String group_id;

    @ApiModelProperty("order")
    private String order;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    @ApiModelProperty("开始时间")
    @JSONField(format = "yyyy-MM-dd")
    private Date beforeTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    private Date afterTime;

    @ApiModelProperty("页码")
    private int page = 1;
    @ApiModelProperty("每页条数")
    private int limit = 12;


}
