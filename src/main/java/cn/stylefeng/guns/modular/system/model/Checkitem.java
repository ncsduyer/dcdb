package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 督查类型
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-17
 */
@TableName("td_checkitem")
public class Checkitem extends Model<Checkitem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 督查项类别（1-事项；2-会议；3-公文；4-信息）
     */
    private Integer itemclass;
    /**
     * 项描述
     */
    private String itemdesc;
    /**
     * 是否图表展示（0-停用；1-启用）
     */
    private Integer status;
    private Integer ischart;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemclass() {
        return itemclass;
    }

    public void setItemclass(Integer itemclass) {
        this.itemclass = itemclass;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Checkitem{" +
        ", id=" + id +
        ", itemclass=" + itemclass +
        ", itemdesc=" + itemdesc +
        ", status=" + status +
        "}";
    }

    public Integer getIschart() {
        return ischart;
    }

    public void setIschart(Integer ischart) {
        this.ischart = ischart;
    }
}
