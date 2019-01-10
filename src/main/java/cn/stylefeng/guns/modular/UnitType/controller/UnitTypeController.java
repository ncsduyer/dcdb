package cn.stylefeng.guns.modular.UnitType.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.UnitType.service.IUnitTypeService;
import cn.stylefeng.guns.modular.system.model.UnitType;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单位类型管理控制器
 *
 * @author fengshuonan
 * @Date 2019-01-10 11:20:25
 */
@Controller
@RequestMapping("/unitType")
public class UnitTypeController extends BaseController {

    private String PREFIX = "/UnitType/unitType/";

    @Autowired
    private IUnitTypeService unitTypeService;

    /**
     * 跳转到单位类型管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unitType.html";
    }

    /**
     * 跳转到添加单位类型管理
     */
    @RequestMapping("/unitType_add")
    public String unitTypeAdd() {
        return PREFIX + "unitType_add.html";
    }

    /**
     * 跳转到修改单位类型管理
     */
    @RequestMapping("/unitType_update/{unitTypeId}")
    public String unitTypeUpdate(@PathVariable Integer unitTypeId, Model model) {
        UnitType unitType = unitTypeService.selectById(unitTypeId);
        model.addAttribute("item",unitType);
        LogObjectHolder.me().set(unitType);
        return PREFIX + "unitType_edit.html";
    }

    /**
     * 获取单位类型管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return unitTypeService.selectList(null);
    }

    /**
     * 新增单位类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UnitType unitType) {
        unitTypeService.insert(unitType);
        return SUCCESS_TIP;
    }

    /**
     * 删除单位类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer unitTypeId) {
        unitTypeService.deleteById(unitTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改单位类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UnitType unitType) {
        unitTypeService.updateById(unitType);
        return SUCCESS_TIP;
    }

    /**
     * 单位类型管理详情
     */
    @RequestMapping(value = "/detail/{unitTypeId}")
    @ResponseBody
    public Object detail(@PathVariable("unitTypeId") Integer unitTypeId) {
        return unitTypeService.selectById(unitTypeId);
    }
}
