package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.ExcelUtil;
import cn.stylefeng.guns.modular.DcdbReport.service.IDcdbReportService;
import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 督办报表控制器
 *
 * @author fengshuonan
 * @Date 2018-11-21 17:32:51
 */
@Api(tags = "督察督办报表api")
@Controller
@RequestMapping("/api/dcdbReport")
public class ApiDcdbReportController extends BaseController {

    private String PREFIX = "/DcdbReport/dcdbReport/";

    @Autowired
    private IDcdbReportService dcdbReportService;


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
    @ApiOperation(value = "督查督办列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "dateGroup", value = "报表分组", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData list(@RequestBody(required = false) Map<String, String> map) throws ParseException {
        return dcdbReportService.getDcdbReports(map);
    }




    /**
     * 新增督办报表
     */
    @ApiOperation(value = "新增督办报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "reportName", value = "报表名称", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData add(@RequestBody(required = false) Map<String, String> map) throws ParseException {

        if (dcdbReportService.addReport(map)) {
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除督办报表
     */
    @ApiOperation(value = "删除督办报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "督查督办事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData delete(@PathVariable("id") Integer id) {
        dcdbReportService.deleteById(id);
        return ResponseData.success(200, "删除成功", null);
    }

    /**
     * 修改督办报表
     */
    @ApiOperation(value = "修改督办报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(@RequestBody DcdbReport dcdbReport) {
        dcdbReportService.updateById(dcdbReport);
        return SUCCESS_TIP;
    }

    /**
     * 督办报表详情
     */
    @ApiOperation(value = "督办报表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "督查督办事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer dcdbReportId) {
        return ResponseData.success(dcdbReportService.selectById(dcdbReportId));
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestParam(value = "dateGroup") String dateGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("dateGroup", dateGroup);
//        if (ToolUtil.isNotEmpty(beforeTime)){
//            map.put("beforeTime",beforeTime);
//        }
//        if (ToolUtil.isNotEmpty(afterTime)){
//            map.put("afterTime",afterTime);
//        }
        //获取数据
        List<Object> list = (List<Object>) dcdbReportService.getDcdbReports(map).getData();

        //excel标题
        String[] title = {"交办事项", "督办责任人", "责任单位", "办理要求", "交办时间", "办理用时", "事项进度"};

        //excel文件名
        String fileName = "督查督办信息表" + System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "督查督办信息表";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[][] content = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            DcdbReport obj = (DcdbReport) list.get(i);
            content[i][0] = obj.getTitle();
            content[i][1] = obj.getAgent();
            content[i][2] = obj.getCompany();
            content[i][3] = obj.getRequirement();
            content[i][4] = formatter.format(obj.getCreatedTime());
            content[i][5] = obj.getUseTime();
            content[i][6] = obj.getStatus();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

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
