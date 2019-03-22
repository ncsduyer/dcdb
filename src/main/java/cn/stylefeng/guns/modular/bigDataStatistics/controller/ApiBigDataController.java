package cn.stylefeng.guns.modular.bigDataStatistics.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.system.model.AppNotice;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * app消息通知控制器
 *
 * @author fengshuonan
 * @Date 2018-11-22 14:25:25
 */
@Controller
@RequestMapping("/appNotice")
public class ApiBigDataController extends BaseController {

    private String PREFIX = "/AppNotice/appNotice/";

    @Autowired
    private IAppNoticeService appNoticeService;

    /**
     * 跳转到app消息通知首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "appNotice.html";
    }

    /**
     * 跳转到添加app消息通知
     */
    @RequestMapping("/appNotice_add")
    public String appNoticeAdd() {
        return PREFIX + "appNotice_add.html";
    }

    /**
     * 跳转到修改app消息通知
     */
    @RequestMapping("/appNotice_update/{appNoticeId}")
    public String appNoticeUpdate(@PathVariable Integer appNoticeId, Model model) {
        AppNotice appNotice = appNoticeService.selectById(appNoticeId);
        model.addAttribute("item", appNotice);
        LogObjectHolder.me().set(appNotice);
        return PREFIX + "appNotice_edit.html";
    }

    /**
     * 获取app消息通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return appNoticeService.selectList(null);
    }

    /**
     * 新增app消息通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AppNotice appNotice) {
        appNoticeService.insert(appNotice);
        return SUCCESS_TIP;
    }

    /**
     * 删除app消息通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer appNoticeId) {
        appNoticeService.deleteById(appNoticeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改app消息通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AppNotice appNotice) {
        appNoticeService.updateById(appNotice);
        return SUCCESS_TIP;
    }

    /**
     * app消息通知详情
     */
    @RequestMapping(value = "/detail/{appNoticeId}")
    @ResponseBody
    public Object detail(@PathVariable("appNoticeId") Integer appNoticeId) {
        return appNoticeService.selectById(appNoticeId);
    }
}
