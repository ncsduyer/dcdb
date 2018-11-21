package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.meetingSituation.service.IMeetingSituationService;
import cn.stylefeng.guns.modular.system.model.MeetingSituation;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 会议督查管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-23 17:15:51
 */
@Controller
@RequestMapping("/api/apimeetingSituation")
public class ApiMeetingSituationController extends BaseController {

    private String PREFIX = "/meetingSituation/meetingSituation/";

    @Autowired
    private IMeetingSituationService meetingSituationService;

    /**
     * 跳转到会议督查管理首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "meetingSituation.html";
    }

    /**
     * 跳转到添加会议督查管理
     */
    @RequestMapping("/meetingSituation_add")
    @Permission
    public String meetingSituationAdd() {
        return PREFIX + "meetingSituation_add.html";
    }

    /**
     * 跳转到修改会议督查管理
     */
    @RequestMapping("/meetingSituation_update/{meetingSituationId}")
    @Permission
    public String meetingSituationUpdate(@PathVariable Integer meetingSituationId, Model model) {
        MeetingSituation meetingSituation = meetingSituationService.selectById(meetingSituationId);
        model.addAttribute("item", meetingSituation);
        LogObjectHolder.me().set(meetingSituation);
        return PREFIX + "meetingSituation_edit.html";
    }

    /**
     * 获取会议督查管理列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public ResponseData list(String condition) {
        return ResponseData.success(meetingSituationService.selectList(null));
    }

    /**
     * 新增会议督查管理
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody MeetingSituation meetingSituation) {
        meetingSituationService.insert(meetingSituation);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议督查管理
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer meetingSituationId) {
        meetingSituationService.deleteById(meetingSituationId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议督查管理
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody MeetingSituation meetingSituation) {
        meetingSituationService.updateById(meetingSituation);
        return SUCCESS_TIP;
    }

    /**
     * 会议督查管理详情
     */
    @RequestMapping(value = "/detail/{meetingSituationId}")
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("meetingSituationId") Integer meetingSituationId) {
        return ResponseData.success(meetingSituationService.selectById(meetingSituationId));
    }
}
