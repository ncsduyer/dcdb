package cn.stylefeng.guns.modular.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public abstract class SreachDto {
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

}
