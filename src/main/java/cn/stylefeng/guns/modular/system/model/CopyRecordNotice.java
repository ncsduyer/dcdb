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
 * 抄送记录表
 * </p>
 *
 * @author 三千霜
 * @since 2019-03-28
 */
@TableName("copy_record_notice")
public class CopyRecordNotice extends Model<CopyRecordNotice> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 关联主键
     */
    @TableField("join_id")
    private Integer joinId;
    /**
     * 内容
     */
    private String content;
    /**
     * 当前状态
     */
    @TableField("now_status")
    private Integer nowStatus;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 电话
     */
    private String tel;
    /**
     * 接收人
     */
    private String sendee;
    /**
     * 触发人id
     */
    @TableField("sender_id")
    private Integer senderId;
    /**
     * 接收状态
     */
    @TableField("send_status")
    private Integer sendStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(Integer nowStatus) {
        this.nowStatus = nowStatus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSendee() {
        return sendee;
    }

    public void setSendee(String sendee) {
        this.sendee = sendee;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CopyRecordNotice{" +
        ", id=" + id +
        ", type=" + type +
        ", joinId=" + joinId +
        ", content=" + content +
        ", nowStatus=" + nowStatus +
        ", createtime=" + createtime +
        ", tel=" + tel +
        ", sendee=" + sendee +
        ", senderId=" + senderId +
        ", sendStatus=" + sendStatus +
        "}";
    }
}
