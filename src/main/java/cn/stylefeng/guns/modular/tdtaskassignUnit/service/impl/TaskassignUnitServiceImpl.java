package cn.stylefeng.guns.modular.tdtaskassignUnit.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitMapper;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.vo.TaskAssignUnitVo;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private ITaskassignService taskassignService;
    private boolean isall=false;
    private Integer taskassignId;
    private Integer count=-1;
    @Override
    public ResponseData updateByTaskassignUnit(List<TaskassignUnit> taskassignUnits) {
        try{
            for (TaskassignUnit taskassignUnit:taskassignUnits
                 ) {
                if (!updateOne(taskassignUnit)){
                    return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
                }
            }

            if (isall){
                Taskassign taskassign=taskassignService.selectById(taskassignId);
                if (taskassign.getStatus()<=2){
                    taskassign.setStatus(2);

                }else {
                    if (count > 0 && selectCount(Condition.create().eq("status", 4).eq("tassignid", taskassignId)) == count) {
                        taskassign.setStatus(4);
                taskassign.setEndtime(new DateTime());

                    } else {
                        taskassign.setStatus(3);
                    }
                }
                taskassignService.updateByTaskassign(taskassign);
                count=-1;
            }
            return ResponseData.success();
        }catch (Exception e){
            count=-1;
            isall=false;
            taskassignId=-1;
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    public boolean updateOne(TaskassignUnit taskassignUnit) {
        TaskassignUnit ts=selectOne(Condition.create().eq("personid", ShiroKit.getUser().getId()).eq("id", taskassignUnit.getId()));
        if (ToolUtil.isEmpty(ts)){
            return false;
        }
        taskassignId=ts.getTassignid();
        if (count<=0){

        count=selectCount(Condition.create().eq("tassignid", taskassignId));
        }
        if (ToolUtil.isNotEmpty(taskassignUnit.getEndtime())){
            ts.setEndtime(taskassignUnit.getEndtime());
        }
        if (ToolUtil.isNotEmpty(taskassignUnit.getRequirements())){
            ts.setRequirements(taskassignUnit.getRequirements());
        }
        if (ToolUtil.isNotEmpty(taskassignUnit.getStatus())){
            ts.setStatus(taskassignUnit.getStatus());
        }

        ts.setUpdatetime(new DateTime());

        updateById(ts);
        //检查是否全部反馈
      if (count>0&&selectCount(Condition.create().gt("status", 1).eq("tassignid", taskassignId))==count){

          isall=true;
      }
        return true;
    }

    @Override
    public List<TaskassignUnit> selectList1(int id, Integer personid) {
        return taskassignUnitMapper.selectList1(id,personid);
    }

    @Override
    public ResponseData selectAsPage(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Page<TaskAssignUnitVo> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            new Bettime(sreachTaskDto);
            EntityWrapper<TaskassignUnit> ew = new EntityWrapper<>();
            ew.setEntity(new TaskassignUnit());
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.gt("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.lt("ta.assigntime", sreachTaskDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCreatorid())){
                ew.eq("ta.creatorid", sreachTaskDto.getCreatorid());
            }
//            拼接查询条件
            if (ToolUtil.isNotEmpty(sreachTaskDto.getTitle())){
                ew.like("t.title", sreachTaskDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getWorkType())){
                ew.in("ta.worktype", sreachTaskDto.getWorkType());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
                ew.in("ta.status", sreachTaskDto.getStatus());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAgent())){
                ew.in("tu.personid", sreachTaskDto.getAgent());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCompanyIds())){
                ew.in("tu.unitid", sreachTaskDto.getCompanyIds());
            }
            if (sreachTaskDto.getIsExceed()==1){
                ew.lt("tu.endtime",new Date()).isNull("ta.endtime");
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("tu.id",false);
            }

            ArrayList<TaskassignUnit> arrayList = taskassignUnitMapper.selectAsPage(page,ew);
            ArrayList<TaskAssignUnitVo> taskVos=new ArrayList<>();
            for (TaskassignUnit task : arrayList) {
                task.getTaskassign().setUseTime(VoUtil.getUseTime(task.getTaskassign().getAssigntime(), task.getTaskassign().getEndtime()));
                TaskAssignUnitVo taskAssignUnitVo=new TaskAssignUnitVo(task);
                taskVos.add(taskAssignUnitVo);
            }
            page.setRecords(taskVos);
            page.setTotal(taskassignUnitMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
