package cn.stylefeng.guns.modular.meeting.dto;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("SreachMeetingDto")
public class SreachMeetingDto extends SreachDto {
    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String title;

    @ApiModelProperty("创建人id")
    private Integer creatorid;

    /**
     * 当前状态
     */
    @ApiModelProperty("当前状态数组")
    private Integer[] status;
    @ApiModelProperty("责任单位数组")
    private Integer[] companyIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
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
}
