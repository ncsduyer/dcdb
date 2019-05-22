package cn.stylefeng.guns.modular.tdtask.service;

import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    ResponseData getDcdbReports(SreachTaskDto sreachTaskDto);

    ResponseData sreachChart(SreachTaskDto sreachTaskDto);

    List<Task> getAll(SreachTaskDto sreachTaskDto);

    void export(SreachTaskDto sreachTaskDto, HttpServletResponse response) throws Exception;

    ResponseData edit(AddTaskDto addTaskDto);
}
