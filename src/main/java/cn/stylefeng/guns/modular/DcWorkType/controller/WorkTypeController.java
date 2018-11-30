package cn.stylefeng.guns.modular.DcWorkType.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.DcWorkType.service.IWorkTypeService;
import cn.stylefeng.guns.modular.system.model.WorkType;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * worktype控制器
 *
 * @author fengshuonan
 * @Date 2018-11-29 13:58:40
 */
@Controller
@RequestMapping("/apiworkType")
public class WorkTypeController extends BaseController {

    private String PREFIX = "/DcWorkType/workType/";

    @Autowired
    private IWorkTypeService workTypeService;

    /**
     * 跳转到worktype首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workType.html";
    }

    /**
     * 跳转到添加worktype
     */
    @RequestMapping("/workType_add")
    public String workTypeAdd() {
        return PREFIX + "workType_add.html";
    }

    /**
     * 跳转到修改worktype
     */
    @RequestMapping("/workType_update/{workTypeId}")
    public String workTypeUpdate(@PathVariable Integer workTypeId, Model model) {
        WorkType workType = workTypeService.selectById(workTypeId);
        model.addAttribute("item", workType);
        LogObjectHolder.me().set(workType);
        return PREFIX + "workType_edit.html";
    }

    /**
     * 获取worktype列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return workTypeService.selectList(null);
    }

    /**
     * 新增worktype
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkType workType) {
        workTypeService.insert(workType);
        return SUCCESS_TIP;
    }

    /**
     * 删除worktype
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer workTypeId) {
        workTypeService.deleteById(workTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改worktype
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkType workType) {
        workTypeService.updateById(workType);
        return SUCCESS_TIP;
    }

    /**
     * worktype详情
     */
    @RequestMapping(value = "/detail/{workTypeId}")
    @ResponseBody
    public Object detail(@PathVariable("workTypeId") Integer workTypeId) {
        return workTypeService.selectById(workTypeId);
    }
}
