package cn.stylefeng.guns.modular.Infosrec.controller;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.Infosrec.service.IInfosrecService;
import cn.stylefeng.guns.modular.Infosrec.vo.InfosrecVo;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.system.model.Infosrec;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 区委信息上报管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-23 13:15:14
 */
@Api(tags = "区委信息上报")
@Controller
@RequestMapping("/api/infosrec")
public class InfosrecController extends BaseController {

    private String PREFIX = "/Infosrec/infosrec/";

    @Autowired
    private IInfosrecService infosrecService;
    @Autowired
    private ICompanyService companyService;

    

    /**
     * 获取区委信息上报管理列表
     */
    @ApiOperation(value = "区委信息上报列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkitemid", value = "检查项ID数组", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorid", value = "创建人id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "companyIds", value = "部门id数组", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "所属事项id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public ResponseData list(@Validated @RequestBody SreachMeetingRecDto sreachMeetingRecDto) {
        return infosrecService.selectListByDto(sreachMeetingRecDto);
    }

    /**
     * 新增区委信息上报管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(Infosrec infosrec) {
        if (ToolUtil.isEmpty(infosrec.getCreatetime())){
            infosrec.setCreatetime(new DateTime());
        }
        if (infosrecService.insert(infosrec)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 删除区委信息
     */
    @ApiOperation(value = "删除区委信息单个单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infosid", value = "必填:信息id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "unitid", value = "必填:部门id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData delete(@RequestBody Infosrec infosrec) {
        if (ToolUtil.isEmpty(infosrec.getInfosid())||ToolUtil.isEmpty(infosrec.getUnitid())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        if (infosrecService.delete(Condition.create().eq("infosid", infosrec.getInfosid()).eq("unitid", infosrec.getUnitid()))){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 修改区委信息上报管理
     */
    @ApiOperation(value = "修改区委信息单个单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必填:id", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "infosid", value = "可选:信息id", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "unitid", value = "可选:部门id", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "checkitemid", value = "可选:检查项ID", required = false, dataType = "String"),
            @ApiImplicitParam(name = "checkvalue", value = "必填:检查项值", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/update",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData update(@RequestBody List<Infosrec> infosrecs) {

        if (infosrecService.updateBatchById(infosrecs)){
            return SUCCESS_TIP;
        }
        return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
    }

    /**
     * 区委信息上报管理详情
     */
    @ApiOperation(value = "区委信息单个单位详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infosid", value = "必填:信息id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "unitid", value = "必填:部门id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/detail",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData detail(@RequestBody Infosrec infosrec) {
        if (ToolUtil.isEmpty(infosrec.getInfosid())||ToolUtil.isEmpty(infosrec.getUnitid())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        try {
            List<InfosrecVo> infosrecVos=new ArrayList<>();
            InfosrecVo infosrecVo=null;
            for (Infosrec i: (List<Infosrec>) infosrecService.selectList(Condition.create().eq("infosid", infosrec.getInfosid()).eq("unitid", infosrec.getUnitid()))) {
                infosrecVo=new InfosrecVo();
                BeanUtils.copyProperties(i, infosrecVo);
                infosrecVo.setUnitname(companyService.selectById(infosrecVo.getUnitid()).getTitle());
                infosrecVos.add(infosrecVo);
            }
            infosrecVo=null;

            return ResponseData.success(infosrecVos);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }

    }
}
