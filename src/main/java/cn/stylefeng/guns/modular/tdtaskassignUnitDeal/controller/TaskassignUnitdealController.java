package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.ITaskassignUnitdealService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单位督办日志记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:04:49
 */
@Controller
@RequestMapping("/taskassignUnitdeal")
public class TaskassignUnitdealController extends BaseController {

    private String PREFIX = "/tdtaskassignUnitDeal/taskassignUnitdeal/";

    @Autowired
    private ITaskassignUnitdealService taskassignUnitdealService;

    /**
     * 跳转到单位督办日志记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "taskassignUnitdeal.html";
    }

    /**
     * 跳转到添加单位督办日志记录
     */
    @RequestMapping("/taskassignUnitdeal_add")
    public String taskassignUnitdealAdd() {
        return PREFIX + "taskassignUnitdeal_add.html";
    }

    /**
     * 跳转到修改单位督办日志记录
     */
    @RequestMapping("/taskassignUnitdeal_update/{taskassignUnitdealId}")
    public String taskassignUnitdealUpdate(@PathVariable Integer taskassignUnitdealId, Model model) {
        TaskassignUnitdeal taskassignUnitdeal = taskassignUnitdealService.selectById(taskassignUnitdealId);
        model.addAttribute("item",taskassignUnitdeal);
        LogObjectHolder.me().set(taskassignUnitdeal);
        return PREFIX + "taskassignUnitdeal_edit.html";
    }

    /**
     * 获取单位督办日志记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return taskassignUnitdealService.selectList(null);
    }

    /**
     * 新增单位督办日志记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TaskassignUnitdeal taskassignUnitdeal) {
        taskassignUnitdealService.insert(taskassignUnitdeal);
        return SUCCESS_TIP;
    }

    /**
     * 删除单位督办日志记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer taskassignUnitdealId) {
        taskassignUnitdealService.deleteById(taskassignUnitdealId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单位督办日志记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TaskassignUnitdeal taskassignUnitdeal) {
        taskassignUnitdealService.updateById(taskassignUnitdeal);
        return SUCCESS_TIP;
    }

    /**
     * 单位督办日志记录详情
     */
    @RequestMapping(value = "/detail/{taskassignUnitdealId}")
    @ResponseBody
    public Object detail(@PathVariable("taskassignUnitdealId") Integer taskassignUnitdealId) {
        return taskassignUnitdealService.selectById(taskassignUnitdealId);
    }
}
