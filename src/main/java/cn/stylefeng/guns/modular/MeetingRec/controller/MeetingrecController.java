package cn.stylefeng.guns.modular.MeetingRec.controller;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
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

import java.util.List;

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
    @Autowired
    private ICompanyService companyService;
    /**
     * 获取会议督查记录管理列表
     */
    @ApiOperation(value = "会议督查单位统计记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "必填:会议ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "checkitemid", value = "检查项ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "companyIds", value = "必填:单位id数组", required = true, dataType = "String"),
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
    public ResponseData add(List<Meetingrec> meetingrecs) {
        for (Meetingrec meetingrec:meetingrecs){
            if (ToolUtil.isEmpty(meetingrec.getCreatetime())){
                meetingrec.setCreatetime(new DateTime());
            }
        }
        if (meetingrecService.insertBatch(meetingrecs)){
        return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除会议督查记录管理
     */
    @ApiOperation(value = "删除单个单位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "meetingid", value = "必填:会议ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "unitid", value = "必填:部门id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/deleteByunit",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData deleteByunit(@RequestBody Meetingrec meetingrec) {
        if (ToolUtil.isEmpty(meetingrec.getMeetingid())||ToolUtil.isEmpty(meetingrec.getUnitid())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        if (meetingrecService.delete(Condition.create().eq("meetingid", meetingrec.getMeetingid()).eq("unitid", meetingrec.getUnitid()))){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }
    /**
     * 删除会议督查记录管理
     */
    @ApiOperation(value = "删除单个信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必填:D", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET,RequestMethod.POST})
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
    @ApiOperation(value = "修改区委信息单位个人记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必填:id", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "meetingid", value = "可选:会议ID", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "unitid", value = "可选:部门id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "checkitemid", value = "可选:检查项ID", required = false, dataType = "String")

    })
    @RequestMapping(value = "/update",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData update(@RequestBody Meetingrec meetingrec) {
        meetingrec.setCheckvalue("1");
        if (meetingrecService.updateById(meetingrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }
    /**
     * 修改会议督查记录管理
     */
    @ApiOperation(value = "批量修改区委信息单位个人记录")
    @RequestMapping(value = "/updateList",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData updateList(@RequestBody List<Meetingrec> meetingrecs) {
        for (Meetingrec meetingrec:meetingrecs){
            meetingrec.setCheckvalue("1");
        }
        if (meetingrecService.updateBatchById(meetingrecs)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 会议督查记录管理详情
     */
    @ApiOperation(value = "区委信息单个单位人员详情列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "meetingid", value = "必填:会议ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "unitid", value = "必填:部门id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/detail",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData detail(@RequestBody Meetingrec meetingrec) {
        if (ToolUtil.isEmpty(meetingrec.getMeetingid())||ToolUtil.isEmpty(meetingrec.getUnitid())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        try {
            return ResponseData.success(meetingrecService.getInfoByUnitid(Condition.create().eq("meetingid", meetingrec.getMeetingid()).eq("unitid", meetingrec.getUnitid())));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }

    }
}
