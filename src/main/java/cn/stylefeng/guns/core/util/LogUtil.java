package cn.stylefeng.guns.core.util;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignLog;
import cn.stylefeng.guns.modular.tdtaskassignLog.service.ITaskassignLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LogUtil {
    @Autowired
    private ITaskassignLogService taskassignLogService;
    public static LogUtil logUtil;
    @PostConstruct
    public void init() {
        logUtil = this;
    }
    public static void addLog(Taskassign taskassign, String str) {
        TaskassignLog taskassignLog = new TaskassignLog();
        taskassignLog.setTaskid(taskassign.getTaskid());
        taskassignLog.setTassignid(taskassign.getId());
        taskassignLog.setCreatetime(new DateTime());
        taskassignLog.setLogcontent(str);
        taskassignLog.setStatus(taskassign.getStatus());
        logUtil.taskassignLogService.insert(taskassignLog);
    }
}
