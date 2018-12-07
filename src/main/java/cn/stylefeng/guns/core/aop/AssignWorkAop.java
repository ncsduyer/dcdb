package cn.stylefeng.guns.core.aop;

import cn.stylefeng.guns.core.util.JsonUtils;
import cn.stylefeng.guns.core.util.SmsUtil;
import cn.stylefeng.guns.core.util.ValidateUtils;
import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.system.model.AppNotice;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.workFlowLog.service.IWorkFlowLogService;
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

import java.util.Date;

@Aspect
@Component
public class AssignWorkAop {
    @Autowired
    private IWorkFlowLogService workFlowLogService;
    @Autowired
    private IAppNoticeService appNoticeService;
    @Autowired
    private IUserService userService;


    @Pointcut("execution(* cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService.add(..))")
    private void addAssignWorkService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService.update1(..))")
    private void editAssignWorkService() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService.insert(..))")
    private void sendSms() {

    }
    //插入时记录
    @AfterReturning(value = "addAssignWorkService()", returning = "responseData")

    public void addWorkFlowLog(JoinPoint joinPoint, Object responseData) {
        AssignWork assignWork = (AssignWork) ((ResponseData) responseData).getData();
        if (ToolUtil.isEmpty(assignWork)) {
            return;
        } else {
            if (workFlowLogService.selectOne(Condition.create()
                    .eq("a_w_id", assignWork.getId())) != null) {
                return;
            }
            WorkFlowLog workFlowLog = new WorkFlowLog();
            workFlowLog.setaWId(assignWork.getId());
            workFlowLog.setEventType(1);
            workFlowLog.setCreatedTime(new Date());
            workFlowLog.setStep(1);
            workFlowLogService.insert(workFlowLog);
            //获取手机号
            User user = userService.selectById(assignWork.getAgent());
            AppNotice appNotice = new AppNotice();
            appNotice.setTitle(assignWork.getTitle());
            appNotice.setContent(assignWork.getRequirement());
            appNotice.setCreatetime(new Date());
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
    @AfterReturning("editAssignWorkService()")
    public void editWorkFlowLog(JoinPoint joinPoint) {
        AssignWork assignWork = (AssignWork) joinPoint.getArgs()[0];
        if (workFlowLogService.selectOne(Condition.create()
                .eq("a_w_id", assignWork.getId()).eq("step", assignWork.getStatus())) != null) {
            return;
        }
        WorkFlowLog workFlowLog = new WorkFlowLog();
        workFlowLog.setaWId(assignWork.getId());
        workFlowLog.setEventType(1);
        workFlowLog.setCreatedTime(new Date());
        workFlowLog.setStep(assignWork.getStatus());
        workFlowLogService.insert(workFlowLog);
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
