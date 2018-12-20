package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitdealMapper;
import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.ITaskassignUnitdealService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交办事项时间-责任单位责任人-处理登记表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
public class TaskassignUnitdealServiceImpl extends ServiceImpl<TaskassignUnitdealMapper, TaskassignUnitdeal> implements ITaskassignUnitdealService {
    @Autowired
    private ITaskassignService taskassignService;
    @Autowired
    private ITaskassignUnitService taskassignUnitService;
    @Override
    public ResponseData updateByTaskassignUnitdeal(TaskassignUnitdeal taskassignUnitdeal) {
        try {
            if (taskassignUnitdeal.getStatus()==1){
                if (taskassignUnitdeal.getFinishtime().before(taskassignUnitdeal.getCreatetime())){
                   return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
                }
                if (ToolUtil.isEmpty(taskassignUnitdeal.getFinishtime())){
                  taskassignUnitdeal.setFinishtime(new DateTime());
                }
                taskassignUnitdeal.setPretime(null);
                taskassignUnitdeal.setDelaytime(null);
//                Taskassign taskassign=taskassignService.selectById(taskassignUnitService.selectOne(Condition.create().eq("taunitid", taskassignUnitdeal.getTaunitid())).getTassignid());
//                if (taskassign.getEndtime().before(taskassignUnitdeal.getFinishtime())){
//                taskassign.setEndtime(taskassignUnitdeal.getFinishtime());
//                taskassignService.updateByTaskassign(taskassign);
//
//                }
            }else{
                taskassignUnitdeal.setFinishtime(null);
                if (taskassignUnitdeal.getIsdelay()==1){
                    taskassignUnitdeal.setPretime(selectById(taskassignUnitdeal.getId()).getDelaytime());
                }
            }

            updateById(taskassignUnitdeal);
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
