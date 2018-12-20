package cn.stylefeng.guns.modular.tdtaskassign.service;

import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
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

    ResponseData updateByTaskassign(Taskassign taskassign);
}
