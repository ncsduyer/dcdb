package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 公文督查记录
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
@ApiModel("DocRec")
@TableName("td_doc_rec")
public class DocRec extends Model<DocRec> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 公文ID
     */
    @ApiModelProperty("公文ID")
    private Integer docassignid;
    /**
     * 检查项ID
     */
    @ApiModelProperty("检查项ID")
    private Integer checkitemid;
    /**
     * 检查项值
     */
    @ApiModelProperty("检查项值始终为 1 ")
    private Integer checkvalue;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Integer unitid;

    @TableField(exist = false)
    private Checkitem checkitem;
    public Checkitem getCheckitem() {
        return checkitem;
    }

    public void setCheckitem(Checkitem checkitem) {
        this.checkitem = checkitem;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocassignid() {
        return docassignid;
    }

    public void setDocassignid(Integer docassignid) {
        this.docassignid = docassignid;
    }

    public Integer getCheckitemid() {
        return checkitemid;
    }

    public void setCheckitemid(Integer checkitemid) {
        this.checkitemid = checkitemid;
    }

    public Integer getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(Integer checkvalue) {
        this.checkvalue = checkvalue;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DocRec{" +
        ", id=" + id +
        ", docassignid=" + docassignid +
        ", checkitemid=" + checkitemid +
        ", checkvalue=" + checkvalue +
        ", createtime=" + createtime +
        ", unitid=" + unitid +
        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocRec)) return false;
        DocRec rec = (DocRec) o;
        return id.equals(rec.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
