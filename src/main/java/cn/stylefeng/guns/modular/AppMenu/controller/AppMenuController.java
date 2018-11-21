package cn.stylefeng.guns.modular.AppMenu.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.AppMenu.service.IAppMenuService;
import cn.stylefeng.guns.modular.system.model.AppMenu;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * app菜单管理控制器
 *
 * @author fengshuonan
 * @Date 2018-11-21 10:19:00
 */
@Controller
@RequestMapping("/appMenu")
public class AppMenuController extends BaseController {

    private String PREFIX = "/AppMenu/appMenu/";

    @Autowired
    private IAppMenuService appMenuService;

    /**
     * 跳转到app菜单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "appMenu.html";
    }

    /**
     * 跳转到添加app菜单管理
     */
    @RequestMapping("/appMenu_add")
    public String appMenuAdd() {
        return PREFIX + "appMenu_add.html";
    }

    /**
     * 跳转到修改app菜单管理
     */
    @RequestMapping("/appMenu_update/{appMenuId}")
    public String appMenuUpdate(@PathVariable Integer appMenuId, Model model) {
        AppMenu appMenu = appMenuService.selectById(appMenuId);
        model.addAttribute("item", appMenu);
        LogObjectHolder.me().set(appMenu);
        return PREFIX + "appMenu_edit.html";
    }

    /**
     * 获取app菜单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return appMenuService.selectList(null);
    }

    /**
     * 新增app菜单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AppMenu appMenu) {
        appMenuService.insert(appMenu);
        return SUCCESS_TIP;
    }

    /**
     * 删除app菜单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer appMenuId) {
        appMenuService.deleteById(appMenuId);
        return SUCCESS_TIP;
    }

    /**
     * 修改app菜单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AppMenu appMenu) {
        appMenuService.updateById(appMenu);
        return SUCCESS_TIP;
    }

    /**
     * app菜单管理详情
     */
    @RequestMapping(value = "/detail/{appMenuId}")
    @ResponseBody
    public Object detail(@PathVariable("appMenuId") Integer appMenuId) {
        return appMenuService.selectById(appMenuId);
    }
}
