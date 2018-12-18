package cn.stylefeng.guns.modular.tdtaskassignLog.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.TaskassignLog;
import cn.stylefeng.guns.modular.tdtaskassignLog.service.ITaskassignLogService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 督察督办运转记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:02:53
 */
@Api(tags = "督察督办运转记录")
@Controller
@RequestMapping("/api/taskassignLog")
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
    @ApiOperation(value = "督察督办运转记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项id", required = false, dataType = "String")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData list(@RequestParam Integer id) {
        return ResponseData.success(taskassignLogService.selectList(Condition.create().eq("tassignid", id).orderBy("status",true).orderBy("createtime", true)));
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
