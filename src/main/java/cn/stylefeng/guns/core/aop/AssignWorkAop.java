package cn.stylefeng.guns.core.aop;

import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import cn.stylefeng.guns.modular.workFlowLog.service.IWorkFlowLogService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
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

    @Pointcut("execution(* cn.stylefeng.guns.modular.AssignWork.controller.AssignWorkController.add(..))")
    private void addAssignWork() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.AssignWork.controller.AssignWorkController.update(..))")
    private void editAssignWork() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.api.controller.ApiAssignWorkController.add(..))")
    private void addApiAssignWork() {

    }

    @Pointcut("execution(* cn.stylefeng.guns.modular.api.controller.ApiAssignWorkController.update(..))")
    private void editApiAssignWork() {

    }

    //插入时记录
    @AfterReturning(value = "addAssignWork()||addApiAssignWork()", returning = "responseData")
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

        }
    }

    //修改时记录
    @AfterReturning("editAssignWork()||editApiAssignWork()")
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
}
