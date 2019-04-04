package cn.stylefeng.guns.modular.tdtaskassignUnit.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单位督办记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:04:22
 */
@Api(tags = "单位督办记录")
@RestController
@RequestMapping("/api/taskassignUnit")
public class TaskassignUnitController extends BaseController {

    private String PREFIX = "/tdtaskassignUnit/taskassignUnit/";

    @Autowired
    private ITaskassignUnitService taskassignUnitService;

    

    /**
     * 获取单位督办记录列表
     */
    @ApiOperation(value = "获取单位督办记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项事项id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "personid", value = "督办负责人id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @Permission
    public ResponseData list(@RequestParam(value = "id") Integer id, @RequestParam(value = "personid") Integer personid) {
        return ResponseData.success(taskassignUnitService.selectList1(id,personid));
    }

//    /**
//     * 新增单位督办记录
//     */
//    @ApiOperation(value = "新增单位督办记录")
//    @Permission
//    @RequestMapping(value = "/add")
//    @ResponseBody
//    public BigResponseData add(TaskassignUnit taskassignUnit) {
//        taskassignUnitService.insert(taskassignUnit);
//        return SUCCESS_TIP;
//    }

//    /**
//     * 删除单位督办记录
//     */
//    @RequestMapping(value = "/delete")
//    @ResponseBody
//    public BigResponseData delete(@RequestParam Integer taskassignUnitId) {
//        taskassignUnitService.deleteById(taskassignUnitId);
//        return SUCCESS_TIP;
//    }

    /**
     * 修改单位督办记录
     */
    @ApiOperation(value = "修改单位督办记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "单位督办记录列表", required = true, dataType = "Long"),
    })
    @Permission
    @RequestMapping(value = "/update",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData update(@RequestBody List<TaskassignUnit> taskassignUnit) {
       return taskassignUnitService.updateByTaskassignUnit(taskassignUnit);
    }
     /**
     * 修改单位督办记录
     */
    @ApiOperation(value = "标记单位延期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单位督办记录id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "未及时上报状态", required = true, dataType = "Long"),
    })
    @Permission
    @RequestMapping(value = "/updateIsTimeLy/{id}/{status}",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData updateIsTimeLy(@PathVariable("id") Integer id,@PathVariable("status") Integer status) {
        TaskassignUnit tu=new TaskassignUnit();
        tu.setId(id);
        tu.setIstimely(status);
        if (taskassignUnitService.updateById(tu)) {
            return ResponseData.success();
        }
       return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 单位督办记录详情
     */
    @ApiOperation(value = "单位督办记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项事项id", required = true, dataType = "Long"),
    })
    @Permission
    @RequestMapping(value = "/detail/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer taskassignUnitId) {
        return ResponseData.success(taskassignUnitService.selectById(taskassignUnitId));
    }
}
