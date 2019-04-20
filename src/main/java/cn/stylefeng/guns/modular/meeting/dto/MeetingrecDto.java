package cn.stylefeng.guns.modular.meeting.dto;

import cn.stylefeng.guns.modular.system.model.Meetingrec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("MeetingrecDto")
public class MeetingrecDto extends Meetingrec {
    @ApiModelProperty("图片列表")
    private List<Integer> pictures;
    @ApiModelProperty("文件列表")
    private List<Integer> files;

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
}
