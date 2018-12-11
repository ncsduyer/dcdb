package cn.stylefeng.guns.modular.tdtaskassignLog.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.TaskassignLog;
import cn.stylefeng.guns.modular.tdtaskassignLog.service.ITaskassignLogService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 督察督办运转记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:02:53
 */
@Controller
@RequestMapping("/taskassignLog")
public class TaskassignLogController extends BaseController {

    private String PREFIX = "/tdtaskassignLog/taskassignLog/";

    @Autowired
    private ITaskassignLogService taskassignLogService;

    /**
     * 跳转到督察督办运转记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "taskassignLog.html";
    }

    /**
     * 跳转到添加督察督办运转记录
     */
    @RequestMapping("/taskassignLog_add")
    public String taskassignLogAdd() {
        return PREFIX + "taskassignLog_add.html";
    }

    /**
     * 跳转到修改督察督办运转记录
     */
    @RequestMapping("/taskassignLog_update/{taskassignLogId}")
    public String taskassignLogUpdate(@PathVariable Integer taskassignLogId, Model model) {
        TaskassignLog taskassignLog = taskassignLogService.selectById(taskassignLogId);
        model.addAttribute("item",taskassignLog);
        LogObjectHolder.me().set(taskassignLog);
        return PREFIX + "taskassignLog_edit.html";
    }

    /**
     * 获取督察督办运转记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return taskassignLogService.selectList(null);
    }

    /**
     * 新增督察督办运转记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TaskassignLog taskassignLog) {
        taskassignLogService.insert(taskassignLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除督察督办运转记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer taskassignLogId) {
        taskassignLogService.deleteById(taskassignLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督察督办运转记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TaskassignLog taskassignLog) {
        taskassignLogService.updateById(taskassignLog);
        return SUCCESS_TIP;
    }

    /**
     * 督察督办运转记录详情
     */
    @RequestMapping(value = "/detail/{taskassignLogId}")
    @ResponseBody
    public Object detail(@PathVariable("taskassignLogId") Integer taskassignLogId) {
        return taskassignLogService.selectById(taskassignLogId);
    }
}
