package cn.stylefeng.guns.modular.MeetingRec.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("SreachMeetingRecDto")
public class SreachMeetingRecDto  {

    @NotNull
    @ApiModelProperty("所属事项id")
    private Integer pid;
//    @ApiModelProperty("创建人id")
//    private Integer creatorid;
    @ApiModelProperty("检查项ID")
    private Integer[] checkitemid;

    @ApiModelProperty("单位数组")
    private Integer[] companyIds;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

//    public Integer getCreatorid() {
//        return creatorid;
//    }
//
//    public void setCreatorid(Integer creatorid) {
//        this.creatorid = creatorid;
//    }

    public Integer[] getCheckitemid() {
        return checkitemid;
    }

    public void setCheckitemid(Integer[] checkitemid) {
        this.checkitemid = checkitemid;
    }

    public Integer[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Integer[] companyIds) {
        this.companyIds = companyIds;
    }
}
