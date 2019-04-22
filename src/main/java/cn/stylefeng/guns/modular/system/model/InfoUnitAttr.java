package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 信息内单位附件表
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-22
 */
@TableName("td_info_unit_attr")
public class InfoUnitAttr extends Model<InfoUnitAttr> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer unitid;
    /**
     * 关联外表id
     */
    private Integer infoid;
    /**
     * 资源id
     */
    private String pictures;
    /**
     * 类型 1图片 2文件
     */
    private String files;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "InfoUnitAttr{" +
        ", id=" + id +
        ", unitid=" + unitid +
        ", infoid=" + infoid +
        ", pictures=" + pictures +
        ", files=" + files +
        "}";
    }
}
