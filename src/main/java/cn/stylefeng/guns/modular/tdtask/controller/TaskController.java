package cn.stylefeng.guns.modular.tdtask.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private ITaskassignUnitService taskassignUnitService;


    /**
     * 跳转到添加交办事项
     */
    @ApiOperation(value = "状态统计")
    @RequestMapping(value = "/index",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData index(@RequestBody Map<String, Integer[]> map) {
        Map<String, Integer> ret = new HashMap<>();

        for (Map.Entry<String, Integer[]> entry : map.entrySet()) {
            EntityWrapper<Taskassign> ew = new EntityWrapper<>();
            ew.setEntity(new Taskassign());

            if (ToolUtil.isNotEmpty(entry.getValue())){
                ew.in("ta.status",entry.getValue());
                if(VoUtil.getMaxNum(entry.getValue())<5){
                    ew.in("tu.status", entry.getValue());
                }
            }
            ew.eq("tu.personid", ShiroKit.getUser().getId());
            ret.put(entry.getKey(), taskassignService.selectCountByStatus(ew));
        }
        return ResponseData.success(ret);
    }
    /**
     * 获取交办事项列表单表数据
     */
    @ApiOperation(value = "交办事项列表单表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData list() {
        return   ResponseData.success(taskService.selectList(Condition.create().eq("endstatus", 1).orderBy("id", false)));

    }


    /**
     * 获取交办事项列表
     */
    @ApiOperation(value = "交办事项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "可选参数，事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "可选参数，督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "creatorid", value = "可选参数，创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "可选参数，开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "可选参数，结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "可选参数，事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "可选参数，责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "可选参数，查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "可选参数，页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "可选参数，每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/getTaskList", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData getTaskList(@RequestBody(required = false) SreachTaskDto sreachTaskDto) {
        ResponseData responseData =taskassignUnitService.selectAsPage(sreachTaskDto);
        return responseData;

    }

    /**
     * 获取交办事项列表
     */
    @ApiOperation(value = "交办事项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "可选参数，事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "可选参数，督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "creatorid", value = "可选参数，创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "可选参数，开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "可选参数，结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "可选参数，事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "可选参数，责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "可选参数，查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "可选参数，页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "可选参数，每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/getTaskListByTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getTaskListByTask(@RequestBody(required = false) SreachTaskDto sreachTaskDto) {
        ResponseData responseData =taskassignService.SreachPage(sreachTaskDto);
        return responseData;

    }

    /**
     * 新增交办事项
     */
    @ApiOperation(value = "新增交办事项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "可选参数，督办事项id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "必选参数，事项名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "worktype", value = "必选参数，交办事件类型id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "assigntime", value = "必选参数，交办时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "assignmemo", value = "必选参数，交办事件要求", required = true, dataType = "String"),
            @ApiImplicitParam(name = "companyIds", value = "必选参数，[{unitid:责任单位id,personid:对应责任人id}] 数组 ", required = true, dataType = "String"),
    })
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
    public ResponseData update(@RequestBody AddTaskDto addTaskDto) {
        return taskService.edit(addTaskDto);
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
    @ApiOperation(value = "删除交办事项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData delete(@PathVariable("id") Integer id) {
        return taskService.deleteMoreById(id);
    }
    /**
     * 交办事项报表图表
     */
    @ApiOperation(value = "交办事项报表图表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "chartType", value = "图表类型，默认为柱状图 1：柱状图，2：饼图", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/chart", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData chart(@RequestBody SreachTaskDto sreachTaskDto) {
        return taskService.sreachChart(sreachTaskDto);
    }
    /**
     * 交办事项报表列表
     */
    @ApiOperation(value = "交办事项报表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "必选参数，开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "必选参数，结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/report", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData report(@RequestBody SreachTaskDto sreachTaskDto) {
        return taskService.getDcdbReports(sreachTaskDto);
    }
    /**
     * 导出报表
     *
     * @return
     */
    @ApiOperation(value = "导出报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "必选参数，开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "必选参数，结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
            //上面是sreachTaskDto属性
            @ApiImplicitParam(name = "exportType", value = "导出类型 默认为excel 1：excel，2：doc", required = false, dataType = "Long"),

    })
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachTaskDto sreachTaskDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        taskService.export(sreachTaskDto, response);
    }


}
