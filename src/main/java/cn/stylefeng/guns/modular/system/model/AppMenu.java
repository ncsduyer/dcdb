package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * app菜单表
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-21
 */
@TableName("t_tb_app_menu")
public class AppMenu extends Model<AppMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("menu_id")
    private Long menuId;
    /**
     * appmenu类型
     */
    private Integer type;
    private Integer order;
    private Integer statue;
    /**
     * pc端html路径
     */
    @TableField("pc_url")
    private String pcUrl;
    /**
     * 移动端html路径
     */
    @TableField("wap_url")
    private String wapUrl;

    private String pcIcon;
    private String wapIcon;

    public String getPcIcon() {
        return pcIcon;
    }

    public void setPcIcon(String pcIcon) {
        this.pcIcon = pcIcon;
    }

    public String getWapIcon() {
        return wapIcon;
    }

    public void setWapIcon(String wapIcon) {
        this.wapIcon = wapIcon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppMenu{" +
                ", id=" + id +
                ", menuId=" + menuId +
                ", type=" + type +
                ", statue=" + statue +
                ", pcUrl=" + pcUrl +
                ", wapUrl=" + wapUrl +
                "}";
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
