package cn.stylefeng.guns.modular.LeaderShip.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.LeaderShip.service.ILeadershipService;
import cn.stylefeng.guns.modular.system.model.Leadership;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * apileadership控制器
 *
 * @author fengshuonan
 * @Date 2018-11-29 11:56:36
 */
@Controller
@RequestMapping("/apileadership")
public class LeadershipController extends BaseController {

    private String PREFIX = "/LeaderShip/leadership/";

    @Autowired
    private ILeadershipService leadershipService;

    /**
     * 跳转到apileadership首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "leadership.html";
    }

    /**
     * 跳转到添加apileadership
     */
    @RequestMapping("/leadership_add")
    public String leadershipAdd() {
        return PREFIX + "leadership_add.html";
    }

    /**
     * 跳转到修改apileadership
     */
    @RequestMapping("/leadership_update/{leadershipId}")
    public String leadershipUpdate(@PathVariable Integer leadershipId, Model model) {
        Leadership leadership = leadershipService.selectById(leadershipId);
        model.addAttribute("item", leadership);
        LogObjectHolder.me().set(leadership);
        return PREFIX + "leadership_edit.html";
    }

    /**
     * 获取apileadership列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return leadershipService.selectList(null);
    }

    /**
     * 新增apileadership
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Leadership leadership) {
        leadershipService.insert(leadership);
        return SUCCESS_TIP;
    }

    /**
     * 删除apileadership
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer leadershipId) {
        leadershipService.deleteById(leadershipId);
        return SUCCESS_TIP;
    }

    /**
     * 修改apileadership
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Leadership leadership) {
        leadershipService.updateById(leadership);
        return SUCCESS_TIP;
    }

    /**
     * apileadership详情
     */
    @RequestMapping(value = "/detail/{leadershipId}")
    @ResponseBody
    public Object detail(@PathVariable("leadershipId") Integer leadershipId) {
        return leadershipService.selectById(leadershipId);
    }
}
