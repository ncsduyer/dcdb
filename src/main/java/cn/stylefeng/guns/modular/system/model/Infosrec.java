package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 区委信息督查表
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@TableName("td_infosrec")
public class Infosrec extends Model<Infosrec> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 信息ID
     */
    private Integer infosid;
    /**
     * 检查项ID
     */
    private Integer checkitemid;
    /**
     * 检查项值
     */
    private String checkvalue;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 部门id
     */
    private Integer unitid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfosid() {
        return infosid;
    }

    public void setInfosid(Integer infosid) {
        this.infosid = infosid;
    }

    public Integer getCheckitemid() {
        return checkitemid;
    }

    public void setCheckitemid(Integer checkitemid) {
        this.checkitemid = checkitemid;
    }

    public String getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(String checkvalue) {
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
        return "Infosrec{" +
        ", id=" + id +
        ", infosid=" + infosid +
        ", checkitemid=" + checkitemid +
        ", checkvalue=" + checkvalue +
        ", createtime=" + createtime +
        ", unitid=" + unitid +
        "}";
    }
}
