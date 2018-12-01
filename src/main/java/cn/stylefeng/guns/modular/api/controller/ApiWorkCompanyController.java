package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 督查单位关联控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:24:46
 */
@Controller
@RequestMapping("/api/apiworkCompany")
@Api(tags = "督查单位关联api")
public class ApiWorkCompanyController extends BaseController {

    private String PREFIX = "/DcWorkCompany/workCompany/";

    @Autowired
    private IWorkCompanyService workCompanyService;

    /**
     * 跳转到督查单位关联首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "workCompany.html";
    }

    /**
     * 跳转到添加督查单位关联
     */
    @RequestMapping("/workCompany_add")
    @Permission
    public String workCompanyAdd() {
        return PREFIX + "workCompany_add.html";
    }

    /**
     * 跳转到修改督查单位关联
     */
    @RequestMapping("/workCompany_update/{workCompanyId}")
    @Permission
    public String workCompanyUpdate(@PathVariable Integer workCompanyId, Model model) {
        WorkCompany workCompany = workCompanyService.selectById(workCompanyId);
        model.addAttribute("item", workCompany);
        LogObjectHolder.me().set(workCompany);
        return PREFIX + "workCompany_edit.html";
    }

    /**
     * 获取督查单位关联列表
     */
    @ApiOperation(value = "获取督查单位关联列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "督办事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData list(@PathVariable("id") Integer id) {
        return ResponseData.success(workCompanyService.selectManyList(id));
    }

    /**
     * 新增督查单位关联
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody WorkCompany workCompany) {
        if (workCompanyService.insert1(workCompany)) {
            return SUCCESS_TIP;
        }
        return ResponseData.error(4001, "数据错误");
    }

    /**
     * 删除督查单位关联
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer workCompanyId) {
        workCompanyService.deleteById(workCompanyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查单位关联
     */
    @ApiOperation(value = "修改督查单位关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "督办事项列表", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Map<String, List<WorkCompany>> workCompanyList) {
        if (workCompanyService.updateByWorkCompany(workCompanyList)) return SUCCESS_TIP;

        return ResponseData.error(4002, "禁止修改数据");

    }


    /**
     * 督查单位关联详情
     */
    @ApiOperation(value = "督查单位关联详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return ResponseData.success(workCompanyService.selectWithManyById(id));
    }
}
