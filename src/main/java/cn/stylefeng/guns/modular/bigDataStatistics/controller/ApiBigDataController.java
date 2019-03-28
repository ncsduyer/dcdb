package cn.stylefeng.guns.modular.bigDataStatistics.controller;

import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.modular.DocAssignRec.service.IDocassignrecService;
import cn.stylefeng.guns.modular.Docs.service.IDocsService;
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.Infos.service.IInfosService;
import cn.stylefeng.guns.modular.Infosrec.service.IInfosrecService;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.bigDataStatistics.service.IBigDataServiceImpl;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.CheckItemVo;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.service.IMeetingService;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.EventStep;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * app消息通知控制器
 *
 * @author fengshuonan
 * @Date 2018-11-22 14:25:25
 */
@RestController
@RequestMapping("/api/BigData")
@Api(value = "大数据api集合", tags = "大数据 api集合")
public class ApiBigDataController extends BaseController {

    @Autowired
    private IBigDataServiceImpl bigDataService;
    @Autowired
    private ITaskassignService taskassignService;

    @Autowired
    private IMeetingService meetingService;

    @Autowired
    private IDocsService docsService;

    @Autowired
    private IInfosService iInfosService;
    @Autowired
    private IMeetingrecService meetingrecService;

    @Autowired
    private IDocassignrecService docassignrecService;

    @Autowired
    private IInfosrecService iInfosrecService;
    @Autowired
    private IEventStepService eventStepService;
    @Autowired
    private ICheckitemService checkitemService;
    /**
     * 获取督察督办数据
     */
    @ApiOperation(value = "获取督察督办数据")
    @RequestMapping(value = "/count", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData count() {
        HashMap<String,Integer> map=new HashMap<>(5);
        Integer dcdb=taskassignService.selectCount(Condition.create().gt("id", 0));
        Integer meet=meetingrecService.selectCount(Condition.create().gt("id", 0));
        Integer doc=docassignrecService.selectCount(Condition.create().gt("id", 0));
        Integer info= iInfosrecService.selectCount(Condition.create().gt("id", 0));
        map.put("total", dcdb+meet+doc+info);
        map.put("dcdb", dcdb);
        map.put("meet",meet );
        map.put("doc", doc);
        map.put("info",info);
        return ResponseData.success(map);
    }
    /**
     * 获取督办事项部门统计
     */
    @ApiOperation(value = "获取督办事项部门统计")
    @RequestMapping(value = "/countUnit", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countUnit() {
        List<EventStep> eventSteps=eventStepService.selectList(Condition.create().eq("event_type", 1));
        HashMap<String,Integer> map=new HashMap<>();
        for (EventStep et:eventSteps){
            EntityWrapper<Taskassign> ew = new EntityWrapper<>();
            ew.setEntity(new Taskassign());
            ew.eq("status", et.getStatus());
            map.put(et.getStep(),taskassignService.selectCount(ew));
        }
        return ResponseData.success(map);

    }
    /**
     * 获取督办事项部门问题统计
     */
    @ApiOperation(value = "获取督办事项部门问题统计")
    @RequestMapping(value = "/countUnitStar", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countUnitStar() {
        return ResponseData.success(bigDataService.countUnitStar());

    }
    /**
     * 获取督办事项相关统计
     */
    @ApiOperation(value = "获取督办事项相关统计")
    @RequestMapping(value = "/countAssignStatus", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countAssignStatus() {

        return ResponseData.success(bigDataService.countAssignStatus());

    }
    /**
     * 获取区委办管理事务统计
     */
    @ApiOperation(value = "获取区委办管理事务统计")
    @RequestMapping(value = "/countManagementServicesStatistics", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countManagementServicesStatistics() {
        Calendar cale = null;
//        ArrayList<HashMap<String,HashMap<String,Integer>>> maps=new ArrayList<>();
        HashMap<String,HashMap<String,Integer>> mapHashMap=new HashMap<>(12);
        HashMap<String,Integer> map=null;
        String date[] = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
        for (int integer=1;integer<12;integer++) {
           Date start= Bettime.getStartOfDay(Bettime.getFirstDayOfMonth(integer));
           Date end= Bettime.getEndOfDay(Bettime.getLastDayOfMonth(integer));
            map=new HashMap<>();
            map.put("督办事项", taskassignService.selectCount(Condition.create().between("createtime", start, end)));
            map.put("区委会议", meetingrecService.selectCount(Condition.create().between("createtime", start, end)));
            map.put("区委公文", docassignrecService.selectCount(Condition.create().between("createtime", start, end)));
            map.put("区委信息", iInfosrecService.selectCount(Condition.create().between("createtime", start, end)));
            mapHashMap.put(date[integer-1],map);
        }
        return ResponseData.success(mapHashMap);

    }

    /**
     * 获取区委会议统计
     */
    @ApiOperation(value = "获取区委会议统计")
    @RequestMapping(value = "/countMeeting", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countMeeting() {
        List<Checkitem> checkitems=checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1));
        List<CheckItemVo> checkItemVos=new ArrayList<>();
        CheckItemVo checkItemVo=null;
        for (Checkitem checkitem : checkitems){
            checkItemVo=new CheckItemVo();
            CopyUtils.copyProperties(checkitem, checkItemVo);
            checkItemVo.setCount(meetingrecService.selectCount(Condition.create().eq("checkitemid", checkitem.getId())));
            checkItemVos.add(checkItemVo);
        }
        return ResponseData.success(checkItemVos);

    }
    /**
     * 获取区委公文统计
     */
    @ApiOperation(value = "获取区委公文统计")
    @RequestMapping(value = "/countDocs", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countDocs() {
        List<Checkitem> checkitems=checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1));
        List<CheckItemVo> checkItemVos=new ArrayList<>();
        CheckItemVo checkItemVo=null;
        for (Checkitem checkitem : checkitems){
            checkItemVo=new CheckItemVo();
            CopyUtils.copyProperties(checkitem, checkItemVo);
            checkItemVo.setCount(docassignrecService.selectCount(Condition.create().eq("checkitemid", checkitem.getId())));
            checkItemVos.add(checkItemVo);
        }
        return ResponseData.success(checkItemVos);

    }
    /**
     * 获取区委信息统计
     */
    @ApiOperation(value = "获取区委信息统计")
    @RequestMapping(value = "/countInfos", method = {RequestMethod.GET})
    @ResponseBody
    @Cacheable(value = "bigdata",key = "#root.targetClass+'#'+#root.method")
    public ResponseData countInfos() {
        List<Checkitem> checkitems=checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1));
        List<CheckItemVo> checkItemVos=new ArrayList<>();
        CheckItemVo checkItemVo=null;
        for (Checkitem checkitem : checkitems){
            checkItemVo=new CheckItemVo();
            CopyUtils.copyProperties(checkitem, checkItemVo);
            checkItemVo.setCount(iInfosrecService.selectCount(Condition.create().eq("checkitemid", checkitem.getId())));
            checkItemVos.add(checkItemVo);
        }
        return ResponseData.success(checkItemVos);

    }
}
