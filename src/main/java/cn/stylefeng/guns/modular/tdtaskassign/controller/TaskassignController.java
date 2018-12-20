package cn.stylefeng.guns.modular.tdtaskassign.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 交办事项时间控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:01:49
 */
@Api(tags = "交办事项内容")
@Controller
@RequestMapping("/api/taskassign")
public class TaskassignController extends BaseController {

    private String PREFIX = "/tdtaskassign/taskassign/";

    @Autowired
    private ITaskassignService taskassignService;

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
     * 修改交办事项
     */
    @ApiOperation(value = "修改交办事项")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Taskassign taskassign) {
        return taskassignService.updateByTaskassign(taskassign);
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
