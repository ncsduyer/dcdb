package cn.stylefeng.guns.modular.tdtaskassign.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.system.dao.TaskassignMapper;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 交办事项时间表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskassignServiceImpl extends ServiceImpl<TaskassignMapper, Taskassign> implements ITaskassignService {
    @Autowired
    private TaskassignMapper taskassignMapper;
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
            if(ToolUtil.isEmpty(taskassign.getEndtime())){

            taskassign1.setEndtime(new DateTime());
            }else{

            taskassign1.setEndtime(taskassign.getEndtime());
            }
            updateById(taskassign1);
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public Taskassign selectByManyId(Integer taskassignId) {
        EntityWrapper<Taskassign> ew = new EntityWrapper<>();
        ew.setEntity(new Taskassign());
        ew.eq("ta.id", taskassignId);
        if (ToolUtil.isNotEmpty(ShiroKit.getUser())){
            ew.eq("tu.personid", ShiroKit.getUser().getId());
        }
       Taskassign taskassign= taskassignMapper.selectByManyId(ew);
        if (ToolUtil.isNotEmpty(taskassign)){

        taskassign.setUseTime(VoUtil.getUseTime(taskassign.getCreatetime(), taskassign.getEndtime()));
        }
        return taskassign;
    }
}
