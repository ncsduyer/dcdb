package cn.stylefeng.guns.modular.meeting.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.meeting.dto.AddMeetingDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.meeting.service.IMeetingService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
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

/**
 * 会议管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 12:48:16
 */
@Api(tags = "区委会议")
@Controller
@RequestMapping("/api/meeting")
public class MeetingController extends BaseController {

    private String PREFIX = "/meeting/meeting/";

    @Autowired
    private IMeetingService meetingService;

    /**
     * 获取会议单表列表
     */
    @ApiOperation(value = "会议单表列表")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getList() {
        return ResponseData.success(meetingService.selectList(Condition.create().orderBy("id", false)));
    }

    /**
     * 获取会议管理列表
     */
    @ApiOperation(value = "会议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "关键词", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态 (0-停用；1-启用)", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "相关单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "order", value = "排序条件", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData list(@RequestBody(required = false) SreachMeetingDto sreachMeetingDto) {
        ResponseData responseData = meetingService.SreachPage(sreachMeetingDto);
        return responseData;
    }

    /**
     * 新增会议
     */
    @ApiOperation(value = "新增会议")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@Validated @RequestBody AddMeetingDto addMeetingDto) {

        return meetingService.add(addMeetingDto);
    }


    /**
     * 修改会议
     */
    @ApiOperation(value = "修改会议")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody AddMeetingDto addDto) {
        return meetingService.edit(addDto);
    }



    /**
     * 会议详情
     */
    @ApiOperation(value = "会议单条详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会议事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return meetingService.selectWithManyById(id);
    }
    /**
     * 删除会议
     */
////    @ApiOperation(value = "删除会议")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "assignWorkId", value = "id", required = true, dataType = "Long"),
////    })
////    @RequestMapping(value = "/delete/{assignWorkId}",method = RequestMethod.GET)
////    @Permission
////    @ResponseBody
////    public BigResponseData delete(@PathVariable("assignWorkId") Integer assignWorkId) {
////        meetingService.deleteById(assignWorkId);
////        return SUCCESS_TIP;
////    }
    /**
     * 会议报表图表
     */
    @ApiOperation(value = "会议报表图表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "chartType", value = "图表类型，默认为柱状图 1：柱状图，2：饼图", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/chart", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData chart(@RequestBody SreachMeetingDto sreachMeetingDto) {
        return meetingService.sreachChart(sreachMeetingDto);
    }
    /**
     * 会议报表列表
     */
    @ApiOperation(value = "会议报表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "关键词", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态 (0-停用；1-启用)", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "相关单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "order", value = "排序条件", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/report", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData report(@RequestBody SreachMeetingDto sreachMeetingDto) {
        return meetingService.getReports(sreachMeetingDto);
    }
    /**
     * 导出报表
     *
     * @return
     */
    @ApiOperation(value = "导出报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "关键词", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态 (0-停用；1-启用)", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "相关单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "order", value = "排序条件", required = false, dataType = "Long"),
            //上面是sreachMeetingDto属性
            @ApiImplicitParam(name = "exportType", value = "导出类型 默认为excel 1：excel，2：doc", required = false, dataType = "Long"),

    })
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachMeetingDto sreachMeetingDto, HttpServletRequest request, HttpServletResponse response) {
        meetingService.export(sreachMeetingDto, response);

    }


}
