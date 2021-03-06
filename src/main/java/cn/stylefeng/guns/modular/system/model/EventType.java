package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * 事件类型
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@TableName("t_tb_event_type")
public class EventType extends Model<EventType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 事务类型
     */
    @TableField("event_type")
    private String eventType;

    public String getReportAlias() {
        return reportAlias;
    }

    public void setReportAlias(String reportAlias) {
        this.reportAlias = reportAlias;
    }

    @TableField("report_alias")
    private String reportAlias;


    public ArrayList<Checkitem> getCheckitems() {
        return checkitems;
    }

    public void setCheckitems(ArrayList<Checkitem> checkitems) {
        this.checkitems = checkitems;
    }

    @TableField(exist = false)
    private ArrayList<Checkitem> checkitems;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", eventType=" + eventType +
                "}";
    }
}
