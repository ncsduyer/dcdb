package cn.stylefeng.guns.modular.Docs.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.util.ExportUtil;
import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
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
    private IDocsService docsService;



    /**
     * 获取公文运转列表
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
    public ResponseData list(@RequestBody(required = false) SreachDocDto sreachDocDto) {
        ResponseData responseData = docsService.SreachPage(sreachDocDto);
        return responseData;
    }

    /**
     * 新增会议
     */
    @ApiOperation(value = "新增会议")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@Validated @RequestBody AddDocDto addDocDto) {

        return docsService.add(addDocDto);
    }


    /**
     * 修改会议
     */
    @ApiOperation(value = "修改会议")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody AddDocDto addDto) {
        return docsService.edit(addDto);
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
        return docsService.selectWithManyById(id);
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
////    public ResponseData delete(@PathVariable("assignWorkId") Integer assignWorkId) {
////        docsService.deleteById(assignWorkId);
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
    public ResponseData chart(@RequestBody SreachDocDto sreachDocDto) {
        return docsService.sreachChart(sreachDocDto);
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
    public ResponseData report(@RequestBody SreachDocDto sreachDocDto) {
        return docsService.getReports(sreachDocDto);
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
            //上面是sreachDocDto属性
            @ApiImplicitParam(name = "exportType", value = "导出类型 默认为excel 1：excel，2：doc", required = false, dataType = "Long"),

    })
    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void export(@RequestBody SreachDocDto sreachDocDto, HttpServletRequest request, HttpServletResponse response) {

        //excle模板文件名
        String template="dcdbzhcx.xml";
        //excel文件名

        //sheet名
        String sheetName = "督查督办数据分析表";
        docsService.getReports(sreachDocDto).getData();
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("dateGroup", dateGroup);
//        //获取数据
//        List<Object> list = (List<Object>) dcdbReportService.getDcdbReports(map).getData();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[][] content = new String[5][5];
//        for (int i = 0; i < list.size(); i++) {
//            content[i] = new String[title.length];
//            DcdbReport obj = (DcdbReport) list.get(i);
//            content[i][0] = obj.getTitle();
//            content[i][1] = obj.getAgent();
//            content[i][2] = obj.getCompany();
//            content[i][3] = obj.getRequirement();
//            content[i][4] = formatter.format(obj.getCreatedTime());
//            content[i][5] = obj.getUseTime();
//            content[i][6] = obj.getStatus();
//        }
        ExportUtil.outExport(sreachDocDto, response, template, sheetName, null);
    }
}
