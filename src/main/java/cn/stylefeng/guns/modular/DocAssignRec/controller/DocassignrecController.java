package cn.stylefeng.guns.modular.DocAssignRec.controller;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.Docs.service.IDocRecService;
import cn.stylefeng.guns.modular.Docs.service.IDocService;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.DocRec;
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

import java.util.List;

/**
 * 公文运转上报管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 13:14:25
 */
@Api(tags = "公文运转上报")
@Controller
@RequestMapping("/api/docassignrec")
public class DocassignrecController extends BaseController {

    private String PREFIX = "/DocAssignRec/docassignrec/";

    @Autowired
    private IDocRecService docassignrecService;
    @Autowired
    private IDocService docService;



    /**
     * 获取公文运转上报管理列表
     */
    @ApiOperation(value = "公文运转上报列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "关键词", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态 (0-未归档；1-已归档)", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "相关单位数组", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "order", value = "排序条件", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData list(@Validated @RequestBody SreachMeetingDto sreachMeetingRecDto) {
        return docService.selectAsMore(sreachMeetingRecDto);
    }

    /**
     * 新增公文运转上报督查记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(List<DocRec> recs) {
        for (DocRec rec:recs){
            if (ToolUtil.isEmpty(rec.getCreatetime())){
                rec.setCreatetime(new DateTime());
            }
        }

        if (docassignrecService.insertBatch(recs)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除公文运转上报督查记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam(value = "id") Integer id) {
        if (docassignrecService.deleteById(id)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 修改公文运转上报督查记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(DocRec docassignrec) {

        if (docassignrecService.updateById(docassignrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 公文运转上报督查记录详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return ResponseData.success(docassignrecService.selectById(id));
    }
}
