package cn.stylefeng.guns.modular.api.vo;

import com.baomidou.mybatisplus.annotations.TableField;

public class VersionVo {
    private Integer id;
    /**
     * 客户端设备id 1安卓pad 2安卓手机 3ios手机 4iospad
     */
    @TableField("app_type")
    private Integer appType;
    /**
     * 版本标识 1.2
     */
    private String versionCode;
    /**
     * 是否升级  1升级，0不升级，2强制升级
     */
    private Integer type;
    private String apkUrl;
    private String content;
    /**
     * 升级提示
     */
    private String upgradePoint;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpgradePoint() {
        return upgradePoint;
    }

    public void setUpgradePoint(String upgradePoint) {
        this.upgradePoint = upgradePoint;
    }
}
