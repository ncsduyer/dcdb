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
 * @since 2019-04-22
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
     * 当前接收人员ID
     */
    private Integer currecvid;
    /**
     * 当前接收人员
     */
    private String currecv;
    /**
     * 办件人id（多选）
     */
    @TableField("do_person_ids")
    private String doPersonIds;
    /**
     * 主送人id
     */
    @TableField("sender_id")
    private Integer senderId;
    /**
     * 分送人id（多选）
     */
    @TableField("copy_sender_id")
    private String copySenderId;
    /**
     * 创建人id
     */
    private Integer creatorid;
    /**
     * 图片列表 包含原图，缩略图
     */
    private String pictures;
    /**
     * 文件列表
     */
    private String files;
    /**
     * 归档关闭说明
     */
    private String closememo;
    /**
     * 流转说明
     */
    private String assignmemo;
    /**
     * 领导批示
     */
    private String instructions;
    /**
     * 办公室建议
     */
    private String suggestion;
    /**
     * 拟办意见
     */
    private String opinion;
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
     * 是否超期
     */
    private Integer exceed;
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
    @TableField(exist = false)
    private List<Asset> imgs;
    @TableField(exist = false)
    private List<Asset> fileList;

    public List<Asset> getImgs() {
        return imgs;
    }

    public void setImgs(List<Asset> imgs) {
        this.imgs = imgs;
    }

    public List<Asset> getFileList() {
        return fileList;
    }

    public void setFileList(List<Asset> fileList) {
        this.fileList = fileList;
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

    public String getDoPersonIds() {
        return doPersonIds;
    }

    public void setDoPersonIds(String doPersonIds) {
        this.doPersonIds = doPersonIds;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getCopySenderId() {
        return copySenderId;
    }

    public void setCopySenderId(String copySenderId) {
        this.copySenderId = copySenderId;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getClosememo() {
        return closememo;
    }

    public void setClosememo(String closememo) {
        this.closememo = closememo;
    }

    public String getAssignmemo() {
        return assignmemo;
    }

    public void setAssignmemo(String assignmemo) {
        this.assignmemo = assignmemo;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
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

    public Integer getExceed() {
        return exceed;
    }

    public void setExceed(Integer exceed) {
        this.exceed = exceed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public List<DocRec> getDocRecs() {
        return docRecs;
    }

    public void setDocRecs(List<DocRec> docRecs) {
        this.docRecs = docRecs;
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
        ", currecvid=" + currecvid +
        ", currecv=" + currecv +
        ", doPersonIds=" + doPersonIds +
        ", senderId=" + senderId +
        ", copySenderId=" + copySenderId +
        ", creatorid=" + creatorid +
        ", pictures=" + pictures +
        ", files=" + files +
        ", closememo=" + closememo +
        ", assignmemo=" + assignmemo +
        ", instructions=" + instructions +
        ", suggestion=" + suggestion +
        ", opinion=" + opinion +
        ", assignTime=" + assignTime +
        ", endTime=" + endTime +
        ", exceed=" + exceed +
        ", status=" + status +
        "}";
    }
}
