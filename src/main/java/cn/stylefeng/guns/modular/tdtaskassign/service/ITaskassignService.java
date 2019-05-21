package cn.stylefeng.guns.modular.tdtaskassign.service;

import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 交办事项时间表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface ITaskassignService extends IService<Taskassign> {
    ResponseData SreachPage(SreachTaskDto sreachTaskDto);

    ResponseData updateByTaskassign(Taskassign taskassign);

    Taskassign selectByManyId(Integer id);

    Integer selectCountByStatus(Wrapper<Taskassign> wrapper);
}
