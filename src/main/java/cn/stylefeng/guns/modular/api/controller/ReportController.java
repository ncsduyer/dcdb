package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.ExcelUtil;
import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.zhreport.dto.AddReportDto;
import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.guns.modular.zhreport.vo.ReportVo;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "group_id", value = "报表分组", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseData list(@RequestBody SreachReportDto sreachWorkDto) throws ParseException {
        return reportService.getList(sreachWorkDto);
    }

    /**
     * 新增报表统计
     */
    @ApiOperation(value = "新增报表统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "reportName", value = "报表名称", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody @Validated AddReportDto addReportDto) {
        return reportService.add(addReportDto);
    }

    /**
     * 删除报表统计
     */
    @ApiOperation(value = "删除报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "分组id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "companyId", value = "单位id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam(value = "groupId") Integer groupId,@RequestParam(value = "companyId") String company) {
        if (reportService.deleteByGidCompany(groupId,company)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 修改报表统计
     */
    @ApiOperation(value = "修改督办报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody @Validated Report report) {
        if (reportService.updateById(report)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 报表统计详情
     */
    @RequestMapping(value = "/detail/{reportId}")
    @ResponseBody
    public Object detail(@PathVariable("reportId") Integer reportId) {
        return reportService.selectList(Condition.create().eq("group_id",reportId));
    }
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestParam(value = "dateGroup") String dateGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SreachReportDto sreachReportDto=new SreachReportDto();
        sreachReportDto.setGroup_id(dateGroup);
        //获取数据
        List<ReportVo> list = (List<ReportVo>) reportService.getList(sreachReportDto).getData();

        //excle模板文件名
        String template="zhcx.xml";
        //excel文件名
        String fileName = "综合查询数据分析表" + ".xls";

        //sheet名
        String sheetName = "综合查询数据分析表";
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("dateGroup", dateGroup);
//        //获取数据
//        List<Object> list = (List<Object>) dcdbReportService.getDcdbReports(map).getData();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[][] content = new String[5][5];
//        for (int i = 0; i < list.size(); i++) {
//            content[i] = new String[title.length];
//            DcdbReport obj = (DcdbReport) list.get(i);
//            content[i][0] = obj.getTitle();
//            content[i][1] = obj.getAgent();
//            content[i][2] = obj.getCompany();
//            content[i][3] = obj.getRequirement();
//            content[i][4] = formatter.format(obj.getCreatedTime());
//            content[i][5] = obj.getUseTime();
//            content[i][6] = obj.getStatus();
//        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(template,sheetName,content);

        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
