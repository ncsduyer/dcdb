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
 * 资源表
 * </p>
 *
 * @author 三千霜
 * @since 2019-03-01
 */
@TableName("t_tb_asset")
public class Asset extends Model<Asset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 文件大小,单位B
     */
    @TableField("file_size")
    private Long fileSize;
    /**
     * 上传时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 状态;1:可用,0:不可用
     */
    private Integer status;
    /**
     * 下载次数
     */
    @TableField("download_times")
    private Integer downloadTimes;
    /**
     * 文件惟一码
     */
    @TableField("file_key")
    private String fileKey;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件路径,相对于根目录,可以为url
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 文件md5值
     */
    @TableField("file_md5")
    private String fileMd5;
    @TableField("file_sha1")
    private String fileSha1;
    /**
     * 文件后缀名,不包括点
     */
    private String suffix;
    /**
     * 其它详细信息,JSON格式
     */
    private String more;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Integer downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileSha1() {
        return fileSha1;
    }

    public void setFileSha1(String fileSha1) {
        this.fileSha1 = fileSha1;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Asset{" +
        ", id=" + id +
        ", userId=" + userId +
        ", fileSize=" + fileSize +
        ", createTime=" + createTime +
        ", status=" + status +
        ", downloadTimes=" + downloadTimes +
        ", fileKey=" + fileKey +
        ", filename=" + filename +
        ", filePath=" + filePath +
        ", fileMd5=" + fileMd5 +
        ", fileSha1=" + fileSha1 +
        ", suffix=" + suffix +
        ", more=" + more +
        "}";
    }
}
