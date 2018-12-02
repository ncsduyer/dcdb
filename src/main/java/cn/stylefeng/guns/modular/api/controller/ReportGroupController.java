package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.reportGroup.service.IReportGroupService;
import cn.stylefeng.guns.modular.system.model.ReportGroup;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 综合报表统计分组控制器
 *
 * @author fengshuonan
 * @Date 2018-12-02 11:33:13
 */
@Controller
@RequestMapping("/api/reportGroup")
public class ReportGroupController extends BaseController {

    private String PREFIX = "/reportGroup/reportGroup/";

    @Autowired
    private IReportGroupService reportGroupService;

    /**
     * 跳转到综合报表统计分组首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "reportGroup.html";
    }

    /**
     * 跳转到添加综合报表统计分组
     */
    @RequestMapping("/reportGroup_add")
    public String reportGroupAdd() {
        return PREFIX + "reportGroup_add.html";
    }

    /**
     * 跳转到修改综合报表统计分组
     */
    @RequestMapping("/reportGroup_update/{reportGroupId}")
    public String reportGroupUpdate(@PathVariable Integer reportGroupId, Model model) {
        ReportGroup reportGroup = reportGroupService.selectById(reportGroupId);
        model.addAttribute("item",reportGroup);
        LogObjectHolder.me().set(reportGroup);
        return PREFIX + "reportGroup_edit.html";
    }

    /**
     * 获取综合报表统计分组列表
     */
    @ApiOperation(value = "获取综合报表统计分组列表")
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseData list() {
        return ResponseData.success(reportGroupService.selectList(Condition.create().orderBy("id", false).orderBy("after_time", false)));
    }

    /**
     * 新增综合报表统计分组
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ReportGroup reportGroup) {
        reportGroupService.insert(reportGroup);
        return SUCCESS_TIP;
    }

    /**
     * 删除综合报表统计分组
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer reportGroupId) {
        reportGroupService.deleteById(reportGroupId);
        return SUCCESS_TIP;
    }

    /**
     * 修改综合报表统计分组
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ReportGroup reportGroup) {
        reportGroupService.updateById(reportGroup);
        return SUCCESS_TIP;
    }

    /**
     * 综合报表统计分组详情
     */
    @RequestMapping(value = "/detail/{reportGroupId}")
    @ResponseBody
    public Object detail(@PathVariable("reportGroupId") Integer reportGroupId) {
        return reportGroupService.selectById(reportGroupId);
    }
}
