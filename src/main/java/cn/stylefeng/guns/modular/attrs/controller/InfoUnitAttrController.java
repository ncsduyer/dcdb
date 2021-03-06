package cn.stylefeng.guns.modular.attrs.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.attrs.service.IInfoUnitAttrService;
import cn.stylefeng.guns.modular.system.model.InfoUnitAttr;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * attrs控制器
 *
 * @author fengshuonan
 * @Date 2019-04-22 16:42:45
 */
@Controller
@RequestMapping("/api/infoUnitAttr")
public class InfoUnitAttrController extends BaseController {

    private String PREFIX = "/attrs/infoUnitAttr/";

    @Autowired
    private IInfoUnitAttrService infoUnitAttrService;

    /**
     * 跳转到attrs首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "infoUnitAttr.html";
    }

    /**
     * 跳转到添加attrs
     */
    @RequestMapping("/infoUnitAttr_add")
    public String infoUnitAttrAdd() {
        return PREFIX + "infoUnitAttr_add.html";
    }

    /**
     * 跳转到修改attrs
     */
    @RequestMapping("/infoUnitAttr_update/{infoUnitAttrId}")
    public String infoUnitAttrUpdate(@PathVariable Integer infoUnitAttrId, Model model) {
        InfoUnitAttr infoUnitAttr = infoUnitAttrService.selectById(infoUnitAttrId);
        model.addAttribute("item",infoUnitAttr);
        LogObjectHolder.me().set(infoUnitAttr);
        return PREFIX + "infoUnitAttr_edit.html";
    }

    /**
     * 获取attrs列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return infoUnitAttrService.selectList(null);
    }

    /**
     * 新增attrs
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(@RequestBody InfoUnitAttr infoUnitAttr) {
        infoUnitAttrService.insert(infoUnitAttr);
        return SUCCESS_TIP;
    }

    /**
     * 删除attrs
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam Integer infoUnitAttrId) {
        infoUnitAttrService.deleteById(infoUnitAttrId);
        return SUCCESS_TIP;
    }

    /**
     * 修改attrs
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(@RequestBody InfoUnitAttr infoUnitAttr) {
        infoUnitAttrService.updateById(infoUnitAttr);
        return SUCCESS_TIP;
    }

    /**
     * attrs详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public ResponseData detail(@RequestBody InfoUnitAttr infoUnitAttr) {
        return ResponseData.success(infoUnitAttrService.selectOne(Condition.create().eq("infoid", infoUnitAttr.getInfoid()).eq("unitid",infoUnitAttr.getUnitid())));
    }
}
