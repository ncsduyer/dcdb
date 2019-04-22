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
 * 会议督查记录
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@TableName("td_meetingrec")
public class Meetingrec extends Model<Meetingrec> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会议ID
     */
    private Integer meetingid;
    /**
     * 检查项ID
     */
    private Integer checkitemid;
    /**
     * 检查项值
     */
    private String checkvalue;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 部门id
     */
    private Integer unitid;
    private String name;
    @TableField(exist = false)
    private List<MeetingRecAttr> meetingRecAttrs;
    public List<MeetingRecAttr> getMeetingRecAttrs() {
        return meetingRecAttrs;
    }

    public void setMeetingRecAttrs(List<MeetingRecAttr> meetingRecAttrs) {
        this.meetingRecAttrs = meetingRecAttrs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(Integer meetingid) {
        this.meetingid = meetingid;
    }

    public Integer getCheckitemid() {
        return checkitemid;
    }

    public void setCheckitemid(Integer checkitemid) {
        this.checkitemid = checkitemid;
    }

    public String getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(String checkvalue) {
        this.checkvalue = checkvalue;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof Meetingrec){
            Meetingrec meetingrec=(Meetingrec)obj;
            if(meetingrec.id.equals(this.id)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Meetingrec{" +
        ", id=" + id +
        ", meetingid=" + meetingid +
        ", checkitemid=" + checkitemid +
        ", checkvalue=" + checkvalue +
        ", createtime=" + createtime +
        ", unitid=" + unitid +
        "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
