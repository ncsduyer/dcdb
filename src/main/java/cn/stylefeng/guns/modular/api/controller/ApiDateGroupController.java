package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DateGroup.service.IDateGroupService;
import cn.stylefeng.guns.modular.system.model.DateGroup;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 报表时间分组控制器
 *
 * @author fengshuonan
 * @Date 2018-11-24 11:53:31
 */
@Controller
@RequestMapping("/api/dateGroup")
public class ApiDateGroupController extends BaseController {

    private String PREFIX = "/DateGroup/dateGroup/";

    @Autowired
    private IDateGroupService dateGroupService;

    /**
     * 跳转到报表时间分组首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dateGroup.html";
    }

    /**
     * 跳转到添加报表时间分组
     */
    @RequestMapping("/dateGroup_add")
    public String dateGroupAdd() {
        return PREFIX + "dateGroup_add.html";
    }

    /**
     * 跳转到修改报表时间分组
     */
    @RequestMapping("/dateGroup_update/{dateGroupId}")
    public String dateGroupUpdate(@PathVariable Integer dateGroupId, Model model) {
        DateGroup dateGroup = dateGroupService.selectById(dateGroupId);
        model.addAttribute("item", dateGroup);
        LogObjectHolder.me().set(dateGroup);
        return PREFIX + "dateGroup_edit.html";
    }

    /**
     * 获取报表时间分组列表
     */
    @ApiOperation(value = "获取报表时间分组列表")
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseData list() {
        return ResponseData.success(dateGroupService.selectList(Condition.create().orderBy("id", false).orderBy("after_time", false)));
    }

    /**
     * 新增报表时间分组
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DateGroup dateGroup) {
        dateGroupService.insert(dateGroup);
        return SUCCESS_TIP;
    }

    /**
     * 删除报表时间分组
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dateGroupId) {
        dateGroupService.deleteById(dateGroupId);
        return SUCCESS_TIP;
    }

    /**
     * 修改报表时间分组
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DateGroup dateGroup) {
        dateGroupService.updateById(dateGroup);
        return SUCCESS_TIP;
    }

    /**
     * 报表时间分组详情
     */
    @RequestMapping(value = "/detail/{dateGroupId}")
    @ResponseBody
    public Object detail(@PathVariable("dateGroupId") Integer dateGroupId) {
        return dateGroupService.selectById(dateGroupId);
    }
}
