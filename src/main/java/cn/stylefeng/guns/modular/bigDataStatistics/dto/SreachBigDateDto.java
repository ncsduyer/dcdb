package cn.stylefeng.guns.modular.bigDataStatistics.dto;

import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SreachBigDateDto extends SreachTaskDto {
    @ApiModelProperty("检查项id")
    private Integer checkItemId;
    @ApiModelProperty("查询类型，1督察督办数据，2区委会议数据，3区委公文数据，4区委信息统计")
    private Integer queryType=1;
    public Integer getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Integer checkItemId) {
        this.checkItemId = checkItemId;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
