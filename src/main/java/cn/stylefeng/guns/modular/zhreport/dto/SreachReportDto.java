package cn.stylefeng.guns.modular.zhreport.dto;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("SreachReportDto")
public class SreachReportDto extends SreachDto {

    @ApiModelProperty("查询类型，1督察督办数据，2责任单位数据，3人员数据，4事务相关统计")
    private Integer queryType=1;

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
