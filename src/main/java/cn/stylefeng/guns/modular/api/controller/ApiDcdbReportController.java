package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcdbReport.service.IDcdbReportService;
import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    })
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData list(@RequestBody(required = false) Map<String, Date> map) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        Date frist = sdf.parse(sdf.format(calendar.getTime()));
        calendar.roll(Calendar.DATE, -1);
        Date last = sdf.parse(sdf.format(calendar.getTime()));
        if (ToolUtil.isEmpty(map)) {
            map = new HashMap<String, Date>();
            map.put("beforeTime", frist);
            map.put("afterTime", last);
        }
        Date beforeTime = ToolUtil.isNotEmpty(map.get("beforeTime")) ? map.get("beforeTime") : frist;
        Date afterTime = ToolUtil.isNotEmpty(map.get("afterTime")) ? map.get("afterTime") : last;
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {
            afterTime = sdf.parse(sdf.format(afterTime));
            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }
        return ResponseData.success(dcdbReportService.selectList(Condition.create().between("created_time", beforeTime, afterTime)));
    }

    /**
     * 新增督办报表
     */
    @ApiOperation(value = "督查督办列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData add(@RequestBody(required = false) Map<String, Date> map) throws ParseException {

        if (dcdbReportService.addReport(map)) {
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除督办报表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer id) {
        dcdbReportService.deleteById(id);
        return SUCCESS_TIP;
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
}
