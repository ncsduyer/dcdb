package cn.stylefeng.guns.modular.tdtask.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.ChartUtil;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.system.dao.TaskMapper;
import cn.stylefeng.guns.modular.system.model.EventStep;
import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtask.vo.ReportsVo;
import cn.stylefeng.guns.modular.tdtask.vo.TaskVo;
import cn.stylefeng.guns.modular.tdtask.vo.chart.*;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private IEventStepService eventStepService;
    @Override
    public ResponseData SreachPage(SreachTaskDto sreachTaskDto) {
       return taskassignUnitService.selectAsPage(sreachTaskDto);

    }

    @Override
    public ResponseData add(AddTaskDto addTaskDto) {
        try{
            if (selectCount(Condition.create().eq("title", addTaskDto.getTitle()))>0){
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(),"交办事项重名");
            }
            if (ToolUtil.isNotEmpty(addTaskDto.getCompanyIds())) {

                    Task task = new Task();
                    BeanUtils.copyProperties(addTaskDto, task);
                if (ToolUtil.isEmpty(selectById(task.getId()))){
                    insert(task);
                }
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
    public ResponseData getDcdbReports(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Page<ReportsVo> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            Bettime bettime=new Bettime(sreachTaskDto);
            sreachTaskDto.setBeforeTime(bettime.getBeforeTime());
            sreachTaskDto.setAfterTime(bettime.getAfterTime());
            EntityWrapper<Task> ew = new EntityWrapper<>();
            ew.setEntity(new Task());
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.ge("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.le("ta.assigntime", sreachTaskDto.getAfterTime());
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
                ew.le("tu.endtime",new Date()).isNull("ta.endtime");
            }
            ew.groupBy("tu.id");
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("t.id,ta.id,tud.id",false);
            }

            ArrayList<Task> arrayList = taskMapper.selectAsPage(page,ew);
            ArrayList<TaskVo> taskVos=new ArrayList<>();
            for (Task task : arrayList) {
                for (Taskassign taskassign:task.getTaskassigns()){
                    taskassign.setUseTime(VoUtil.getUseTime(taskassign.getAssigntime(), taskassign.getEndtime()));
                    TaskVo taskVo=new TaskVo(task,taskassign);
                    taskVos.add(taskVo);
                }
            }
            Map<String, List<TaskVo>> map=taskVos.stream().collect(Collectors.groupingBy(x->x.getStep()));
            ArrayList<ReportsVo> reportsVos=new ArrayList<>();
            for (Map.Entry<String, List<TaskVo>> et: map.entrySet()
                 ) {
                    ReportsVo reportsVo=new ReportsVo(et.getKey(),et.getValue());
                    reportsVos.add(reportsVo);
            }
            page.setRecords(reportsVos);

            ew = new EntityWrapper<>();
            ew.setEntity(new Task());
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.ge("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.le("ta.assigntime", sreachTaskDto.getAfterTime());
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
                ew.le("tu.endtime",new Date()).isNull("ta.endtime");
            }
            page.setTotal(taskMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
    @Override
    public ResponseData sreachChart(SreachTaskDto sreachTaskDto) {

        //循环获取类型
        List<EventStep> eventSteps=eventStepService.selectList(Condition.create().eq("event_type", 1));
        List<Series> seriess;
        Legend legend=new Legend();

        switch (sreachTaskDto.getChartType()){
            case ChartUtil.PIE:
                legend.setData(new ArrayList<>());

                seriess = new LinkedList<>();
                try {
                    new Bettime(sreachTaskDto);
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                for (EventStep et:eventSteps){
                     Series<DataBean> series=new Series<>();
                    series.setData(new ArrayList<>());
                    EntityWrapper<Taskassign> ew = new EntityWrapper<>();
                    ew.setEntity(new Taskassign());
                    if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                        ew.ge("assigntime", sreachTaskDto.getBeforeTime());
                    }
                    if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                        ew.le("assigntime", sreachTaskDto.getAfterTime());
                    }
                    ew.eq("status", et.getStatus());
                    legend.getData().add(et.getStep());
                    series.getData().add(new DataBean(et.getStep(),taskassignService.selectCount(ew)));
                    seriess.add(series);
                }
                //填充数据到map

                return ResponseData.success(new ChartVo(seriess,legend));
            default:
                //拆分时间
                Axis axis=new Axis();
                axis.setData(new LinkedHashSet<>());
                List<Date> dates=Bettime.getDates(sreachTaskDto.getBeforeTime(), sreachTaskDto.getAfterTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                legend.setData(new ArrayList<>());
                seriess = new LinkedList<>();

                try {
                   new Bettime(sreachTaskDto);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for (EventStep et:eventSteps){
                    Series<Integer> series=new Series<>();
                    series.setData(new ArrayList<>());
                    legend.getData().add(et.getStep());
                    series.setName(et.getStep());


                        EntityWrapper<Taskassign> ew = new EntityWrapper<>();
                        ew.setEntity(new Taskassign());
                        ew.setSqlSelect("date_format(assigntime, '%Y-%m-%d') time,count(id) size");
                        if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                            ew.ge("assigntime", sreachTaskDto.getBeforeTime());
                        }
                        if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                            ew.le("assigntime", sreachTaskDto.getAfterTime());
                        }
                        ew.eq("status", et.getStatus());
                        ew.groupBy("date_format(assigntime, '%Y-%m-%d')");
                      List<Map<String, Object>> maps= taskassignService.selectMaps(ew);
                    for (Date date :dates) {
                        axis.getData().add(sdf.format(date));
                        series.getData().add(0);
                    }
                    for (Map<String, Object> map:maps){
                        try {
                            series.getData().set(dates.indexOf(sdf.parse((String) map.get("time"))),((Number) map.get("size")).intValue());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    seriess.add(series);
                }



                return ResponseData.success(new ChartVo(seriess,legend,axis));
        }
    }

}
