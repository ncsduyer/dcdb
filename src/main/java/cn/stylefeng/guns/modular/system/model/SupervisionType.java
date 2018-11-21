package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 督查事件类型
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-23
 */
@TableName("t_tb_supervision_type")
public class SupervisionType extends Model<SupervisionType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 事件类型
     */
    private String title;
    /**
     * 所属功能类型，1 会议监督，2 公文监督
     */
    @TableField("s_type")
    private Integer sType;


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

    public Integer getsType() {
        return sType;
    }

    public void setsType(Integer sType) {
        this.sType = sType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SupervisionType{" +
                ", id=" + id +
                ", title=" + title +
                ", sType=" + sType +
                "}";
    }
}
