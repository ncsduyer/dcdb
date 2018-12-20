package cn.stylefeng.guns.modular.tdtaskassignUnit.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitMapper;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交办事项时间-责任单位责任人表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
public class TaskassignUnitServiceImpl extends ServiceImpl<TaskassignUnitMapper, TaskassignUnit> implements ITaskassignUnitService {
    @Autowired
    private TaskassignUnitMapper taskassignUnitMapper;
    @Override
    public ResponseData updateByTaskassignUnit(TaskassignUnit taskassignUnit) {
        try{
            TaskassignUnit ts=selectOne(Condition.create().eq("personid", ShiroKit.getUser().getId()).eq("id", taskassignUnit.getId()));
            if (ToolUtil.isEmpty(ts)){
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
            ts.setEndtime(taskassignUnit.getEndtime());
            ts.setRequirements(taskassignUnit.getRequirements());
            ts.setUpdatetime(new DateTime());
            ts.setStatus(taskassignUnit.getStatus());
            updateById(ts);
            return ResponseData.success();
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public List<TaskassignUnit> selectList1(int id, Integer personid) {
        return taskassignUnitMapper.selectList1(id,personid);
    }
}
