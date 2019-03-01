package cn.stylefeng.guns.modular.resources.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.Asset;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 资源管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-01 11:21:32
 */
@Controller
@RequestMapping("/asset")
public class AssetController extends BaseController {

    private String PREFIX = "/resources/asset/";

    @Autowired
    private IAssetService assetService;

    /**
     * 跳转到资源管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "asset.html";
    }

    /**
     * 跳转到添加资源管理
     */
    @RequestMapping("/asset_add")
    public String assetAdd() {
        return PREFIX + "asset_add.html";
    }

    /**
     * 跳转到修改资源管理
     */
    @RequestMapping("/asset_update/{assetId}")
    public String assetUpdate(@PathVariable Integer assetId, Model model) {
        Asset asset = assetService.selectById(assetId);
        model.addAttribute("item",asset);
        LogObjectHolder.me().set(asset);
        return PREFIX + "asset_edit.html";
    }

    /**
     * 获取资源管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return assetService.selectList(null);
    }

    /**
     * 新增资源管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Asset asset) {
        assetService.insert(asset);
        return SUCCESS_TIP;
    }

    /**
     * 删除资源管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer assetId) {
        assetService.deleteById(assetId);
        return SUCCESS_TIP;
    }

    /**
     * 修改资源管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Asset asset) {
        assetService.updateById(asset);
        return SUCCESS_TIP;
    }

    /**
     * 资源管理详情
     */
    @RequestMapping(value = "/detail/{assetId}")
    @ResponseBody
    public Object detail(@PathVariable("assetId") Integer assetId) {
        return assetService.selectById(assetId);
    }
}
