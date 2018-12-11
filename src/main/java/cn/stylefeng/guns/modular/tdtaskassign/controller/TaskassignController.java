package cn.stylefeng.guns.modular.tdtaskassign.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 交办事项时间控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:01:49
 */
@Controller
@RequestMapping("/api/taskassign")
public class TaskassignController extends BaseController {

    private String PREFIX = "/tdtaskassign/taskassign/";

    @Autowired
    private ITaskassignService taskassignService;

    /**
     * 跳转到交办事项时间首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "taskassign.html";
    }

    /**
     * 跳转到添加交办事项时间
     */
    @RequestMapping("/taskassign_add")
    public String taskassignAdd() {
        return PREFIX + "taskassign_add.html";
    }

    /**
     * 跳转到修改交办事项时间
     */
    @RequestMapping("/taskassign_update/{taskassignId}")
    public String taskassignUpdate(@PathVariable Integer taskassignId, Model model) {
        Taskassign taskassign = taskassignService.selectById(taskassignId);
        model.addAttribute("item",taskassign);
        LogObjectHolder.me().set(taskassign);
        return PREFIX + "taskassign_edit.html";
    }

    /**
     * 获取交办事项时间列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return taskassignService.selectList(null);
    }

    /**
     * 新增交办事项时间
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Taskassign taskassign) {
        taskassignService.insert(taskassign);
        return SUCCESS_TIP;
    }

    /**
     * 删除交办事项时间
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer taskassignId) {
        taskassignService.deleteById(taskassignId);
        return SUCCESS_TIP;
    }

    /**
     * 修改交办事项时间
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Taskassign taskassign) {
        taskassignService.updateById(taskassign);
        return SUCCESS_TIP;
    }

    /**
     * 交办事项时间详情
     */
    @RequestMapping(value = "/detail/{taskassignId}")
    @ResponseBody
    public Object detail(@PathVariable("taskassignId") Integer taskassignId) {
        return taskassignService.selectById(taskassignId);
    }
}
