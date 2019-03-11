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
 * app版本信息
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-25
 */
@TableName("t_tb_version_upgrade")
public class VersionUpgrade extends Model<VersionUpgrade> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客户端设备id 1安卓pad 2安卓手机 3ios手机 4iospad
     */
    @TableField("app_type")
    private Integer appType;
    /**
     * 大版本号id
     */
    @TableField("version_id")
    private Integer versionId;
    /**
     * 小版本号
     */
    @TableField("version_mini")
    private Integer versionMini;
    /**
     * 版本标识 1.2
     */
    @TableField("version_code")
    private String versionCode;
    /**
     * 是否升级  1升级，0不升级，2强制升级
     */
    private Integer type;
    @TableField("apk_url")
    private String apkUrl;
    private String content;
    /**
     * 升级提示
     */
    @TableField("upgrade_point")
    private String upgradePoint;
    private Integer status;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getVersionMini() {
        return versionMini;
    }

    public void setVersionMini(Integer versionMini) {
        this.versionMini = versionMini;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpgradePoint() {
        return upgradePoint;
    }

    public void setUpgradePoint(String upgradePoint) {
        this.upgradePoint = upgradePoint;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VersionUpgrade{" +
                ", id=" + id +
                ", appType=" + appType +
                ", versionId=" + versionId +
                ", versionMini=" + versionMini +
                ", versionCode=" + versionCode +
                ", type=" + type +
                ", apkUrl=" + apkUrl +
                ", upgradePoint=" + upgradePoint +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
