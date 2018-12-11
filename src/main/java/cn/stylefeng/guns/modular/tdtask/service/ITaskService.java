package cn.stylefeng.guns.modular.tdtask.service;

import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 交办事项表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface ITaskService extends IService<Task> {

    ResponseData SreachPage(SreachTaskDto sreachTaskDto);

    ResponseData add(AddTaskDto addTaskDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData updateByTaskassign(Taskassign taskassign);
}
