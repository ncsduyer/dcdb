package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.DcWorkType.service.IWorkTypeService;
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.system.model.WorkType;
import cn.stylefeng.guns.modular.system.service.IUserService;
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
 * 督查类型管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:21:35
 */
@Controller
@RequestMapping("/api/apiworkType")
@Api(tags = "督查类型api")
public class ApiWorkTypeController extends BaseController {

    private String PREFIX = "/DcWorkType/workType/";

    @Autowired
    private IWorkTypeService workTypeService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEventStepService eventStepService;

    /**
     * 跳转到督查类型管理首页
     */
    @RequestMapping("")

    public String index() {
        return PREFIX + "workType.html";
    }

    /**
     * 跳转到添加督查类型管理
     */
    @RequestMapping("/workType_add")

    public String workTypeAdd() {
        return PREFIX + "workType_add.html";
    }

    /**
     * 跳转到修改督查类型管理
     */
    @RequestMapping("/workType_update/{workTypeId}")

    @ResponseBody
    public String workTypeUpdate(@PathVariable Integer workTypeId, Model model) {
        WorkType workType = workTypeService.selectById(workTypeId);
        model.addAttribute("item", workType);
        LogObjectHolder.me().set(workType);
        return PREFIX + "workType_edit.html";
    }

    /**
     * 获取督查类型管理列表
     */
    @ApiOperation(value = "获取督查类型管理列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData list() {
        return ResponseData.success(workTypeService.selectList(null));
    }

    /**
     * 获取督办人列表
     */
    @ApiOperation(value = "获取督办人列表")
    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData Userlist() {
        return ResponseData.success(userService.selectList(Condition.create().setSqlSelect("id,name").eq("status", 1)));
    }

    /**
     * 获取督办流程列表
     */
    @ApiOperation(value = "获取督办流程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "event_type", value = "事项类型", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/eventtpyelist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData eventTpyelist(@RequestParam(value = "event_type", defaultValue = "1") Integer event_type) {
        return ResponseData.success(eventStepService.selectList(Condition.create().eq("event_type", event_type).orderBy("id", true)));
    }

    /**
     * 获取督查单位列表
     */
    @ApiOperation(value = "获取督查单位列表")
    @RequestMapping(value = "/companylist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData Companylist() {
        return ResponseData.success(companyService.selectList(Condition.create().eq("status", 1)));
    }

    /**
     * 新增督查类型管理
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody WorkType workType) {
        workTypeService.insert(workType);
        return SUCCESS_TIP;
    }

    /**
     * 删除督查类型管理
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer workTypeId) {
        workTypeService.deleteById(workTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查类型管理
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody WorkType workType) {
        workTypeService.updateById(workType);
        return SUCCESS_TIP;
    }

    /**
     * 督查类型管理详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer workTypeId) {
        return ResponseData.success(workTypeService.selectById(workTypeId));
    }
}
