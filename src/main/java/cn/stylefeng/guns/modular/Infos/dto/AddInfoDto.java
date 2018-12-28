package cn.stylefeng.guns.modular.Infos.dto;

import cn.stylefeng.guns.modular.system.model.Infosrec;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel("AddInfoDto")
public class AddInfoDto  {
    private Integer id;
    @ApiModelProperty("名称")
    @NotNull(message = "名称不能为空")
    private String title;
    @ApiModelProperty("会议备注")
    private String memo;


    @ApiModelProperty("会议时间")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @NotNull(message = "会议时间不能为空")
    private Date mtime;
    @ApiModelProperty("会议状态(0-停用；1-启用)")
    private Integer status;
    @ApiModelProperty("上报信息数组")
    private List<Infosrec> resc;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Infosrec> getResc() {
        return resc;
    }

    public void setResc(List<Infosrec> resc) {
        this.resc = resc;
    }
}