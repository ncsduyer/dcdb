package cn.stylefeng.guns.modular.workFlowLog.service.impl;

import cn.stylefeng.guns.modular.system.dao.WorkFlowLogMapper;
import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import cn.stylefeng.guns.modular.workFlowLog.service.IWorkFlowLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 督察督办流程记录 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-05
 */
@Service
public class WorkFlowLogServiceImpl extends ServiceImpl<WorkFlowLogMapper, WorkFlowLog> implements IWorkFlowLogService {
    @Autowired
    private WorkFlowLogMapper workFlowLogMapper;

    @Override
    public List<WorkFlowLog> selectByManyId(Integer id) {
        return workFlowLogMapper.selectByManyId(id);
    }
}
