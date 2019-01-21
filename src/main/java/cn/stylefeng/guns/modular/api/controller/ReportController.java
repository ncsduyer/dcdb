package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 报表统计控制器
 *
 * @author fengshuonan
 * @Date 2018-12-02 11:31:27
 */
@Api(tags = "报表统计")
@Controller
@RequestMapping("/api/report")
public class ReportController extends BaseController {

    private String PREFIX = "/zhreport/report/";

    @Autowired
    private IReportService reportService;

    /**
     * 获取报表统计列表
     */
    @ApiOperation(value = "获取报表统计列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型，1督察督办数据，2责任单位数据，3人员数据，4事务相关统计", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData list(@RequestBody SreachReportDto sreachReportDto){
        return reportService.getReport(sreachReportDto);

    }
    /**
     * 获取报表统计列表
     */
    @ApiOperation(value = "获取报表统计列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型，1督察督办数据，2责任单位数据，3人员数据，4事务相关统计", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "chartType", value = "图表类型，默认为柱状图 1：柱状图，2：饼图", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/chart",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData chart(@RequestBody SreachReportDto sreachReportDto){
        return reportService.getChart(sreachReportDto);

    }


    /**
     * 导出报表
     *
     * @return
     */
    @ApiOperation(value = "获取报表统计列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型，1督察督办数据，2责任单位数据，3人员数据，4事务相关统计", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "exportType", value = "导出类型 默认为excel 1：excel，2：doc", required = false, dataType = "Long"),})

    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachReportDto sreachReportDto, HttpServletRequest request, HttpServletResponse response) throws Exception {

        reportService.export(sreachReportDto,response);
    }

}
