package cn.stylefeng.guns.modular.AssignWork.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.AssignWork.dto.AddWorkDto;
import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 督查督办管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:30:06
 */
@Api(tags = "督察督办功能api")
@Controller
@RequestMapping("/apiassignWork")
public class AssignWorkController extends BaseController {

    private String PREFIX = "/AssignWork/assignWork/";

    @Autowired
    private IAssignWorkService assignWorkService;
    @Autowired
    private IWorkCompanyService workCompanyService;

    /**
     * 跳转到督查督办管理首页
     */
    @RequestMapping("")

    public String index() {
        return PREFIX + "assignWork.html";
    }

    /**
     * 跳转到添加督查督办管理
     */
    @RequestMapping("/assignWork_add")

    public String assignWorkAdd() {
        return PREFIX + "assignWork_add.html";
    }

    /**
     * 跳转到修改督查督办管理
     */

    @RequestMapping(value = "/assignWork_update/{assignWorkId}", method = RequestMethod.GET)

    @ResponseBody
    public AssignWork assignWorkUpdate(@PathVariable Integer assignWorkId) {
        AssignWork assignWork = assignWorkService.selectById(assignWorkId);
        LogObjectHolder.me().set(assignWork);
        return assignWork;
    }

    /**
     * 获取督查督办管理列表
     */
    @ApiOperation(value = "督查督办列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "事项名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "agent", value = "督办责任人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事项状态 1已下发未反馈,2已反馈,3欠进度,4达进度,5超进度,6已完成,7现场办结,8允许延期,9手动关闭", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "delayStatus", value = "事项延期状态 1:延期 ", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)

    @ResponseBody
    public ResponseData list(@RequestBody(required = false) SreachWorkDto sreachWorkDto) {
        if (ToolUtil.isEmpty(sreachWorkDto)) {
            sreachWorkDto = new SreachWorkDto();
        }
        return ResponseData.success(assignWorkService.SreachPage(sreachWorkDto));
    }

    /**
     * 新增督查督办管理
     */
    @ApiOperation(value = "新增督查督办")
    @RequestMapping(value = "/add", method = RequestMethod.POST)

    @ResponseBody
    @Transactional
    public ResponseData add(@RequestBody AddWorkDto addWorkDto) {
        if (ToolUtil.isNotEmpty(addWorkDto.getCompanyIds())) {
            AssignWork assignWork = new AssignWork();
            BeanUtils.copyProperties(addWorkDto, assignWork);
            assignWork.setCreatedId(ShiroKit.getUser().getId());
            assignWork.setCreatedTime(new Date());
            assignWork.setStatus(1);
            assignWorkService.insert(assignWork);
            WorkCompany workCompany = new WorkCompany();
            workCompany.setaWId(assignWork.getId());

            for (Integer id : addWorkDto.getCompanyIds()
                    ) {
                workCompany.setCompanyId(id);
                workCompanyService.insert1(workCompany);
            }

            return ResponseData.success(assignWork);
        } else {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    /**
     * 删除督查督办管理
     */
//    @ApiOperation(value = "删除督查督办")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "assignWorkId", value = "id", required = true, dataType = "Long"),
//    })
//    @RequestMapping(value = "/delete/{assignWorkId}",method = RequestMethod.GET)
//
//    @ResponseBody
//    public ResponseData delete(@PathVariable("assignWorkId") Integer assignWorkId) {
//        assignWorkService.deleteById(assignWorkId);
//        return SUCCESS_TIP;
//    }

    /**
     * 修改督查督办管理
     */
    @ApiOperation(value = "修改督查督办")
    @RequestMapping(value = "/update", method = RequestMethod.POST)

    @ResponseBody
    public ResponseData update(@RequestBody AssignWork assignWork) {

        if (assignWork.getStatus() == 9 || assignWork.getStatus() == 6) {
            AssignWork assignWork1 = new AssignWork();
            assignWork1.setId(assignWork.getId());
            assignWork1.setStatus(assignWork.getStatus());
            assignWork1.setRemarks(assignWork.getRemarks());
            assignWork.setEndTime(new Date());
            assignWorkService.updateById(assignWork1);
        } else {
            assignWorkService.updateById(assignWork);
        }

        return SUCCESS_TIP;
    }

    /**
     * 督查督办管理详情
     */
    @ApiOperation(value = "督查督办单条详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "督查督办事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)

    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer assignWorkId) {
        return ResponseData.success(assignWorkService.selectWithManyById(assignWorkId));
    }
}
