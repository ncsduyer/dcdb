package cn.stylefeng.guns.modular.meeting.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.meeting.dto.AddMeetingDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.meeting.service.IMeetingService;
import cn.stylefeng.guns.modular.system.dao.MeetingMapper;
import cn.stylefeng.guns.modular.system.model.Meeting;
import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区委会议 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public ResponseData SreachPage(SreachMeetingDto sreachDto) {
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

    @Override
    public ResponseData add(AddMeetingDto addDto) {
        try{
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
    public ResponseData sreachChart(SreachMeetingDto sreachDto) {
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
                ew.orderBy("t.id,ta.id",false);
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
            page.setTotal(taskMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData getReports(SreachMeetingDto sreachDto) {

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
                        ew.gt("assigntime", sreachTaskDto.getBeforeTime());
                    }
                    if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                        ew.lt("assigntime", sreachTaskDto.getAfterTime());
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
                        ew.gt("assigntime", sreachTaskDto.getBeforeTime());
                    }
                    if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                        ew.lt("assigntime", sreachTaskDto.getAfterTime());
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

    @Override
    public ResponseData edit(AddMeetingDto addDto) {
        return null;
    }
}
