package cn.stylefeng.guns.modular.tdtaskassign.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.system.dao.TaskassignMapper;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;

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
    public ResponseData SreachPage(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Page<Taskassign> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            new Bettime(sreachTaskDto);
            EntityWrapper<Taskassign> ew = new EntityWrapper<>();
            ew.setEntity(new Taskassign());
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCreatorid())) {
                ew.eq("ta.creatorid", sreachTaskDto.getCreatorid());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAgent())){
                ew.in("tu.personid", sreachTaskDto.getAgent());
            }

//          拼接查询条件
            if (ToolUtil.isNotEmpty(sreachTaskDto.getTitle())){
                ew.like("t.title", sreachTaskDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getWorkType())){
                ew.in("ta.worktype", sreachTaskDto.getWorkType());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCompanyIds())){
                ew.in("tu.unitid", sreachTaskDto.getCompanyIds());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getIsExceed())&&sreachTaskDto.getIsExceed()==1){
                //    ew.le("tu.endtime",new Date()).isNull("ta.endtime");
                ew.eq("tu.status", 3);
                //    ew.eq("ta.status",3);
            }else{
                if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
                    ew.in("ta.status", sreachTaskDto.getStatus());
                }
                if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
                    if(VoUtil.getMaxNum(sreachTaskDto.getStatus())<5){
                        ew.in("tu.status", sreachTaskDto.getStatus());
                    }
                }
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.ge("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.le("ta.assigntime", sreachTaskDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTuEndTime())){
                ew.ge("tu.endtime", sreachTaskDto.getBeforeTuEndTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTuEndTime())){
                ew.le("tu.endtime", sreachTaskDto.getAfterTuEndTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("tu.id",false);
            }

            ArrayList<Taskassign> arrayList = taskassignMapper.selectAsPage(page,ew.groupBy("ta.id,tu.id"));
            for (Taskassign taskassign : arrayList) {
                taskassign.setUseTime(VoUtil.getUseTime(taskassign.getAssigntime(), taskassign.getEndtime()));
            }
            page.setRecords(arrayList);
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

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
            if(taskassign.getStatus()>3){
//                if(ToolUtil.isEmpty(taskassign.getEndtime())&&ToolUtil.isEmpty(taskassign1.getEndtime())){
//                taskassign1.setEndtime(new DateTime());
//                }else if (ToolUtil.isNotEmpty(taskassign.getEndtime())&&ToolUtil.isNotEmpty(taskassign1.getEndtime())&&taskassign.getEndtime().after(taskassign1.getEndtime())){
//                taskassign1.setEndtime(taskassign.getEndtime());
//                }
                if(ToolUtil.isEmpty(taskassign.getEndtime())){
                taskassign1.setEndtime(new DateTime());
                }else if(taskassign.getEndtime().before(taskassign1.getCreatetime())){
                    DateTime dateTime=new DateTime();
                    dateTime.setTime(taskassign1.getCreatetime().getTime()+3600);
                taskassign1.setEndtime(dateTime);
                }
                else{
                taskassign1.setEndtime(taskassign.getEndtime());
                }
            }
            updateById(taskassign1);
            return ResponseData.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public Taskassign selectByManyId(Integer taskassignId) {
        EntityWrapper<Taskassign> ew = new EntityWrapper<>();
        ew.setEntity(new Taskassign());
        ew.eq("ta.id", taskassignId);
//        if (ToolUtil.isNotEmpty(ShiroKit.getUser())){
//            ew.eq("tu.personid", ShiroKit.getUser().getId());
//        }
       Taskassign taskassign= taskassignMapper.selectByManyId(ew);
        if (ToolUtil.isNotEmpty(taskassign)){

        taskassign.setUseTime(VoUtil.getUseTime(taskassign.getCreatetime(), taskassign.getEndtime()));
        }
        return taskassign;
    }

    @Override
    public Integer selectCountByStatus(Wrapper<Taskassign> wrapper) {
        return taskassignMapper.selectCountByStatus(wrapper);
    }
}
