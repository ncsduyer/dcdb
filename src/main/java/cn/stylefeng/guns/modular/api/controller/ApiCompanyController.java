package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.system.model.Company;
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
 * 督查责任单位管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:29:44
 */
@Api(tags = "督查责任单位")
@Controller
@RequestMapping("/api/apicompany")
public class ApiCompanyController extends BaseController {

    private String PREFIX = "/DcCompany/company/";

    @Autowired
    private ICompanyService companyService;

    /**
     * 跳转到督查责任单位管理首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "company.html";
    }

    /**
     * 跳转到添加督查责任单位管理
     */
    @RequestMapping("/company_add")
    @Permission
    public String companyAdd() {
        return PREFIX + "company_add.html";
    }

    /**
     * 跳转到修改督查责任单位管理
     */
    @RequestMapping("/company_update/{companyId}")
    @Permission
    public String companyUpdate(@PathVariable Integer companyId, Model model) {
        Company company = companyService.selectById(companyId);
        model.addAttribute("item", company);
        LogObjectHolder.me().set(company);
        return PREFIX + "company_edit.html";
    }

    /**
     * 获取督查责任单位管理列表
     */
    @ApiOperation(value = "获取督查责任单位列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData list() {
        return ResponseData.success(companyService.selectList(Condition.create().eq("status", 1).orderBy("id", true)));
    }

    /**
     * 新增督查责任单位管理
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody Company company) {
        companyService.insert(company);
        return SUCCESS_TIP;
    }

    /**
     * 删除督查责任单位管理
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer companyId) {
        companyService.deleteById(companyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查责任单位管理
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Company company) {
        companyService.updateById(company);
        return SUCCESS_TIP;
    }

    /**
     * 督查责任单位管理详情
     */
    @ApiOperation(value = "督查责任单位管理详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer companyId) {
        return ResponseData.success(companyService.selectById(companyId));
    }
}
