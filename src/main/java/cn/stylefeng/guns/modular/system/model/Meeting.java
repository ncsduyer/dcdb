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
 * 会议
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-23
 */
@TableName("t_tb_meeting")
public class Meeting extends Model<Meeting> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会议名称
     */
    private String title;
    /**
     * 开会时间
     */
    @TableField("meeting_time")
    private Date meetingTime;
    /**
     * 会风会纪督查负责人
     */
    private Integer Overseer;
    /**
     * 应到人数
     */
    private Integer schedule;
    /**
     * 实际到会人数
     */
    private Integer actual;
    /**
     * 备注
     */
    private String make;


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

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Integer getOverseer() {
        return Overseer;
    }

    public void setOverseer(Integer Overseer) {
        this.Overseer = Overseer;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                ", id=" + id +
                ", title=" + title +
                ", meetingTime=" + meetingTime +
                ", Overseer=" + Overseer +
                ", schedule=" + schedule +
                ", actual=" + actual +
                ", make=" + make +
                "}";
    }
}
