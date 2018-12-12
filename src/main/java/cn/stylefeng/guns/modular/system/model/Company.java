package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 责任单位
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_company")
public class Company extends Model<Company> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 责任单位名称
     */
    private String title;
    @TableField("abb_title")
    private String abbTitle;
    /**
     * 地址
     */
    private String adress;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 负责人
     */
    private String contact;
    /**
     * 负责人电话
     */
    @TableField("contact_phone")
    private String contactPhone;
    /**
     * 详细信息
     */
    private String remarks;

    private Integer status;

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

    public String getAbbTitle() {
        return abbTitle;
    }

    public void setAbbTitle(String abbTitle) {
        this.abbTitle = abbTitle;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", title=" + title +
                ", abbTitle=" + abbTitle +
                ", adress=" + adress +
                ", tel=" + tel +
                ", contact=" + contact +
                ", contactPhone=" + contactPhone +
                ", remarks=" + remarks +
                "}";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
