package cn.stylefeng.guns.modular.workFlowLog.service;

import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 督察督办流程记录 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-05
 */
public interface IWorkFlowLogService extends IService<WorkFlowLog> {

    List<WorkFlowLog> selectByManyId(Integer id);
}
