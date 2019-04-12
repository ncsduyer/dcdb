package cn.stylefeng.guns.modular.Docs.dto;

import cn.stylefeng.guns.modular.system.model.CopyRecordNotice;
import cn.stylefeng.guns.modular.system.model.DocRec;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel("AddDocDto")
public class AddDocDto {
    private Integer id;
    @ApiModelProperty("名称")
    @NotNull(message = "名称不能为空")
    private String title;
    @ApiModelProperty("部门id")
    private Integer unitid;
    @ApiModelProperty("公文类型1-请示；2-报告；3-代拟稿类")
    private Integer doctype;
    @ApiModelProperty("流转说明")
    private String assignmemo;
    @ApiModelProperty("当前接收人员ID")
    private Integer currecvid;
    @ApiModelProperty("当前接收人员")
    private String currecv;

    @ApiModelProperty("创建人id")
    private Integer creatorid;

    @ApiModelProperty("归档关闭说明")
    private String closememo;

    @ApiModelProperty("来文时间")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @NotNull(message = "来文时间不能为空")
    private Date assignTime;

    @ApiModelProperty("完结时间")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;
    @ApiModelProperty("状态(0-未归档；1-已归档)")
    private Integer status;
    @ApiModelProperty("上报信息数组")
    private List<DocRec> resc;
    private List<CopyRecordNotice> copyRecordNotices;

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

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Integer getDoctype() {
        return doctype;
    }

    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }

    public Integer getCurrecvid() {
        return currecvid;
    }

    public void setCurrecvid(Integer currecvid) {
        this.currecvid = currecvid;
    }

    public String getCurrecv() {
        return currecv;
    }

    public void setCurrecv(String currecv) {
        this.currecv = currecv;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public String getClosememo() {
        return closememo;
    }

    public void setClosememo(String closememo) {
        this.closememo = closememo;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DocRec> getResc() {
        return resc;
    }

    public void setResc(List<DocRec> resc) {
        this.resc = resc;
    }

    public List<CopyRecordNotice> getCopyRecordNotices() {
        return copyRecordNotices;
    }

    public void setCopyRecordNotices(List<CopyRecordNotice> copyRecordNotices) {
        this.copyRecordNotices = copyRecordNotices;
    }
}
