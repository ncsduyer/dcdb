package cn.stylefeng.guns.modular.tdtask.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.util.ExportUtil;
import cn.stylefeng.guns.core.util.vo.ExportColSubVo;
import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.system.model.Task;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.AddTaskDto;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            @ApiImplicitParam(name = "title", value = "事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "督办责任人id数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事项状态 1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "责任单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "isExceed", value = "查询延期 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/getTaskList", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData getTaskList(@RequestBody(required = false) SreachTaskDto sreachTaskDto) {
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
    public ResponseData update(@RequestBody Task task) {
        return ResponseData.success(taskService.updateById(task));
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
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
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
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
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
    public void export(@RequestBody SreachTaskDto sreachTaskDto, HttpServletRequest request, HttpServletResponse response) {

        //excle模板文件名
        String template = "dcdbzhcx.xml";
        //sheet名
        String sheetName = "督查督办数据分析表";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        //循环次数
        int index = 1;

        //获取数据
        List<Task> tasks = taskService.getAll(sreachTaskDto);


        for (Task task : tasks) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(0);
            //交办时间
            ExportColVo assigntimeCol = new ExportColVo();
            //责任单位
            ExportColVo unitCol = new ExportColVo();
            // 督办责任人
            ExportColVo personCol = new ExportColVo();
            // 办理要求
            ExportColVo assignmemoCol = new ExportColVo();
            // 办理情况
            ExportColVo dealdescCol = new ExportColVo();
            //延期办理情况
            ExportColVo delaydescCol = new ExportColVo();
            //办结总时间
            ExportColVo usetimeCol = new ExportColVo();
            for (Taskassign ta : task.getTaskassigns()) {
                //设置总行数
                exportRowVo.setTotal(ta.getTaskassignUnits().size() + exportRowVo.getTotal());
                // 设置交办时间
                assigntimeCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), sdf.format(ta.getAssigntime())));
                for (TaskassignUnit tu : ta.getTaskassignUnits()) {
                    // 设置责任单位
                    unitCol.getCols().add(new ExportColSubVo(1, tu.getCompany().getTitle()));
                    // 设置督办责任人
                    personCol.getCols().add(new ExportColSubVo(1, tu.getPerson().getName()));
                    // 设置办理情况
                    if (ToolUtil.isNotEmpty(tu.getTaskassignUnitdeals()) && tu.getTaskassignUnitdeals().size() > 0) {
                        if (tu.getStatus() == 3) {
                            dealdescCol.getCols().add(new ExportColSubVo(1, ""));
                            // 设置延期办理情况
                            delaydescCol.getCols().add(new ExportColSubVo(1, tu.getTaskassignUnitdeals().get(0).getDealdesc()));
                        } else {
                            dealdescCol.getCols().add(new ExportColSubVo(1, tu.getTaskassignUnitdeals().get(0).getDealdesc()));
                            delaydescCol.getCols().add(new ExportColSubVo(1, ""));
                            // 设置延期办理情况
                        }
                    } else {
                        dealdescCol.getCols().add(new ExportColSubVo(1, ""));
                        delaydescCol.getCols().add(new ExportColSubVo(1, ""));
                    }
                }
                // 设置办理要求
                assignmemoCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), ta.getAssignmemo()));
                // 设置办结总时间
                usetimeCol.getCols().add(new ExportColSubVo(ta.getTaskassignUnits().size(), ta.getUseTime()));

            }
            //        编号
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(exportRowVo.getTotal(), String.valueOf(index))));
            //设置交办事项
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(exportRowVo.getTotal(), task.getTitle())));
            assigntimeCol.removeDuplication();
            unitCol.removeDuplication();
            personCol.removeDuplication();
            assignmemoCol.removeDuplication();
            dealdescCol.removeDuplication();
            delaydescCol.removeDuplication();
            usetimeCol.removeDuplication();
            exportRowVo.getColVos().add(assigntimeCol);
            exportRowVo.getColVos().add(unitCol);
            exportRowVo.getColVos().add(personCol);
            exportRowVo.getColVos().add(assignmemoCol);
            exportRowVo.getColVos().add(dealdescCol);
            exportRowVo.getColVos().add(delaydescCol);
            exportRowVo.getColVos().add(usetimeCol);
            index++;
            exportRowVos.add(exportRowVo);
        }

        ExportUtil.outExport(sreachTaskDto, response, template, sheetName, exportRowVos);

    }
}
