package cn.stylefeng.guns.modular.zhreport.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
public class AddReportDto {
    @ApiModelProperty("报表分组名称")

    @NotNull(message = "不能为空")
    private String groupName;


    @ApiModelProperty("开始时间")
    @JSONField(format = "yyyy-MM-dd")
    @NotNull(message = "不能为空")
    private Date beforeTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    @NotNull(message = "不能为空")
    private Date afterTime;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
