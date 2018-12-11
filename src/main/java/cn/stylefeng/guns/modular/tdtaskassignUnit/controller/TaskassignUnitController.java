package cn.stylefeng.guns.modular.tdtaskassignUnit.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单位督办记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:04:22
 */
@Controller
@RequestMapping("/taskassignUnit")
public class TaskassignUnitController extends BaseController {

    private String PREFIX = "/tdtaskassignUnit/taskassignUnit/";

    @Autowired
    private ITaskassignUnitService taskassignUnitService;

    /**
     * 跳转到单位督办记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "taskassignUnit.html";
    }

    /**
     * 跳转到添加单位督办记录
     */
    @RequestMapping("/taskassignUnit_add")
    public String taskassignUnitAdd() {
        return PREFIX + "taskassignUnit_add.html";
    }

    /**
     * 跳转到修改单位督办记录
     */
    @RequestMapping("/taskassignUnit_update/{taskassignUnitId}")
    public String taskassignUnitUpdate(@PathVariable Integer taskassignUnitId, Model model) {
        TaskassignUnit taskassignUnit = taskassignUnitService.selectById(taskassignUnitId);
        model.addAttribute("item",taskassignUnit);
        LogObjectHolder.me().set(taskassignUnit);
        return PREFIX + "taskassignUnit_edit.html";
    }

    /**
     * 获取单位督办记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return taskassignUnitService.selectList(null);
    }

    /**
     * 新增单位督办记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TaskassignUnit taskassignUnit) {
        taskassignUnitService.insert(taskassignUnit);
        return SUCCESS_TIP;
    }

    /**
     * 删除单位督办记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer taskassignUnitId) {
        taskassignUnitService.deleteById(taskassignUnitId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单位督办记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TaskassignUnit taskassignUnit) {
        taskassignUnitService.updateById(taskassignUnit);
        return SUCCESS_TIP;
    }

    /**
     * 单位督办记录详情
     */
    @RequestMapping(value = "/detail/{taskassignUnitId}")
    @ResponseBody
    public Object detail(@PathVariable("taskassignUnitId") Integer taskassignUnitId) {
        return taskassignUnitService.selectById(taskassignUnitId);
    }
}
