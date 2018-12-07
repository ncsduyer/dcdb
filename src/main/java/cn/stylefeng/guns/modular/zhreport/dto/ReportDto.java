package cn.stylefeng.guns.modular.zhreport.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public abstract class ReportDto {
    @ApiModelProperty("开始时间")
    @JSONField(format = "yyyy-MM-dd")
//    @NotNull(message = "不能为空")
    private Date beforeTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
//    @NotNull(message = "不能为空")
    private Date afterTime;
    @ApiModelProperty("报表分组名称id")
    private String group_id;
    @ApiModelProperty("报表分组名称")
//    @NotNull(message = "不能为空")
    private String groupName;

    @ApiModelProperty("order")
    private String order;

    @ApiModelProperty("页码")
    private int page = 1;
    @ApiModelProperty("每页条数")
    private int limit = 12;

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





    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
