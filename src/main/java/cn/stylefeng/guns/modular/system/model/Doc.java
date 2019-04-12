package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交办公文表
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
@TableName("td_doc")
public class Doc extends Model<Doc> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交办公文名称
     */
    private String title;
    /**
     * 部门id
     */
    private Integer unitid;
    /**
     * 公文类型1-请示；2-报告；3-代拟稿类
     */
    private Integer doctype;
    /**
     * 流转说明
     */
    private String assignmemo;
    /**
     * 当前接收人员ID
     */
    private Integer currecvid;
    /**
     * 当前接收人员
     */
    private String currecv;
    /**
     * 创建人id
     */
    private Integer creatorid;
    /**
     * 归档关闭说明
     */
    private String closememo;
    /**
     * 来文时间
     */
    @TableField("assign_time")
    private Date assignTime;
    /**
     * 完结时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 状态(启动停用)
     */
    private Integer status;
    @TableField(exist = false)
    private Integer count;
    @TableField(exist = false)
    private User createuser;
    @TableField(exist = false)
    private EventStep eventStep;


    @TableField(exist = false)
    private List<DocRec> docRecs;
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public List<DocRec> getDocRecs() {
        return docRecs;
    }

    public void setDocRecs(List<DocRec> docRecs) {
        this.docRecs = docRecs;
    }

    public User getCreateuser() {
        return createuser;
    }

    public void setCreateuser(User createuser) {
        this.createuser = createuser;
    }

    public EventStep getEventStep() {
        return eventStep;
    }

    public void setEventStep(EventStep eventStep) {
        this.eventStep = eventStep;
    }

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

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Integer getDoctype() {
        return doctype;
    }

    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }

    public Integer getCurrecvid() {
        return currecvid;
    }

    public void setCurrecvid(Integer currecvid) {
        this.currecvid = currecvid;
    }

    public String getCurrecv() {
        return currecv;
    }

    public void setCurrecv(String currecv) {
        this.currecv = currecv;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public String getClosememo() {
        return closememo;
    }

    public void setClosememo(String closememo) {
        this.closememo = closememo;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        return "Doc{" +
        ", id=" + id +
        ", title=" + title +
        ", unitid=" + unitid +
        ", doctype=" + doctype +
        ", assignmemo=" + assignmemo +
        ", currecvid=" + currecvid +
        ", currecv=" + currecv +
        ", creatorid=" + creatorid +
        ", closememo=" + closememo +
        ", assignTime=" + assignTime +
        ", endTime=" + endTime +
        ", status=" + status +
        "}";
    }
}
