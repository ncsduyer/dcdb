package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 会议督查情况表
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-23
 */
@TableName("t_tb_meeting_situation")
public class MeetingSituation extends Model<MeetingSituation> {

    private static final long serialVersionUID = 1L;

    /**
     * 会议id
     */
    @TableField("m_id")
    private Integer mId;
    /**
     * 督查情况类型id
     */
    @TableField("s_id")
    private Integer sId;
    /**
     * 部门id
     */
    @TableField("company_id")
    private Integer companyId;
    /**
     * 人数
     */
    private Integer number;


    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "MeetingSituation{" +
                ", mId=" + mId +
                ", sId=" + sId +
                ", companyId=" + companyId +
                ", number=" + number +
                "}";
    }
}
