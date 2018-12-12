package cn.stylefeng.guns.modular.tdtaskassignUnit.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 单位督办记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:04:22
 */
@RestController
@RequestMapping("/taskassignUnit")
public class TaskassignUnitController extends BaseController {

    private String PREFIX = "/tdtaskassignUnit/taskassignUnit/";

    @Autowired
    private ITaskassignUnitService taskassignUnitService;

    

    /**
     * 获取单位督办记录列表
     */
    @ApiOperation(value = "获取单位督办记录列表")
    @ApiImplicitParams({

    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @Permission
    public ResponseData list() {
        return ResponseData.success(taskassignUnitService.selectList(Condition.create().orderBy("tassignid", false)));
    }

//    /**
//     * 新增单位督办记录
//     */
//    @ApiOperation(value = "新增单位督办记录")
//    @Permission
//    @RequestMapping(value = "/add")
//    @ResponseBody
//    public ResponseData add(TaskassignUnit taskassignUnit) {
//        taskassignUnitService.insert(taskassignUnit);
//        return SUCCESS_TIP;
//    }

//    /**
//     * 删除单位督办记录
//     */
//    @RequestMapping(value = "/delete")
//    @ResponseBody
//    public ResponseData delete(@RequestParam Integer taskassignUnitId) {
//        taskassignUnitService.deleteById(taskassignUnitId);
//        return SUCCESS_TIP;
//    }

    /**
     * 修改单位督办记录
     */
    @ApiOperation(value = "修改单位督办记录")
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(TaskassignUnit taskassignUnit) {
       return taskassignUnitService.updateByTaskassignUnit(taskassignUnit);
    }

    /**
     * 单位督办记录详情
     */
    @ApiOperation(value = "单位督办记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项事项id", required = true, dataType = "Long"),
    })
    @Permission
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer taskassignUnitId) {
        return ResponseData.success(taskassignUnitService.selectById(taskassignUnitId));
    }
}
