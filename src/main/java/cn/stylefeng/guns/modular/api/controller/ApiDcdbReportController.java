package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.DcdbReport.service.IDcdbReportService;
import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 督办报表控制器
 *
 * @author fengshuonan
 * @Date 2018-11-21 17:32:51
 */
@Controller
@RequestMapping("/api/dcdbReport")
public class ApiDcdbReportController extends BaseController {

    private String PREFIX = "/DcdbReport/dcdbReport/";

    @Autowired
    private IDcdbReportService dcdbReportService;
    @Autowired
    private IAssignWorkService assignWorkService;

    /**
     * 跳转到督办报表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dcdbReport.html";
    }

    /**
     * 跳转到添加督办报表
     */
    @RequestMapping("/dcdbReport_add")
    public String dcdbReportAdd() {
        return PREFIX + "dcdbReport_add.html";
    }

    /**
     * 跳转到修改督办报表
     */
    @RequestMapping("/dcdbReport_update/{dcdbReportId}")
    public String dcdbReportUpdate(@PathVariable Integer dcdbReportId, Model model) {
        DcdbReport dcdbReport = dcdbReportService.selectById(dcdbReportId);
        model.addAttribute("item", dcdbReport);
        LogObjectHolder.me().set(dcdbReport);
        return PREFIX + "dcdbReport_edit.html";
    }

    /**
     * 获取督办报表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return dcdbReportService.selectList(null);
    }

    /**
     * 新增督办报表
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody SreachWorkDto sreachWorkDto) {
        assignWorkService.SreachPage(sreachWorkDto);
//        dcdbReportService.insert();
        return SUCCESS_TIP;
    }

    /**
     * 删除督办报表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dcdbReportId) {
        dcdbReportService.deleteById(dcdbReportId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督办报表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DcdbReport dcdbReport) {
        dcdbReportService.updateById(dcdbReport);
        return SUCCESS_TIP;
    }

    /**
     * 督办报表详情
     */
    @RequestMapping(value = "/detail/{dcdbReportId}")
    @ResponseBody
    public Object detail(@PathVariable("dcdbReportId") Integer dcdbReportId) {
        return dcdbReportService.selectById(dcdbReportId);
    }
}
