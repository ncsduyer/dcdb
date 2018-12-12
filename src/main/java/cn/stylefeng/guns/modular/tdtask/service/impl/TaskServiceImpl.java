package cn.stylefeng.guns.modular.tdtask.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.system.dao.TaskMapper;
import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * 交办事项表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ITaskassignService taskassignService;
    @Autowired
    private ITaskassignUnitService taskassignUnitService;
    @Override
    public ResponseData SreachPage(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Bettime bettime=new Bettime(sreachTaskDto);
            sreachTaskDto.setBeforeTime(bettime.getBeforeTime());
            sreachTaskDto.setAfterTime(bettime.getAfterTime());
            Page<Task> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            EntityWrapper<Task> ew = new EntityWrapper<>();
            ew.setEntity(new Task());
            ew.between("ta.createtime", sreachTaskDto.getBeforeTime(), sreachTaskDto.getAfterTime());
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
               ew.lt("ta.assigntime",new Date()).isNull("ta.endtime");
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
               ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("t.id,ta.id",false);
            }

            System.out.println(ew.getSqlSegment());


            ArrayList<Task> arrayList = taskMapper.selectAsPage(page,ew);

            for (Task task : arrayList) {
                for (Taskassign taskassign:task.getTaskassigns()){
                    taskassign.setUseTime(VoUtil.getUseTime(taskassign.getCreatetime(), taskassign.getEndtime()));
                }
            }
            page.setRecords(arrayList);
            page.setTotal(arrayList.size());
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }

    }

    @Override
    public ResponseData add(AddTaskDto addTaskDto) {
        try{
            if (ToolUtil.isNotEmpty(addTaskDto.getCompanyIds())) {

                Task task = new Task();
                BeanUtils.copyProperties(addTaskDto, task);
                insert(task);
                Taskassign taskassign = new Taskassign();
                taskassign.setTaskid(task.getId());
                taskassign.setWorktype(addTaskDto.getWorktype());
                taskassign.setAssigntime(addTaskDto.getAssigntime());
                taskassign.setAssignmemo(addTaskDto.getAssignmemo());
                taskassign.setCreatetime(new DateTime());
                taskassign.setCreatorid(ShiroKit.getUser().getId());
                taskassign.setStatus(1);
                taskassignService.insert(taskassign);
                TaskassignUnit taskassignUnit = new TaskassignUnit();
                taskassignUnit.setTassignid(taskassign.getId());


//                循环插入交办单位
                for (TaskassignUnit map : addTaskDto.getCompanyIds()) {
                    taskassignUnit.setPersonid(map.getPersonid());
                    taskassignUnit.setUnitid(map.getUnitid());
                    taskassignUnit.setCreatetime(new DateTime());
                    taskassignUnit.setStatus(1);
                    taskassignUnitService.insert(taskassignUnit);
                }

                return ResponseData.success(taskassign);
            } else {
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }

    }

    @Override
    public ResponseData selectWithManyById(Integer id) {
        Task task = taskMapper.selectWithManyById(id);
        for (Taskassign taskassign:task.getTaskassigns()) {
            taskassign.setUseTime(VoUtil.getUseTime(taskassign.getCreatetime(), taskassign.getEndtime()));
        }
        return ResponseData.success(task);
    }

    @Override
    public ResponseData updateByTaskassign(Taskassign taskassign) {
        try {
            if(taskassign.getEndtime().before(taskassign.getCreatetime())){
                    return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(),"办结时间错误");
            }
                Taskassign taskassign1 = new Taskassign();
                taskassign1.setId(taskassign.getId());
                taskassign1.setStatus(taskassign.getStatus());
                taskassign1.setClosememo(taskassign.getClosememo());
                taskassign1.setEndtime(taskassign.getEndtime());
                 taskassignService.updateById(taskassign1);
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
