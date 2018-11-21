package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 领导列表
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_leadership")
public class Leadership extends Model<Leadership> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 领导姓名
     */
    private String name;
    /**
     * 部门
     */
    private String department;
    /**
     * 职务
     */
    private String title;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Leadership{" +
                "id=" + id +
                ", name=" + name +
                ", department=" + department +
                ", title=" + title +
                ", phone=" + phone +
                ", email=" + email +
                "}";
    }
}
