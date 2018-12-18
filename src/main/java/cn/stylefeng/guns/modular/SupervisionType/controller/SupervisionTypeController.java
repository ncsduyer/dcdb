//package cn.stylefeng.guns.modular.SupervisionType.controller;
//
//import cn.stylefeng.guns.core.common.annotion.Permission;
//import cn.stylefeng.guns.core.log.LogObjectHolder;
//import cn.stylefeng.guns.modular.SupervisionType.service.ISupervisionTypeService;
//import cn.stylefeng.guns.modular.system.model.SupervisionType;
//import cn.stylefeng.roses.core.base.controller.BaseController;
//import cn.stylefeng.roses.core.reqres.response.ResponseData;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 督查类型管理控制器
// *
// * @author fengshuonan
// * @Date 2018-10-23 17:16:37
// */
//@Api(tags = "督查类型")
//@Controller
//@RequestMapping("/supervisionType")
//public class SupervisionTypeController extends BaseController {
//
//    private String PREFIX = "/SupervisionType/supervisionType/";
//
//    @Autowired
//    private ISupervisionTypeService supervisionTypeService;
//
//    /**
//     * 跳转到督查类型管理首页
//     */
//    @RequestMapping("")
//    @Permission
//    public String index() {
//        return PREFIX + "supervisionType.html";
//    }
//
//    /**
//     * 跳转到添加督查类型管理
//     */
//    @RequestMapping("/supervisionType_add")
//    @Permission
//    public String supervisionTypeAdd() {
//        return PREFIX + "supervisionType_add.html";
//    }
//
//    /**
//     * 跳转到修改督查类型管理
//     */
//    @RequestMapping("/supervisionType_update/{supervisionTypeId}")
//    @Permission
//    public String supervisionTypeUpdate(@PathVariable Integer supervisionTypeId, Model model) {
//        SupervisionType supervisionType = supervisionTypeService.selectById(supervisionTypeId);
//        model.addAttribute("item", supervisionType);
//        LogObjectHolder.me().set(supervisionType);
//        return PREFIX + "supervisionType_edit.html";
//    }
//
//    /**
//     * 获取督查类型管理列表
//     */
//    @ApiOperation(value = "获取督查类型列表")
//    @RequestMapping(value = "/list")
//    @Permission
//    @ResponseBody
//    public ResponseData list() {
//        return ResponseData.success(supervisionTypeService.selectList(null));
//    }
//
//    /**
//     * 新增督查类型管理
//     */
//    @RequestMapping(value = "/add")
//    @Permission
//    @ResponseBody
//    public ResponseData add(@RequestBody SupervisionType supervisionType) {
//        supervisionTypeService.insert(supervisionType);
//        return SUCCESS_TIP;
//    }
//
//    /**
//     * 删除督查类型管理
//     */
//    @RequestMapping(value = "/delete")
//    @Permission
//    @ResponseBody
//    public ResponseData delete(@RequestParam Integer supervisionTypeId) {
//        supervisionTypeService.deleteById(supervisionTypeId);
//        return SUCCESS_TIP;
//    }
//
//    /**
//     * 修改督查类型管理
//     */
//    @RequestMapping(value = "/update")
//    @Permission
//    @ResponseBody
//    public ResponseData update(@RequestBody SupervisionType supervisionType) {
//        supervisionTypeService.updateById(supervisionType);
//        return SUCCESS_TIP;
//    }
//
//    /**
//     * 督查类型管理详情
//     */
//    @RequestMapping(value = "/detail/{supervisionTypeId}")
//    @Permission
//    @ResponseBody
//    public ResponseData detail(@PathVariable("supervisionTypeId") Integer supervisionTypeId) {
//        return ResponseData.success(supervisionTypeService.selectById(supervisionTypeId));
//    }
//}
