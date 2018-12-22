package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitdealMapper;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.ITaskassignUnitdealService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                if (selectList(Condition.create().eq("status", 1).eq("taunitid", taskassignUnitdeal.getTaunitid())).size()>0){
                    return new ErrorResponseData(45000, "已经完成不可重复提交！");
                }
                TaskassignUnit taskassignUnit=taskassignUnitService.selectById(taskassignUnitdeal.getTaunitid());
            if (taskassignUnitdeal.getStatus()==1){
                if (ToolUtil.isEmpty(taskassignUnitdeal.getFinishtime())){
                  taskassignUnitdeal.setFinishtime(new DateTime());
                }
                if (taskassignUnitdeal.getFinishtime().before(taskassignUnitdeal.getCreatetime())){
                   return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
                }
                taskassignUnitdeal.setPretime(null);
                taskassignUnitdeal.setDelaytime(null);
                taskassignUnit.setStatus(4);
            }else{
                taskassignUnitdeal.setFinishtime(null);
                if (taskassignUnitdeal.getIsdelay()==1){
                    taskassignUnitdeal.setPretime(selectById(taskassignUnitdeal.getId()).getDelaytime());
                    taskassignUnit.setStatus(3);
                }else{
                    taskassignUnit.setStatus(2);
                }

            }

            updateById(taskassignUnitdeal);
//            判断是否完成 修改unit状态 1-新建未反馈；2-已反馈限期办理中；3-已反馈超期办理中；4-办理完成；）
            List<TaskassignUnit> tsus=new ArrayList<>();
            tsus.add(taskassignUnit);
            taskassignUnitService.updateByTaskassignUnit(tsus);
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
