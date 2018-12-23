package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 区委会议
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@TableName("td_meeting")
public class Meeting extends Model<Meeting> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会议时间
     */
    private Date mtime;
    /**
     * 会议名称
     */
    private String title;
    /**
     * 起草人ID
     */
    private Integer creatorid;
    /**
     * 会议状态(0-停用；1-启用)
     */
    private Integer status;
    /**
     * 会议备注
     */
    private String memo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Meeting{" +
        ", id=" + id +
        ", mtime=" + mtime +
        ", title=" + title +
        ", creatorid=" + creatorid +
        ", status=" + status +
        ", memo=" + memo +
        "}";
    }
}
