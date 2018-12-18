package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 交办事项表
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@TableName("td_task")
public class Task extends Model<Task> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    /**
     * 交办事项名称
     */
    private String title;
    /**
     * 状态(启动停用)
     */
    private Integer endstatus;





    @TableField(exist = false)
    private List<Taskassign> taskassigns;


    public List<Taskassign> getTaskassigns() {
        return taskassigns;
    }

    public void setTaskassigns(List<Taskassign> taskassigns) {
        this.taskassigns = taskassigns;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Task{" +
        ", id=" + id +
        ", title=" + title +
        ", status=" + endstatus +
        "}";
    }

    public Integer getEndstatus() {
        return endstatus;
    }

    public void setEndstatus(Integer endstatus) {
        this.endstatus = endstatus;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
