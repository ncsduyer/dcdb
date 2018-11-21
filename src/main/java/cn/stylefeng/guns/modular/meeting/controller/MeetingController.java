package cn.stylefeng.guns.modular.meeting.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.meeting.service.IMeetingService;
import cn.stylefeng.guns.modular.system.model.Meeting;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 会议管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-23 17:15:07
 */
@Api(tags = "会议管理")
@Controller
@RequestMapping("/meeting")
public class MeetingController extends BaseController {

    private String PREFIX = "/meeting/meeting/";

    @Autowired
    private IMeetingService meetingService;

    /**
     * 跳转到会议管理首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "meeting.html";
    }

    /**
     * 跳转到添加会议管理
     */
    @RequestMapping("/meeting_add")
    @Permission
    public String meetingAdd() {
        return PREFIX + "meeting_add.html";
    }

    /**
     * 跳转到修改会议管理
     */
    @RequestMapping("/meeting_update/{meetingId}")
    @Permission
    public String meetingUpdate(@PathVariable Integer meetingId, Model model) {
        Meeting meeting = meetingService.selectById(meetingId);
        model.addAttribute("item", meeting);
        LogObjectHolder.me().set(meeting);
        return PREFIX + "meeting_edit.html";
    }

    /**
     * 获取会议管理列表
     */
    @ApiOperation(value = "获取会议管理列表")
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public ResponseData list(String condition) {
        return ResponseData.success(meetingService.selectList(null));
    }

    /**
     * 新增会议管理
     */
    @ApiOperation(value = "新增会议管理")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody Meeting meeting) {
        meetingService.insert(meeting);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议管理
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer meetingId) {
        meetingService.deleteById(meetingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议管理
     */
    @ApiOperation(value = "修改会议管理")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Meeting meeting) {
        meetingService.updateById(meeting);
        return SUCCESS_TIP;
    }

    /**
     * 会议管理详情
     */
    @ApiOperation(value = "会议管理详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}")
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer meetingId) {
        return ResponseData.success(meetingService.selectById(meetingId));
    }
}
