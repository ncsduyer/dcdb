package cn.stylefeng.guns.modular.DcCompany.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.system.model.Company;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * apileadership控制器
 *
 * @author fengshuonan
 * @Date 2018-11-29 11:54:14
 */
@Controller
@RequestMapping("/apicompany")
public class CompanyController extends BaseController {

    private String PREFIX = "/DcCompany/company/";

    @Autowired
    private ICompanyService companyService;

    /**
     * 跳转到apileadership首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "company.html";
    }

    /**
     * 跳转到添加apileadership
     */
    @RequestMapping("/company_add")
    public String companyAdd() {
        return PREFIX + "company_add.html";
    }

    /**
     * 跳转到修改apileadership
     */
    @RequestMapping("/company_update/{companyId}")
    public String companyUpdate(@PathVariable Integer companyId, Model model) {
        Company company = companyService.selectById(companyId);
        model.addAttribute("item", company);
        LogObjectHolder.me().set(company);
        return PREFIX + "company_edit.html";
    }

    /**
     * 获取apileadership列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return companyService.selectList(null);
    }

    /**
     * 新增apileadership
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Company company) {
        companyService.insert(company);
        return SUCCESS_TIP;
    }

    /**
     * 删除apileadership
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer companyId) {
        companyService.deleteById(companyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改apileadership
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Company company) {
        companyService.updateById(company);
        return SUCCESS_TIP;
    }

    /**
     * apileadership详情
     */
    @RequestMapping(value = "/detail/{companyId}")
    @ResponseBody
    public Object detail(@PathVariable("companyId") Integer companyId) {
        return companyService.selectById(companyId);
    }
}
