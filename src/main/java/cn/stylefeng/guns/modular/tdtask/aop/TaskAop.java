package cn.stylefeng.guns.modular.tdtask.aop;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.JsonUtils;
import cn.stylefeng.guns.core.util.SmsUtil;
import cn.stylefeng.guns.core.util.ValidateUtils;
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
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.Condition;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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


    @Pointcut("execution(* cn.stylefeng.guns.modular.tdtask.service.ITaskService.add(..))")
    private void addTaskService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService.updateByTaskassign(..))")
    private void editTaskService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService.insert(..))")
    private void sendSms() {

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
            Task task=taskService.selectById(taskassign.getTaskid());


            TaskassignLog taskassignLog = new TaskassignLog();
            taskassignLog.setTaskid(taskassign.getTaskid());
            taskassignLog.setTassignid(taskassign.getId());
            taskassignLog.setCreatetime(new DateTime());
            taskassignLog.setLogcontent(ShiroKit.getUser().getName()+"新建《"+task.getTitle()+"》");
            taskassignLog.setStatus(taskassign.getStatus());
            taskassignLogService.insert(taskassignLog);

        List<TaskassignUnit> taskassignUnits=taskassignUnitService.selectList(Condition.create().eq("tassignid", taskassign.getId()));
        for (TaskassignUnit t :
                taskassignUnits) {
            //获取手机号
            User user = userService.selectById(t.getPersonid());
            AppNotice appNotice = new AppNotice();
            appNotice.setTitle(task.getTitle());
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
        taskassign=taskassignService.selectById(taskassign.getId());
        Task task=taskService.selectById(taskassign.getTaskid());


        TaskassignLog taskassignLog = new TaskassignLog();
        taskassignLog.setTaskid(taskassign.getTaskid());
        taskassignLog.setTassignid(taskassign.getId());
        taskassignLog.setCreatetime(new DateTime());
        taskassignLog.setLogcontent(task.getTitle()+"变更状态为："+eventStep.getStep());
        taskassignLog.setStatus(taskassign.getStatus());
        taskassignLogService.insert(taskassignLog);
    }

    @AfterReturning("sendSms()")
    public void sendSms(JoinPoint joinPoint) throws ClientException {
        AppNotice appNotice=(AppNotice) joinPoint.getArgs()[0];
        if(!ValidateUtils.isMobile(appNotice.getTel())) {
            return ;
        }
        //发送短信
        ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
        json.put("code", appNotice.getTitle());
        SmsUtil.sendSms(null,appNotice,"SMS_146809603", JsonUtils.beanToJson(json), null);
    }
}
