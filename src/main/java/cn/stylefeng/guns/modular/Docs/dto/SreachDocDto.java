package cn.stylefeng.guns.modular.Docs.dto;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("SreachDocDto")
public class SreachDocDto extends SreachDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("关键词")
    private String title;
    @ApiModelProperty("公文类型数组")
    private Integer[] doctypes;
    @ApiModelProperty("创建人id数组")
    private Integer[] creatorids;

    @ApiModelProperty("主送人id数组")
    private Integer[] senderIds;

    @ApiModelProperty("是否超期")
    private Integer exceed;
    @ApiModelProperty("当前状态数组")
    private Integer[] status;
    @ApiModelProperty("责任单位数组")
    private Integer[] companyIds;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getDoctypes() {
        return doctypes;
    }

    public void setDoctypes(Integer[] doctypes) {
        this.doctypes = doctypes;
    }

    public Integer[] getCreatorids() {
        return creatorids;
    }

    public void setCreatorids(Integer[] creatorids) {
        this.creatorids = creatorids;
    }

    public Integer[] getSenderIds() {
        return senderIds;
    }

    public void setSenderIds(Integer[] senderIds) {
        this.senderIds = senderIds;
    }

    public Integer getExceed() {
        return exceed;
    }

    public void setExceed(Integer exceed) {
        this.exceed = exceed;
    }
}
