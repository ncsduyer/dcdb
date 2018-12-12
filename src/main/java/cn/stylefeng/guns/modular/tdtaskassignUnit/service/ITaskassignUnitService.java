package cn.stylefeng.guns.modular.tdtaskassignUnit.service;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 交办事项时间-责任单位责任人表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface ITaskassignUnitService extends IService<TaskassignUnit> {

    ResponseData updateByTaskassignUnit(TaskassignUnit taskassignUnit);
}
