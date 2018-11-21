package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.EventType.service.IEventTypeService;
import cn.stylefeng.guns.modular.system.model.EventType;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 功能模块类型管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:29:07
 */
@Controller
@RequestMapping("/api/apieventType")
public class ApiEventTypeController extends BaseController {

    private String PREFIX = "/EventType/eventType/";

    @Autowired
    private IEventTypeService eventTypeService;

    /**
     * 跳转到功能模块类型管理首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "eventType.html";
    }

    /**
     * 跳转到添加功能模块类型管理
     */
    @Permission
    @RequestMapping("/eventType_add")
    public String eventTypeAdd() {
        return PREFIX + "eventType_add.html";
    }

    /**
     * 跳转到修改功能模块类型管理
     */
    @RequestMapping("/eventType_update/{eventTypeId}")
    @Permission
    public EventType eventTypeUpdate(@PathVariable Integer eventTypeId, Model model) {
        EventType eventType = eventTypeService.selectById(eventTypeId);
        model.addAttribute("item", eventType);
        LogObjectHolder.me().set(eventType);
        return eventType;
    }

    /**
     * 获取功能模块类型管理列表
     */
    @RequestMapping(value = "/list")
    @Permission
    public ResponseData list(String condition) {
        return ResponseData.success(eventTypeService.selectList(null));
    }

    /**
     * 新增功能模块类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission
    public ResponseData add(@RequestBody EventType eventType) {
        eventTypeService.insert(eventType);
        return SUCCESS_TIP;
    }

    /**
     * 删除功能模块类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    public ResponseData delete(@RequestParam Integer eventTypeId) {
        eventTypeService.deleteById(eventTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改功能模块类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission
    public ResponseData update(@RequestBody EventType eventType) {
        eventTypeService.updateById(eventType);
        return SUCCESS_TIP;
    }

    /**
     * 功能模块类型管理详情
     */
    @RequestMapping(value = "/detail/{eventTypeId}")
    @ResponseBody
    @Permission
    public ResponseData detail(@PathVariable("eventTypeId") Integer eventTypeId) {
        return ResponseData.success(eventTypeService.selectById(eventTypeId));
    }
}
