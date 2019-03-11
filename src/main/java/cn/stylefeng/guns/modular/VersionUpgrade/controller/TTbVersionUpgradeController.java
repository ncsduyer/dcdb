package cn.stylefeng.guns.modular.VersionUpgrade.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.VersionUpgrade.service.IVersionUpgradeService;
import cn.stylefeng.guns.modular.system.model.VersionUpgrade;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * app版本管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-07 21:35:02
 */
@Controller
@RequestMapping("/versionUpgrade")
public class TTbVersionUpgradeController extends BaseController {

    private String PREFIX = "/VersionUpgrade/tTbVersionUpgrade/";

    @Autowired
    private IVersionUpgradeService tTbVersionUpgradeService;

    /**
     * 跳转到app版本管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tTbVersionUpgrade.html";
    }

    /**
     * 跳转到添加app版本管理
     */
    @RequestMapping("/tTbVersionUpgrade_add")
    public String tTbVersionUpgradeAdd() {
        return PREFIX + "tTbVersionUpgrade_add.html";
    }

    /**
     * 跳转到修改app版本管理
     */
    @RequestMapping("/tTbVersionUpgrade_update/{tTbVersionUpgradeId}")
    public String tTbVersionUpgradeUpdate(@PathVariable Integer tTbVersionUpgradeId, Model model) {
        VersionUpgrade tTbVersionUpgrade = tTbVersionUpgradeService.selectById(tTbVersionUpgradeId);
        model.addAttribute("item",tTbVersionUpgrade);
        LogObjectHolder.me().set(tTbVersionUpgrade);
        return PREFIX + "tTbVersionUpgrade_edit.html";
    }

    /**
     * 获取app版本管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return tTbVersionUpgradeService.selectList(null);
    }

    /**
     * 新增app版本管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VersionUpgrade tTbVersionUpgrade) {
        tTbVersionUpgradeService.insert(tTbVersionUpgrade);
        return SUCCESS_TIP;
    }

    /**
     * 删除app版本管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tTbVersionUpgradeId) {
        tTbVersionUpgradeService.deleteById(tTbVersionUpgradeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改app版本管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(VersionUpgrade tTbVersionUpgrade) {
        tTbVersionUpgradeService.updateById(tTbVersionUpgrade);
        return SUCCESS_TIP;
    }

    /**
     * app版本管理详情
     */
    @RequestMapping(value = "/detail/{tTbVersionUpgradeId}")
    @ResponseBody
    public Object detail(@PathVariable("tTbVersionUpgradeId") Integer tTbVersionUpgradeId) {
        return tTbVersionUpgradeService.selectById(tTbVersionUpgradeId);
    }
}
