package cn.stylefeng.guns.modular.Infos.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.Infos.dto.AddInfoDto;
import cn.stylefeng.guns.modular.Infos.dto.SreachInfoDto;
import cn.stylefeng.guns.modular.Infos.service.IInfosService;
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
 * 区委信息控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 13:14:52
 */
@Api(tags = "区委信息")
@Controller
@RequestMapping("/api/infos")
public class InfosController extends BaseController {

    private String PREFIX = "/Infos/infos/";

    @Autowired
    private IInfosService infosService;

    /**
     * 获取区委信息单表列表
     */
    @ApiOperation(value = "区委信息单表列表")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getList() {
        return ResponseData.success(infosService.selectList(Condition.create().orderBy("id", false)));
    }

    /**
     * 获取区委信息列表
     */
    @ApiOperation(value = "信息列表")
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
    public ResponseData list(@RequestBody(required = false) SreachInfoDto sreachInfoDto) {
        ResponseData responseData = infosService.SreachPage(sreachInfoDto);
        return responseData;
    }

    /**
     * 新增信息
     */
    @ApiOperation(value = "新增信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@Validated @RequestBody AddInfoDto addInfoDto) {

        return infosService.add(addInfoDto);
    }


    /**
     * 修改信息
     */
    @ApiOperation(value = "修改信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody AddInfoDto addDto) {
        return infosService.edit(addDto);
    }



    /**
     * 信息详情
     */
    @ApiOperation(value = "信息单条详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "信息事项id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer id) {
        return infosService.selectWithManyById(id);
    }
    /**
     * 删除信息
     */
////    @ApiOperation(value = "删除信息")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "assignWorkId", value = "id", required = true, dataType = "Long"),
////    })
////    @RequestMapping(value = "/delete/{assignWorkId}",method = RequestMethod.GET)
////    @Permission
////    @ResponseBody
////    public ResponseData delete(@PathVariable("assignWorkId") Integer assignWorkId) {
////        infosService.deleteById(assignWorkId);
////        return SUCCESS_TIP;
////    }
    /**
     * 信息报表图表
     */
    @ApiOperation(value = "信息报表图表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "chartType", value = "图表类型，默认为柱状图 1：柱状图，2：饼图", required = false, dataType = "Long"),
    })
    @RequestMapping(value = "/chart", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseData chart(@RequestBody SreachInfoDto sreachInfoDto) {
        return infosService.sreachChart(sreachInfoDto);
    }
    /**
     * 信息报表列表
     */
    @ApiOperation(value = "信息报表列表")
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
    public ResponseData report(@RequestBody SreachInfoDto sreachInfoDto) {
        return infosService.getReports(sreachInfoDto);
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
            //上面是sreachInfoDto属性
            @ApiImplicitParam(name = "exportType", value = "导出类型 默认为excel 1：excel，2：doc", required = false, dataType = "Long"),

    })
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachInfoDto sreachInfoDto, HttpServletRequest request, HttpServletResponse response) {
        infosService.export(sreachInfoDto, response);
    }
}
