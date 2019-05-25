package cn.stylefeng.guns.modular.tdtaskassignUnit.service;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 交办事项时间-责任单位责任人表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface ITaskassignUnitService extends IService<TaskassignUnit> {

    ResponseData updateByTaskassignUnit(List<TaskassignUnit> taskassignUnit);

    List<TaskassignUnit> selectList1(int id, Integer personid);

    ResponseData selectAsPage(SreachTaskDto sreachTaskDto);

    TaskassignUnit selectMoreById(Integer id);
}
