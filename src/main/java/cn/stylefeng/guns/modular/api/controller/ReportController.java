package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 报表统计控制器
 *
 * @author fengshuonan
 * @Date 2018-12-02 11:31:27
 */
@Controller
@RequestMapping("/api/report")
public class ReportController extends BaseController {

    private String PREFIX = "/zhreport/report/";

    @Autowired
    private IReportService reportService;

    /**
     * 跳转到报表统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "report.html";
    }

    /**
     * 跳转到添加报表统计
     */
    @RequestMapping("/report_add")
    public String reportAdd() {
        return PREFIX + "report_add.html";
    }

    /**
     * 跳转到修改报表统计
     */
    @RequestMapping("/report_update/{reportId}")
    public String reportUpdate(@PathVariable Integer reportId, Model model) {
        Report report = reportService.selectById(reportId);
        model.addAttribute("item",report);
        LogObjectHolder.me().set(report);
        return PREFIX + "report_edit.html";
    }

    /**
     * 获取报表统计列表
     */
    @ApiOperation(value = "获取报表统计列表")
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseData list(String condition) {
        return reportService.selectList(null);
    }

    /**
     * 新增报表统计
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Report report) {
        reportService.insert(report);
        return SUCCESS_TIP;
    }

    /**
     * 删除报表统计
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer reportId) {
        reportService.deleteById(reportId);
        return SUCCESS_TIP;
    }

    /**
     * 修改报表统计
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Report report) {
        reportService.updateById(report);
        return SUCCESS_TIP;
    }

    /**
     * 报表统计详情
     */
    @RequestMapping(value = "/detail/{reportId}")
    @ResponseBody
    public Object detail(@PathVariable("reportId") Integer reportId) {
        return reportService.selectById(reportId);
    }
}
