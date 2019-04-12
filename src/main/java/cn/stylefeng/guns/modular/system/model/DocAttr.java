package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 交办公文附件表
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
@TableName("td_doc_attr")
public class DocAttr extends Model<DocAttr> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 公文id
     */
    private Integer dassignid;
    /**
     * 公文附件资源id
     */
    @TableField("attr_asset_id")
    private String attrAssetId;
    /**
     * 公文附件时间
     */
    private Date createdtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDassignid() {
        return dassignid;
    }

    public void setDassignid(Integer dassignid) {
        this.dassignid = dassignid;
    }

    public String getAttrAssetId() {
        return attrAssetId;
    }

    public void setAttrAssetId(String attrAssetId) {
        this.attrAssetId = attrAssetId;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DocAttr{" +
        ", id=" + id +
        ", dassignid=" + dassignid +
        ", attrAssetId=" + attrAssetId +
        ", createdtime=" + createdtime +
        "}";
    }
}
