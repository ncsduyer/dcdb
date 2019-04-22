package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-20
 */
@TableName("td_meeting_rec_attr")
public class MeetingRecAttr extends Model<MeetingRecAttr> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联外表id
     */
    private Integer pid;
    /**
     * 资源id
     */
    private Integer assetid;
    /**
     * 类型 1图片 2文件
     */
    private Integer type;
    /**
     * 排序(从小到大)
     */
    private Integer order;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAssetid() {
        return assetid;
    }

    public void setAssetid(Integer assetid) {
        this.assetid = assetid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MeetingRecAttr{" +
        ", id=" + id +
        ", pid=" + pid +
        ", assetid=" + assetid +
        ", type=" + type +
        ", order=" + order +
        "}";
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof MeetingRecAttr){
            MeetingRecAttr meetingrec=(MeetingRecAttr)obj;
            if(meetingrec.id.equals(this.id)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
