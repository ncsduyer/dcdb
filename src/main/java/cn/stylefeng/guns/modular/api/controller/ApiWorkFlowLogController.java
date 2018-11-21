package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import cn.stylefeng.guns.modular.workFlowLog.service.IWorkFlowLogService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 流程记录日志控制器
 *
 * @author fengshuonan
 * @Date 2018-11-05 14:47:33
 */
@Api(tags = "流程记录")
@Controller
@RequestMapping("/api/apiworkFlowLog")
public class ApiWorkFlowLogController extends BaseController {

    private String PREFIX = "/workFlowLog/workFlowLog/";

    @Autowired
    private IWorkFlowLogService workFlowLogService;

    /**
     * 跳转到流程记录日志首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workFlowLog.html";
    }

    /**
     * 跳转到添加流程记录日志
     */
    @RequestMapping("/workFlowLog_add")
    public String workFlowLogAdd() {
        return PREFIX + "workFlowLog_add.html";
    }

    /**
     * 跳转到修改流程记录日志
     */
    @RequestMapping("/workFlowLog_update/{workFlowLogId}")
    public String workFlowLogUpdate(@PathVariable Integer workFlowLogId, Model model) {
        WorkFlowLog workFlowLog = workFlowLogService.selectById(workFlowLogId);
        model.addAttribute("item", workFlowLog);
        LogObjectHolder.me().set(workFlowLog);
        return PREFIX + "workFlowLog_edit.html";
    }

    /**
     * 获取流程记录日志列表
     */
    @ApiOperation(value = "获取流程记录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/list/{id}")
    @ResponseBody
    @Permission
    public ResponseData list(@PathVariable("id") Integer id) {
        List<WorkFlowLog> workFlowLogs = workFlowLogService.selectByManyId(id);
        return ResponseData.success(workFlowLogs);
    }

    /**
     * 新增流程记录日志
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission
    public ResponseData add(WorkFlowLog workFlowLog) {
        workFlowLogService.insert(workFlowLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除流程记录日志
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    public ResponseData delete(@RequestParam Integer workFlowLogId) {
        workFlowLogService.deleteById(workFlowLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改流程记录日志
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission
    public ResponseData update(WorkFlowLog workFlowLog) {
        workFlowLogService.updateById(workFlowLog);
        return SUCCESS_TIP;
    }

    /**
     * 流程记录日志详情
     */
    @RequestMapping(value = "/detail/{workFlowLogId}")
    @ResponseBody

    public ResponseData detail(@PathVariable("workFlowLogId") Integer workFlowLogId) {
        return ResponseData.success(workFlowLogService.selectById(workFlowLogId));
    }
}
