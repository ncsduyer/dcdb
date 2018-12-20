package cn.stylefeng.guns.modular.tdtaskassign.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.dao.TaskassignMapper;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交办事项时间表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
public class TaskassignServiceImpl extends ServiceImpl<TaskassignMapper, Taskassign> implements ITaskassignService {
    @Override
    public ResponseData updateByTaskassign(Taskassign taskassign) {
        try {
            Taskassign taskassign1 = selectById(taskassign.getId());
            if(ToolUtil.isNotEmpty(taskassign.getEndtime())&&taskassign.getEndtime().before(taskassign1.getCreatetime())){
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(),"办结时间错误");
            }
            taskassign1.setId(taskassign.getId());
            taskassign1.setStatus(taskassign.getStatus());
            taskassign1.setClosememo(taskassign.getClosememo());
            taskassign1.setEndtime(taskassign.getEndtime());
            updateById(taskassign1);
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
