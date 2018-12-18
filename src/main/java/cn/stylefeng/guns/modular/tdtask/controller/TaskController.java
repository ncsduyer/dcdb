package cn.stylefeng.guns.modular.tdtask.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 交办事项控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 15:59:04
 */
@Api(tags = "交办事项")
@RestController
@RequestMapping("/api/task")
public class TaskController extends BaseController {

    private String PREFIX = "/tdtask/task/";

    @Autowired
    private ITaskService taskService;
    @Autowired
    private ITaskassignService taskassignService;



    /**
     * 跳转到添加交办事项
     */
    @RequestMapping(value = "/index",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData index(@RequestBody Map<String, Integer[]> map) {
        Map<String, Integer> ret = new HashMap<>();
        for (Map.Entry<String, Integer[]> entry : map.entrySet()) {
            ret.put(entry.getKey(), taskassignService.selectCount(Condition.create().in("status", entry.getValue())));
        }
        return ResponseData.success(ret);
    }


    /**
     * 获取交办事项列表
     */
    @ApiOperation(value = "交办事项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    public ResponseData list(@RequestBody SreachTaskDto sreachTaskDto) {
        ResponseData responseData = taskService.SreachPage(sreachTaskDto);
        return responseData;

    }

    /**
     * 新增交办事项
     */
    @ApiOperation(value = "新增交办事项")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@Validated @RequestBody AddTaskDto addTaskDto) {

        return taskService.add(addTaskDto);
    }


    /**
     * 修改交办事项
     */
    @ApiOperation(value = "修改交办事项")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Taskassign taskassign) {
        return taskService.updateByTaskassign(taskassign);
    }



    /**
     * 交办事项详情
     */
    @ApiOperation(value = "交办事项单条详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return ResponseData.success(taskService.selectWithManyById(id));
    }
    /**
     * 删除交办事项
    */
////    @ApiOperation(value = "删除交办事项")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "assignWorkId", value = "id", required = true, dataType = "Long"),
////    })
////    @RequestMapping(value = "/delete/{assignWorkId}",method = RequestMethod.GET)
////    @Permission
////    @ResponseBody
////    public ResponseData delete(@PathVariable("assignWorkId") Integer assignWorkId) {
////        taskService.deleteById(assignWorkId);
////        return SUCCESS_TIP;
////    }
}
