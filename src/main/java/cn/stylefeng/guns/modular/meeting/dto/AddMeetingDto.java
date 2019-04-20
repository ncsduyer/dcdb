package cn.stylefeng.guns.modular.meeting.dto;

import cn.stylefeng.guns.modular.system.model.CopyRecordNotice;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel("AddMeetingDto")
public class AddMeetingDto {
    private Integer id;
    @ApiModelProperty("名称")
    @NotNull(message = "名称不能为空")
    private String title;
    @ApiModelProperty("会议备注")
    private String memo;

    @ApiModelProperty("会议主持人")
    private Integer hostid;
    @ApiModelProperty("会风会纪督查人")
    private Integer creatorid;

    @ApiModelProperty("会议时间")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @NotNull(message = "会议时间不能为空")
    private Date mtime;
    @ApiModelProperty("会议状态(0-停用；1-启用)")
    private Integer status;
    @ApiModelProperty("图片列表")
    private List<Integer> pictures;
    @ApiModelProperty("文件列表")
    private List<Integer> files;
    @ApiModelProperty("上报信息数组")
    private List<MeetingrecDto> resc;

    private List<CopyRecordNotice> copyRecordNotices;

    public List<CopyRecordNotice> getCopyRecordNotices() {
        return copyRecordNotices;
    }

    public void setCopyRecordNotices(List<CopyRecordNotice> copyRecordNotices) {
        this.copyRecordNotices = copyRecordNotices;
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

    public List<Integer> getPictures() {
        return pictures;
    }

    public void setPictures(List<Integer> pictures) {
        this.pictures = pictures;
    }

    public List<Integer> getFiles() {
        return files;
    }

    public void setFiles(List<Integer> files) {
        this.files = files;
    }

    public List<MeetingrecDto> getResc() {
        return resc;
    }

    public void setResc(List<MeetingrecDto> resc) {
        this.resc = resc;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"memo\":\"")
                .append(memo).append('\"');
        sb.append(",\"host\":")
                .append(hostid);
        sb.append(",\"creatorid\":")
                .append(creatorid);
        sb.append(",\"mtime\":\"")
                .append(mtime).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"resc\":")
                .append(resc);
        sb.append(",\"copyRecordNotices\":")
                .append(copyRecordNotices);
        sb.append('}');
        return sb.toString();
    }

    public Integer getHostid() {
        return hostid;
    }

    public void setHostid(Integer hostid) {
        this.hostid = hostid;
    }
}