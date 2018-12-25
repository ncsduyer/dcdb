package cn.stylefeng.guns.modular.Infosrec.controller;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.Infosrec.service.IInfosrecService;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.system.model.Infosrec;
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
 * 区委信息上报管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 13:15:14
 */
@Api(tags = "区委信息上报")
@Controller
@RequestMapping("/api/infosrec")
public class InfosrecController extends BaseController {

    private String PREFIX = "/Infosrec/infosrec/";

    @Autowired
    private IInfosrecService infosrecService;

    

    /**
     * 获取区委信息上报管理列表
     */
    @ApiOperation(value = "区委信息上报列表")
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
        return infosrecService.selectListByDto(sreachMeetingRecDto);
    }

    /**
     * 新增区委信息上报管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(Infosrec infosrec) {
        if (ToolUtil.isEmpty(infosrec.getCreatetime())){
            infosrec.setCreatetime(new DateTime());
        }
        if (infosrecService.insert(infosrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除区委信息上报管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam(value = "id") Integer id) {
        if (infosrecService.deleteById(id)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 修改区委信息上报管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(Infosrec infosrec) {

        if (infosrecService.updateById(infosrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 区委信息上报管理详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return ResponseData.success(infosrecService.selectById(id));
    }
}
