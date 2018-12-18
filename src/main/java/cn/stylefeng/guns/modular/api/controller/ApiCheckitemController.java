package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 督查类型控制器
 *
 * @author fengshuonan
 * @Date 2018-12-17 15:59:50
 */
@Api(tags = "督查类型api")
@RestController
@RequestMapping("/api/checkitem")
public class ApiCheckitemController extends BaseController {

    @Autowired
    private ICheckitemService checkitemService;

    /**
     * 获取督查类型列表
     * @param itemclass
     */
    @ApiOperation(value = "获取督查类型管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemclass", value = "督查项类别（1-事项；2-会议；3-公文；4-信息）", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData list(@RequestParam(value = "itemclass") Integer itemclass) {
        return ResponseData.success(checkitemService.selectList(Condition.create().eq("itemclass", itemclass).eq("status", 1)));
    }

    /**
     * 新增督查类型
     */
    @ApiOperation(value = "获取督查类型管理列表")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData add(Checkitem checkitem) {
        checkitemService.insert(checkitem);
        return SUCCESS_TIP;
    }

    /**
     * 删除督查类型
     */
    @ApiOperation(value = "获取督查类型管理列表")
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData delete(@RequestParam Integer id) {
        checkitemService.deleteById(id);
        return SUCCESS_TIP;
    }

    /**
     * 修改督查类型
     */
    @ApiOperation(value = "获取督查类型管理列表")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(Checkitem checkitem) {
        checkitemService.updateById(checkitem);
        return SUCCESS_TIP;
    }

    /**
     * 督查类型详情
     */
//    @ApiOperation(value = "获取督查类型管理列表")
//    @RequestMapping(value = "/detail/{id}")
//    @ResponseBody
//    public ResponseData detail(@PathVariable("id") Integer id) {
//        return ResponseData.success(checkitemService.selectById(id));
//    }
}
