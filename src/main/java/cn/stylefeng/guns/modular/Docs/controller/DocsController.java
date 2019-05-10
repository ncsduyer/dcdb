package cn.stylefeng.guns.modular.Docs.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * 公文运转控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 13:13:41
 */
@Api(tags = "公文运转")
@Controller
@RequestMapping("/api/docs")
public class DocsController extends BaseController {

    private String PREFIX = "/Docs/docs/";

    @Autowired
    private IDocService docsService;



    /**
     * 获取公文运转列表
     */
    @ApiOperation(value = "公文运转单表列表")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getList(@RequestBody(required = false) SreachDocDto sreachDto) {
        if (ToolUtil.isEmpty(sreachDto)) {
            sreachDto = new SreachDocDto();
        }
        try {
            new Bettime(sreachDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew = Condition.create();
        if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())) {
            ew.ge("assign_time", sreachDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())) {
            ew.le("assign_time", sreachDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getId())) {
            ew.eq("id", sreachDto.getId());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getCreatorids())) {
            ew.in("creatorid", sreachDto.getCreatorids());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getDoctypes())) {
            ew.in("doctype", sreachDto.getDoctypes());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getSenderIds())) {
            ew.in("sender_id", sreachDto.getSenderIds());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getExceed())) {
            ew.eq("exceed", sreachDto.getExceed());
        }
//            拼接查询条件
        if (ToolUtil.isNotEmpty(sreachDto.getTitle())) {
            ew.like("title", sreachDto.getTitle());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getStatus())) {
            ew.in("status", sreachDto.getStatus());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())) {
            ew.in("unitid", sreachDto.getCompanyIds());
        }
        ew.groupBy("id");
        if (ToolUtil.isNotEmpty(sreachDto.getOrder())) {
            ew.orderBy(sreachDto.getOrder());
        } else {
            ew.orderBy("id", false);
        }
        return ResponseData.success(docsService.selectList(ew));
    }
    /**
     * 获取公文运转列表
     */
    @ApiOperation(value = "公文运转单位统计列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData list(@RequestBody(required = false) SreachDocDto sreachDocDto) {
        ResponseData responseData = docsService.SreachPage(sreachDocDto);
        return responseData;
    }

    /**
     * 新增公文运转
     */
    @ApiOperation(value = "新增公文")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@Validated @RequestBody AddDocDto addDocDto) {

        return docsService.add(addDocDto);
    }


    /**
     * 修改公文运转
     */
    @ApiOperation(value = "修改公文")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody AddDocDto addDto) {
        return docsService.edit(addDto);
    }



    /**
     * 公文运转详情
     */
    @ApiOperation(value = "公文运转单条详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公文运转事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return docsService.selectWithManyById(id);
    }
    /**
     * 删除公文运转
     */
    @ApiOperation(value = "删除公文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData delete(@PathVariable("id") Integer id) {
        docsService.deleteMoreById(id);
        return SUCCESS_TIP;
    }
    /**
     * 公文运转报表图表
     */
    @ApiOperation(value = "公文运转报表图表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "chartType", value = "图表类型，默认为柱状图 1：柱状图，2：饼图", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/chart", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData chart(@RequestBody SreachDocDto sreachDocDto) {
        return docsService.sreachChart(sreachDocDto);
    }
    /**
     * 公文运转报表列表
     */
    @ApiOperation(value = "公文运转报表列表")
    @RequestMapping(value = "/report", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData report(@RequestBody SreachDocDto sreachDocDto) {
        return docsService.getReports(sreachDocDto);
    }
    /**
     * 导出报表
     *
     * @return
     */
    @ApiOperation(value = "导出报表")

    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachDocDto sreachDocDto, HttpServletRequest request, HttpServletResponse response) {
        docsService.export(sreachDocDto, response);
    }
}
