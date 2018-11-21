package cn.stylefeng.guns.modular.DcWcInfos.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.DcWcInfos.service.IWcInfosService;
import cn.stylefeng.guns.modular.system.model.WcInfos;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 督查单位进度控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:25:33
 */
@Controller
@RequestMapping("/apiwcInfos")
@Api(tags = "督查进度控制api")
public class WcInfosController extends BaseController {

    private String PREFIX = "/DcWcInfos/wcInfos/";

    @Autowired
    private IWcInfosService wcInfosService;

    /**
     * 跳转到督查单位进度首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "wcInfos.html";
    }

    /**
     * 跳转到添加督查单位进度
     */
    @RequestMapping("/wcInfos_add")
    @Permission
    public String wcInfosAdd() {
        return PREFIX + "wcInfos_add.html";
    }

    /**
     * 跳转到修改督查单位进度
     */
    @RequestMapping("/wcInfos_update/{wcInfosId}")
    @Permission
    public String wcInfosUpdate(@PathVariable Integer wcInfosId, Model model) {
        WcInfos wcInfos = wcInfosService.selectById(wcInfosId);
        model.addAttribute("item", wcInfos);
        LogObjectHolder.me().set(wcInfos);
        return PREFIX + "wcInfos_edit.html";
    }

    /**
     * 获取督查单位进度列表
     */
    @ApiOperation(value = "获取督查单位进度列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "责任单位id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData list(@PathVariable("id") Integer id) {
        return ResponseData.success(wcInfosService.selectList(Condition.create()
                .eq("pid", id).orderBy("created_time")));
    }

    /**
     * 新增督查单位进度
     */
    @ApiOperation(value = "新增督查单位进度")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody WcInfos wcInfos) {
        if (wcInfos.getPid() == null) {
            return ResponseData.error("错误的数据");
        }
        if (wcInfos.getCreatedTime() == null) {
            wcInfos.setCreatedTime(new Date());
        }
        if (wcInfos.getSubmitter() == null) {
            wcInfos.setSubmitter(ShiroKit.getUser().getName());
        }
        wcInfosService.insert(wcInfos);
        return SUCCESS_TIP;
    }

    /**
     * 删除督查单位进度
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer wcInfosId) {
        wcInfosService.deleteById(wcInfosId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查单位进度
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody WcInfos wcInfos) {
        wcInfosService.updateById(wcInfos);
        return SUCCESS_TIP;
    }

    /**
     * 督查单位进度详情
     */
    @ApiOperation(value = "督查单位进度详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("id") Integer wcInfosId) {
        return ResponseData.success(wcInfosService.selectById(wcInfosId));
    }
}
