package cn.stylefeng.guns.modular.MeetingRec.controller;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 会议督查记录管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 12:50:31
 */
@Api(tags = "会议督查")
@Controller
@RequestMapping("/api/meetingrec")
public class MeetingrecController extends BaseController {

    private String PREFIX = "/MeetingRec/meetingrec/";

    @Autowired
    private IMeetingrecService meetingrecService;

    /**
     * 获取会议督查记录管理列表
     */
    @ApiOperation(value = "会议督查记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkitemid", value = "检查项ID数组", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "部门id数组", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "所属事项id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData list(@Validated @RequestBody SreachMeetingRecDto sreachMeetingRecDto) {
        return meetingrecService.selectListByDto(sreachMeetingRecDto);
    }

    /**
     * 新增会议督查记录管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(Meetingrec meetingrec) {
        if (ToolUtil.isEmpty(meetingrec.getCreatetime())){
            meetingrec.setCreatetime(new DateTime());
        }
        if (meetingrecService.insert(meetingrec)){
        return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除会议督查记录管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam(value = "id") Integer id) {
        if (meetingrecService.deleteById(id)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 修改会议督查记录管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(Meetingrec meetingrec) {

        if (meetingrecService.updateById(meetingrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 会议督查记录管理详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return ResponseData.success(meetingrecService.selectById(id));
    }
}
