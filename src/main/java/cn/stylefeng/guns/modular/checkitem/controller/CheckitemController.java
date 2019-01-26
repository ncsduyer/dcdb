package cn.stylefeng.guns.modular.checkitem.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.EventType.service.IEventTypeService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 督查类型控制器
 *
 * @author fengshuonan
 * @Date 2018-12-17 15:59:50
 */
@Controller
@RequestMapping("/checkitem")
public class CheckitemController extends BaseController {

    private String PREFIX = "/checkitem/checkitem/";

    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IEventTypeService eventTypeService;

    /**
     * 跳转到督查类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "checkitem.html";
    }

    /**
     * 跳转到添加督查类型
     */
    @RequestMapping("/checkitem_add")
    public String checkitemAdd(Model model) {
        model.addAttribute("eventtype",eventTypeService.selectList(null));
        return PREFIX + "checkitem_add.html";
    }

    /**
     * 跳转到修改督查类型
     */
    @RequestMapping("/checkitem_update/{checkitemId}")
    public String checkitemUpdate(@PathVariable Integer checkitemId, Model model) {
        Checkitem checkitem = checkitemService.selectById(checkitemId);
        model.addAttribute("item",checkitem);
        model.addAttribute("eventtype",eventTypeService.selectList(null));
        LogObjectHolder.me().set(checkitem);
        return PREFIX + "checkitem_edit.html";
    }

    /**
     * 获取督查类型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return checkitemService.selectList(null);
    }

    /**
     * 新增督查类型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Checkitem checkitem) {
        checkitemService.insert(checkitem);
        return SUCCESS_TIP;
    }

    /**
     * 删除督查类型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer checkitemId) {
        checkitemService.deleteById(checkitemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查类型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Checkitem checkitem) {
        checkitemService.updateById(checkitem);
        return SUCCESS_TIP;
    }

    /**
     * 督查类型详情
     */
    @RequestMapping(value = "/detail/{checkitemId}")
    @ResponseBody
    public Object detail(@PathVariable("checkitemId") Integer checkitemId) {
        return checkitemService.selectById(checkitemId);
    }
}
