package cn.stylefeng.guns.modular.tdtask.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.*;
import cn.stylefeng.guns.core.util.vo.ExportColSubVo;
import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.system.dao.TaskMapper;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtask.vo.ReportsVo;
import cn.stylefeng.guns.modular.tdtask.vo.TaskVo;
import cn.stylefeng.guns.modular.tdtask.vo.chart.*;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignLog.service.ITaskassignLogService;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ITaskassignLogService taskassignLogService;
    @Autowired
    private IAppNoticeService appNoticeService;
    @Autowired
    private IUserService userService;
    @Override
    public ResponseData SreachPage(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Page<Task> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            new Bettime(sreachTaskDto);
            EntityWrapper<Task> ew = new EntityWrapper<>();
            ew.setEntity(new Task());
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
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("tu.id",false);
            }

            ArrayList<Task> arrayList = taskMapper.selectAsPage(page,ew.groupBy("tu.id"));
            for (Task task : arrayList) {
                for (Taskassign taskassign:task.getTaskassigns()){
                    taskassign.setUseTime(VoUtil.getUseTime(taskassign.getAssigntime(), taskassign.getEndtime()));
                }
            }
            page.setRecords(arrayList);
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
                if (ToolUtil.isEmpty(selectById(task.getId()))){
                    if (selectCount(Condition.create().eq("title", addTaskDto.getTitle()))>0){
                        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(),"交办事项重名");
                    }
                    insert(task);
                }
                Taskassign taskassign = new Taskassign();
                taskassign.setTaskid(task.getId());
                taskassign.setWorktype(addTaskDto.getWorktype());
                taskassign.setAssigntime(addTaskDto.getAssigntime());
                taskassign.setAssignmemo(addTaskDto.getAssignmemo());
                taskassign.setCharge(addTaskDto.getCharge());
                taskassign.setPhone(addTaskDto.getPhone());
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

                taskassign=taskassignService.selectByManyId(taskassign.getId());
                StringBuilder st=new StringBuilder();
                st.append(ShiroKit.getUser().getName());
                st.append(",新建了交办事项:交办时间:");
                st.append(VoUtil.getDate(taskassign.getAssigntime()));
                st.append("; 名称:");
                st.append(taskassign.getTask().getTitle());
                st.append(": 责任单位/责任人:");
                for (TaskassignUnit tu:taskassign.getTaskassignUnits()){
                    st.append(tu.getCompany().getTitle());
                    st.append("/");
                    st.append(tu.getPerson().getName());
                    st.append(" ");
                }
                st.append("; 交办要求:（");
                st.append(taskassign.getAssignmemo());
                st.append("）");

                TaskassignLog taskassignLog = new TaskassignLog();
                taskassignLog.setTaskid(taskassign.getTaskid());
                taskassignLog.setTassignid(taskassign.getId());
                taskassignLog.setCreatetime(new DateTime());
                taskassignLog.setLogcontent(st.toString());
                taskassignLog.setStatus(taskassign.getStatus());
                taskassignLogService.insert(taskassignLog);

//        List<TaskassignUnit> taskassignUnits=taskassignUnitService.selectList(Condition.create().eq("tassignid", taskassign.getId()));
                for (TaskassignUnit t :
                        taskassign.getTaskassignUnits()) {
                    //获取手机号
                    User user = userService.selectById(t.getPersonid());
                    AppNotice appNotice = new AppNotice();
                    appNotice.setTitle(taskassign.getTask().getTitle());
//                    appNotice.setContent(taskassignLog.getLogcontent());
                    appNotice.setContent(taskassign.getAssignmemo());
                    appNotice.setCreatetime(new DateTime());
                    appNotice.setType(1);
                    appNotice.setSendee(user.getName());
                    appNotice.setTel(user.getPhone());
                    appNotice.setSender_id(user.getId());
                    appNotice.setNow_status(taskassign.getStatus());
                    appNotice.setStep(taskassign.getEventStep().getStep());
                    appNoticeService.insert(appNotice);
                }



                return ResponseData.success(taskassign);
            } else {
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }

    }
    @Override
    public ResponseData edit(AddTaskDto addTaskDto) {
        try{
            if (ToolUtil.isNotEmpty(addTaskDto.getCompanyIds())) {

                Task task = new Task();
                BeanUtils.copyProperties(addTaskDto, task);
                if (ToolUtil.isNotEmpty(task.getId())){
                    updateById(task);
                }
                Taskassign taskassign = new Taskassign();
                taskassign.setId(addTaskDto.getTaid());
                taskassign.setTaskid(task.getId());
                taskassign.setWorktype(addTaskDto.getWorktype());
                taskassign.setAssigntime(addTaskDto.getAssigntime());
                taskassign.setAssignmemo(addTaskDto.getAssignmemo());
                taskassign.setClosememo(addTaskDto.getClosememo());
                taskassign.setCharge(addTaskDto.getCharge());
                taskassign.setPhone(addTaskDto.getPhone());
//                taskassign.setCreatetime(new DateTime());
//                taskassign.setCreatorid(ShiroKit.getUser().getId());
//                taskassign.setStatus(1);
                taskassignService.updateById(taskassign);
                TaskassignUnit taskassignUnit = null;
                ArrayList<TaskassignUnit> oldtus= (ArrayList<TaskassignUnit>) taskassignUnitService.selectList(Condition.create().eq("tassignid", taskassign.getId()));
               List<Integer> oldids = oldtus.stream().map(TaskassignUnit::getId).collect(Collectors.toList());
               List<Integer> removeids =new ArrayList();

//                循环插入交办单位
                for (TaskassignUnit map : addTaskDto.getCompanyIds()) {
                    taskassignUnit= new TaskassignUnit();
//                    taskassignUnit.setPersonid(map.getPersonid());
//                    taskassignUnit.setUnitid(map.getUnitid());
                    CopyUtils.copyProperties(map, taskassignUnit);
                    if (ToolUtil.isEmpty(taskassignUnit.getTassignid())){
                        taskassignUnit.setTassignid(taskassign.getId());
                    }
                    if (ToolUtil.isNotEmpty(taskassignUnit.getId())&&Arrays.asList(oldids).contains(taskassignUnit.getId())){
                       removeids.add(taskassignUnit.getId());
                    }
                    if (ToolUtil.isEmpty(taskassignUnit.getCreatetime())){
                        taskassignUnit.setCreatetime(new DateTime());
                    }
                    taskassignUnitService.insertOrUpdate(taskassignUnit);

                }
                for (Integer item:removeids
                     ) {
                    oldids.remove(item);
                }
                taskassignUnitService.deleteBatchIds(oldids);
//                taskassign=taskassignService.selectByManyId(taskassign.getId());
//                StringBuilder st=new StringBuilder();
//                st.append(ShiroKit.getUser().getName());
//                st.append(",新建了交办事项:交办时间:");
//                st.append(VoUtil.getDate(taskassign.getAssigntime()));
//                st.append("; 名称:");
//                st.append(taskassign.getTask().getTitle());
//                st.append(": 责任单位/责任人:");
//                for (TaskassignUnit tu:taskassign.getTaskassignUnits()){
//                    st.append(tu.getCompany().getTitle());
//                    st.append("/");
//                    st.append(tu.getPerson().getName());
//                    st.append(" ");
//                }
//                st.append("; 交办要求:（");
//                st.append(taskassign.getAssignmemo());
//                st.append("）");
//
//                TaskassignLog taskassignLog = new TaskassignLog();
//                taskassignLog.setTaskid(taskassign.getTaskid());
//                taskassignLog.setTassignid(taskassign.getId());
//                taskassignLog.setCreatetime(new DateTime());
//                taskassignLog.setLogcontent(st.toString());
//                taskassignLog.setStatus(taskassign.getStatus());
//                taskassignLogService.insert(taskassignLog);
//
////        List<TaskassignUnit> taskassignUnits=taskassignUnitService.selectList(Condition.create().eq("tassignid", taskassign.getId()));
//                for (TaskassignUnit t :
//                        taskassign.getTaskassignUnits()) {
//                    //获取手机号
//                    User user = userService.selectById(t.getPersonid());
//                    AppNotice appNotice = new AppNotice();
//                    appNotice.setTitle(taskassign.getTask().getTitle());
////                    appNotice.setContent(taskassignLog.getLogcontent());
//                    appNotice.setContent(taskassign.getAssignmemo());
//                    appNotice.setCreatetime(new DateTime());
//                    appNotice.setType(1);
//                    appNotice.setSendee(user.getName());
//                    appNotice.setTel(user.getPhone());
//                    appNotice.setSender_id(user.getId());
//                    appNotice.setNow_status(taskassign.getStatus());
//                    appNotice.setStep(taskassign.getEventStep().getStep());
//                    appNoticeService.insert(appNotice);
//                }



                return ResponseData.success();
            } else {
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
            ew.groupBy("t.id,ta.id,tu.id");
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
                if (et.getValue().size()<1){
                    continue;
                }
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

    @Override
    public List<Task> getAll(SreachTaskDto sreachTaskDto) {
        Bettime bettime= null;
        try {
            bettime = new Bettime(sreachTaskDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        ew.groupBy("t.id,ta.id,tu.id");
        if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
            ew.orderBy(sreachTaskDto.getOrder());
        }else{
            ew.orderBy("t.id,ta.id,tud.id",false);
        }
       List<Task> tasks=taskMapper.getAll(ew);
        for (Task task : tasks) {
            for (Taskassign taskassign:task.getTaskassigns()){
                taskassign.setUseTime(VoUtil.getUseTime(taskassign.getAssigntime(), taskassign.getEndtime()));
            }
        }
        return tasks;
    }
    @Override
    public void export(@RequestBody SreachTaskDto sreachTaskDto, HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            new Bettime(sreachTaskDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (ToolUtil.isEmpty(sreachTaskDto.getBeforeTime())){
            throw new Exception("必须选择开始时间");
        }
        if (ToolUtil.isEmpty(sreachTaskDto.getAfterTime())){
            throw new Exception("必须选择结束时间");
        }
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo head=  new ExportRowVo();
        head.setTotal(1);
        head.getColVos().add(new ExportColVo(new ExportColSubVo(1,9, "区委主要领导批交办事项督办台账("+sdf.format(sreachTaskDto.getBeforeTime())+"--"+sdf.format(sreachTaskDto.getAfterTime())+")")));
        titles.add(head);
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"编号")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"交办事项")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"交办时间")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"责任单位")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"督办责任人")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"办理要求及时限")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"办理情况")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"延期办结情况")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"办结总时间")));
        titles.add(title);
        //sheet名
        String sheetName = "督查督办数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        //循环次数
        int index = 1;
        //循环获取类型
        List<EventStep> eventSteps=eventStepService.selectList(Condition.create().eq("event_type", 1));
        for (EventStep et:eventSteps){
            sreachTaskDto.setStatus(new Integer[]{et.getStatus()});
            //获取数据
            List<Task> tasks = getAll(sreachTaskDto);
            if (tasks.size()<1){
                continue;
            }
            ExportRowVo exportRowVo1 = new ExportRowVo();
            exportRowVo1.setTotal(1);
            exportRowVo1.getColVos().add(new ExportColVo(new ExportColSubVo(1,9, et.getStep()+"("+tasks.size()+")")));
            exportRowVos.add(exportRowVo1);
            for (Task task : tasks) {
                ExportRowVo exportRowVo = new ExportRowVo();
                exportRowVo.setColVos(new ArrayList<>());
                exportRowVo.setTotal(0);
                //交办时间
                ExportColVo assigntimeCol = new ExportColVo();
                //责任单位
                ExportColVo unitCol = new ExportColVo();
                // 督办责任人
                ExportColVo personCol = new ExportColVo();
                // 办理要求
                ExportColVo assignmemoCol = new ExportColVo();
                // 办理情况
                ExportColVo dealdescCol = new ExportColVo();
                //延期办理情况
                ExportColVo delaydescCol = new ExportColVo();
                //办结总时间
                ExportColVo usetimeCol = new ExportColVo();
                for (Taskassign ta : task.getTaskassigns()) {
                    //设置总行数
                    exportRowVo.setTotal(ta.getTaskassignUnits().size() + exportRowVo.getTotal());
                    // 设置交办时间
                    assigntimeCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), sdf.format(ta.getAssigntime())));
                    for (TaskassignUnit tu : ta.getTaskassignUnits()) {
                        // 设置责任单位
                        unitCol.getCols().add(new ExportColSubVo(1, tu.getCompany().getTitle()));
                        // 设置督办责任人
                        personCol.getCols().add(new ExportColSubVo(1, tu.getPerson().getName()));
                        // 设置办理情况
                        if (ToolUtil.isNotEmpty(tu.getTaskassignUnitdeals()) && tu.getTaskassignUnitdeals().size() > 0) {
                            if (tu.getStatus() == 3) {
                                dealdescCol.getCols().add(new ExportColSubVo(1, ""));
                                // 设置延期办理情况
                                delaydescCol.getCols().add(new ExportColSubVo(1, tu.getTaskassignUnitdeals().get(0).getDealdesc()));
                            } else {
                                dealdescCol.getCols().add(new ExportColSubVo(1, tu.getTaskassignUnitdeals().get(0).getDealdesc()));
                                delaydescCol.getCols().add(new ExportColSubVo(1, ""));
                                // 设置延期办理情况
                            }
                        } else {
                            dealdescCol.getCols().add(new ExportColSubVo(1, ""));
                            delaydescCol.getCols().add(new ExportColSubVo(1, ""));
                        }
                    }
                    // 设置办理要求
                    assignmemoCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), ta.getAssignmemo()));
                    // 设置办结总时间
                    usetimeCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), ta.getUseTime()));

                }
                //        编号
                exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(exportRowVo.getTotal(), String.valueOf(index))));
                //设置交办事项
                exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(exportRowVo.getTotal(), task.getTitle())));
                assigntimeCol.removeDuplication();
                unitCol.removeDuplication();
                personCol.removeDuplication();
                assignmemoCol.removeDuplication();
                dealdescCol.removeDuplication();
                delaydescCol.removeDuplication();
                usetimeCol.removeDuplication();
                exportRowVo.getColVos().add(assigntimeCol);
                exportRowVo.getColVos().add(unitCol);
                exportRowVo.getColVos().add(personCol);
                exportRowVo.getColVos().add(assignmemoCol);
                exportRowVo.getColVos().add(dealdescCol);
                exportRowVo.getColVos().add(delaydescCol);
                exportRowVo.getColVos().add(usetimeCol);
                index++;
                exportRowVos.add(exportRowVo);

            }

        }

        ExportUtil.outExport(sreachTaskDto, response, titles, sheetName, exportRowVos);
    }


}
