package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.ITaskassignUnitdealService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 单位督办日志记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 16:04:49
 */
@Api(tags = "单位督办日志")
@RestController
@RequestMapping("/api/taskassignUnitdeal")
public class TaskassignUnitdealController extends BaseController {

    private String PREFIX = "/tdtaskassignUnitDeal/taskassignUnitdeal/";

    @Autowired
    private ITaskassignUnitdealService taskassignUnitdealService;
    @Autowired
    private IAssetService assetService;


    /**
     * 获取单位督办日志记录列表
     */
    @ApiOperation(value = "获取单位督办日志记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单位督办记录id", required = false, dataType = "String"),
    })
    @Permission
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData list(@RequestParam Integer id) {
        return ResponseData.success(taskassignUnitdealService.selectList(Condition.create().eq("taunitid",id).orderBy("createtime", true)));
    }

    /**
     * 新增单位督办日志记录
     */
    @ApiOperation(value = "新增单位督办日志记录")
    @Permission
    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData add(@RequestBody TaskassignUnitdeal taskassignUnitdeal) {
        if (ToolUtil.isEmpty(taskassignUnitdeal.getCreatorid())){
            taskassignUnitdeal.setCreatorid(ShiroKit.getUser().getId());
        }
       return taskassignUnitdealService.updateByTaskassignUnitdeal(taskassignUnitdeal);
    }

//    /**
//     * 删除单位督办日志记录
//     */
//    @RequestMapping(value = "/delete")
//    @ResponseBody
//    public ResponseData delete(@RequestParam Integer taskassignUnitdealId) {
//        taskassignUnitdealService.deleteById(taskassignUnitdealId);
//        return SUCCESS_TIP;
//    }

    /**
     * 修改单位督办日志记录
     */
    @ApiOperation(value = "修改单位督办日志记录")
    @Permission
    @RequestMapping(value = "/update",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData update(@RequestBody TaskassignUnitdeal taskassignUnitdeal) {
       return taskassignUnitdealService.updateByTaskassignUnitdeal(taskassignUnitdeal);

    }

    /**
     * 单位督办日志记录详情
     */
    @ApiOperation(value = "单位督办日志记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "交办事项事项id", required = true, dataType = "Long"),
    })
    @Permission
    @RequestMapping(value = "/detail/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
       TaskassignUnitdeal taskassignUnitdeal= taskassignUnitdealService.selectById(id);
       if(ToolUtil.isNotEmpty(taskassignUnitdeal.getPictures())){
        taskassignUnitdeal.setPictureList(assetService.selectList(Condition.create().in("id", taskassignUnitdeal.getPictures()).eq("status", 1)));
       }
        if(ToolUtil.isNotEmpty(taskassignUnitdeal.getFiles())) {
            taskassignUnitdeal.setFileList(assetService.selectList(Condition.create().in("id", taskassignUnitdeal.getFiles()).eq("status", 1)));
        }
        return ResponseData.success(taskassignUnitdeal);
    }
}
