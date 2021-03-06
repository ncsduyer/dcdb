package cn.stylefeng.guns.modular.tdtask.aop;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.config.properties.SmsProperties;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.task.SmsTask;
import cn.stylefeng.guns.core.util.JsonUtils;
import cn.stylefeng.guns.core.util.ValidateUtils;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignLog.service.ITaskassignLogService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.Condition;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskAop {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ITaskassignService taskassignService;
    @Autowired
    private ITaskassignUnitService taskassignUnitService ;
    @Autowired
    private ITaskassignLogService taskassignLogService;
    @Autowired
    private IAppNoticeService appNoticeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEventStepService eventStepService;
    @Autowired
    private SmsTask smsTask;

    @Pointcut("execution(* cn.stylefeng.guns.modular.tdtask.service.ITaskService.add1(..))")
    private void addTaskService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService.updateByTaskassign(..))")
    private void editTaskService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService.insert(..))")
    private void sendSms() {

    }
    @Pointcut("execution(* cn.stylefeng.guns.modular.CopyRecordNotice.service.ICopyRecordNoticeService.insert*(..))")
    private void copySendSms() {

    }
    //插入时记录
    @AfterReturning(value = "addTaskService()", returning = "responseData")

    public void addTaskLog(JoinPoint joinPoint, Object responseData) {
            Taskassign taskassign = (Taskassign) ((ResponseData) responseData).getData();
            if (ToolUtil.isEmpty(taskassign)) {
                return;
            }
            if (taskassignLogService.selectOne(Condition.create()
                    .eq("tassignid", taskassign.getId()).eq("status", taskassign.getStatus())) != null) {
                return;
            }
//            Task task=taskService.selectById(taskassign.getTaskid());
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
            appNotice.setContent(taskassign.getAssignmemo());
            appNotice.setCreatetime(new DateTime());
            appNotice.setType(1);
            appNotice.setSendee(user.getName());
            appNotice.setTel(user.getPhone());
            appNotice.setSender_id(user.getId());
            appNoticeService.insert(appNotice);
        }


    }

    /**
     *修改时记录
     */
    @AfterReturning("editTaskService()")
    public void editTaskLog(JoinPoint joinPoint) {
        Taskassign taskassign = (Taskassign) joinPoint.getArgs()[0];
        if (taskassignLogService.selectOne(Condition.create().eq("tassignid", taskassign.getId()).eq("status", taskassign.getStatus())) != null) {
            return;
        }
        EventStep eventStep=eventStepService.selectOne(Condition.create().eq("event_type",1).eq("status", taskassign.getStatus()));
        taskassign=taskassignService.selectByManyId(taskassign.getId());
        TaskassignLog taskassignLog = new TaskassignLog();
        taskassignLog.setTaskid(taskassign.getTaskid());
        taskassignLog.setTassignid(taskassign.getId());
        taskassignLog.setCreatetime(new DateTime());
        if(taskassign.getStatus()>4){
            taskassignLog.setLogcontent(ShiroKit.getUser().getName()+"，归档了交办事项，归档时间："+ VoUtil.getDate(taskassign.getEndtime())+"；归档状态："+eventStep.getStep()+"；归档总结:（"+taskassign.getClosememo()+"）");
        taskassignLogService.insert(taskassignLog);
        }
//        else {
//            taskassignLog.setLogcontent("交办事项："+task.getTitle()+"，自动变更状态为："+eventStep.getStep());
//        }
        taskassignLog.setStatus(taskassign.getStatus());
    }

    @AfterReturning("sendSms()")
    public void sendSms(JoinPoint joinPoint) throws ClientException {
        AppNotice appNotice=(AppNotice) joinPoint.getArgs()[0];
        if(!ValidateUtils.isMobile(appNotice.getTel())) {
            return ;
        }
//        Boolean isSend_status=false;
        if(appNotice.getNow_status()>1){
            //发送短信
            ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
            JSONObject map= JSONObject.parseObject(appNotice.getContent());
            json.put("title", appNotice.getTitle().length()>20?appNotice.getTitle().substring(0,20):appNotice.getTitle());
            json.put("pople", (String) map.get("name")+(String) map.get("phone"));
            json.put("unit", (String) map.get("unit"));
            json.put("status", appNotice.getStep());
            if (((String)map.get("make")).length()<20){
                json.put("remark", (String)map.get("make"));
            }else {
                json.put("remark", ((String)map.get("make")).substring(0,16)+"...");
            }
            smsTask.sendSms(null,appNotice.getTel(),SmsProperties.getDealDcDbtmpCode(), JsonUtils.beanToJson(json), null);
        }else{
            //发送短信
            ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
            json.put("title", appNotice.getTitle().length()>20?appNotice.getTitle().substring(0,20):appNotice.getTitle());
            json.put("status", appNotice.getStep());
            if (appNotice.getContent().length()<20){
                json.put("remark", appNotice.getContent());
            }else {
                json.put("remark", appNotice.getContent().substring(0,20)+"...");
            }
            smsTask.sendSms(null,appNotice.getTel(),SmsProperties.getAddDcDbtmpCode(), JsonUtils.beanToJson(json), null);
        }

    }
    @AfterReturning("copySendSms()")
    public void copySendSms(JoinPoint joinPoint) throws ClientException {
        CopyRecordNotice copyRecordNotice=(CopyRecordNotice) joinPoint.getArgs()[0];
        if(!ValidateUtils.isMobile(copyRecordNotice.getTel())) {
            return ;
        }
        ObjectNode json = null;
        JSONObject map = null;
        String title=null;
        switch (copyRecordNotice.getType()){
            case 1:
                json = JsonUtils.getMapperInstance().createObjectNode();
                map= JSONObject.parseObject(copyRecordNotice.getJsonContent());
                title=(String) map.get("title");
                json.put("title",title.length()>20?title.substring(0,20):title);
                json.put("pople", (String) map.get("name")+(String) map.get("phone"));
                json.put("unit", (String) map.get("unit"));
                json.put("status", (String) map.get("status"));
                if (((String)map.get("make")).length()<20){
                    json.put("remark", (String)map.get("make"));
                }else {
                    json.put("remark", ((String)map.get("make")).substring(0,16)+"...");
                }
                smsTask.sendSms(null,copyRecordNotice.getTel(),SmsProperties.getDealDcDbtmpCode(), JsonUtils.beanToJson(json), null);
                break;
            case 2:
                json = JsonUtils.getMapperInstance().createObjectNode();
                map= JSONObject.parseObject(copyRecordNotice.getJsonContent());
                title=(String) map.get("title");
                json.put("title",title.length()>20?title.substring(0,20):title);
                json.put("date", (String) map.get("date"));
                json.put("datetime", (String) map.get("datetime"));
                json.put("check", (String) map.get("check"));
                smsTask.sendSms(null,copyRecordNotice.getTel(),SmsProperties.getMeetingtmpCode(), JsonUtils.beanToJson(json), null);
                break;
            case 3:
                json = JsonUtils.getMapperInstance().createObjectNode();
                map= JSONObject.parseObject(copyRecordNotice.getJsonContent());
                title=(String) map.get("title");
                json.put("title",title.length()>20?title.substring(0,20):title);
                json.put("date", (String) map.get("date"));
                json.put("datetime", (String) map.get("datetime"));
                json.put("check", (String) map.get("check"));
                smsTask.sendSms(null,copyRecordNotice.getTel(),SmsProperties.getDoctmpCode(), JsonUtils.beanToJson(json), null);
                break;
            case 4:
                json = JsonUtils.getMapperInstance().createObjectNode();
                map= JSONObject.parseObject(copyRecordNotice.getJsonContent());
                title=(String) map.get("title");
                json.put("title",title.length()>20?title.substring(0,20):title);
                json.put("date", (String) map.get("date"));
                json.put("datetime", (String) map.get("datetime"));
                json.put("check", (String) map.get("check"));
                smsTask.sendSms(null,copyRecordNotice.getTel(),SmsProperties.getInfotmpCode(), JsonUtils.beanToJson(json), null);
                break;
        }
    }
}
