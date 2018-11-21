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
import java.util.List;

/**
 * <p>
 * 事项单位关联表
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_work_company")
@ApiModel
public class WorkCompany extends Model<WorkCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 督办事项id
     */
    @TableField("a_w_id")
    private Integer aWId;
    /**
     * 责任单位id
     */
    @TableField("company_id")
    private Integer companyId;
    /**
     * 办事要求
     */
    @ApiModelProperty("办事要求")
    private String requirement;
    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    private Date deadline;
    /**
     * 办结情况
     */
    @ApiModelProperty("办结情况")
    private String situation;
    /**
     * 是否超期
     */
    @ApiModelProperty("是否超期")
    @TableField("situation_type")
    private Integer situationType;
    /**
     * 完成状态
     */
    @ApiModelProperty("完成状态")
    private Integer status;
    @ApiModelProperty("已反馈状态")
    @TableField("is_update")

    private Integer isUpdate;
    @ApiModelProperty("进度记录列表")
    @TableField(exist = false)
    private List<WcInfos> wcInfos;
    @TableField(exist = false)
    private Company company;

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getaWId() {
        return aWId;
    }

    public void setaWId(Integer aWId) {
        this.aWId = aWId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getSituationType() {
        return situationType;
    }

    public void setSituationType(Integer situationType) {
        this.situationType = situationType;
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
        return "WorkCompany{" +
                "id=" + id +
                ", aWId=" + aWId +
                ", companyId=" + companyId +
                ", requirement=" + requirement +
                ", deadline=" + deadline +
                ", situation=" + situation +
                ", situationType=" + situationType +
                ", status=" + status +
                "}";
    }


    public List<WcInfos> getWcInfos() {
        return wcInfos;
    }

    public void setWcInfos(List<WcInfos> wcInfos) {
        this.wcInfos = wcInfos;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
