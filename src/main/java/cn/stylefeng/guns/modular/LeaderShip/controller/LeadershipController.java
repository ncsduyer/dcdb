package cn.stylefeng.guns.modular.LeaderShip.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.LeaderShip.service.ILeadershipService;
import cn.stylefeng.guns.modular.system.model.Leadership;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 领导列表控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:26:50
 */
@Api(tags = "领导列表")
@Controller
@RequestMapping("/apileadership")
public class LeadershipController extends BaseController {

    private String PREFIX = "/LeaderShip/leadership/";

    @Autowired
    private ILeadershipService leadershipService;

    /**
     * 跳转到领导列表首页
     */
    @RequestMapping("")
    @Permission
    public String index() {
        return PREFIX + "leadership.html";
    }

    /**
     * 跳转到添加领导列表
     */
    @RequestMapping("/leadership_add")
    @Permission
    public String leadershipAdd() {
        return PREFIX + "leadership_add.html";
    }

    /**
     * 跳转到修改领导列表
     */
    @RequestMapping("/leadership_update/{leadershipId}")
    @Permission
    public String leadershipUpdate(@PathVariable Integer leadershipId, Model model) {
        Leadership leadership = leadershipService.selectById(leadershipId);
        model.addAttribute("item", leadership);
        LogObjectHolder.me().set(leadership);
        return PREFIX + "leadership_edit.html";
    }

    /**
     * 获取领导列表列表
     */
    @ApiOperation(value = "获取领导列表列表")
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public ResponseData list(Leadership leadership) {
        EntityWrapper<Leadership> ew = new EntityWrapper<Leadership>();
        if (StringUtils.isNotBlank(leadership.getName())) {
            ew.like("name", leadership.getName());
        }
        if (StringUtils.isNotBlank(leadership.getDepartment())) {
            ew.like("department", leadership.getDepartment());
        }
        if (StringUtils.isNotBlank(leadership.getTitle())) {
            ew.like("title", leadership.getTitle());
        }
        if (StringUtils.isNotBlank(leadership.getPhone())) {
            ew.like("phone", leadership.getPhone());
        }
        if (StringUtils.isNotBlank(leadership.getEmail())) {
            ew.like("email", leadership.getEmail());
        }

        ew.orderBy("id");
//        System.out.println(ew.getSqlSegment());
        return ResponseData.success(leadershipService.selectList(ew));
    }

    /**
     * 新增领导列表
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(@RequestBody Leadership leadership) {
        leadershipService.insert(leadership);
        return SUCCESS_TIP;
    }

    /**
     * 删除领导列表
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Integer leadershipId) {
        leadershipService.deleteById(leadershipId);
        return SUCCESS_TIP;
    }

    /**
     * 修改领导列表
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(@RequestBody Leadership leadership) {
        leadershipService.updateById(leadership);
        return SUCCESS_TIP;
    }

    /**
     * 领导列表详情
     */
    @RequestMapping(value = "/detail/{leadershipId}")
    @Permission
    @ResponseBody
    public ResponseData detail(@PathVariable("leadershipId") Integer leadershipId) {
        return ResponseData.success(leadershipService.selectById(leadershipId));
    }
}
