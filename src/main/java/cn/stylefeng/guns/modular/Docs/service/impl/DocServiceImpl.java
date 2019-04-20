package cn.stylefeng.guns.modular.Docs.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.core.util.ExportUtil;
import cn.stylefeng.guns.core.util.TypeCastUtil;
import cn.stylefeng.guns.core.util.vo.ExportColSubVo;
import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.CopyRecordNotice.service.ICopyRecordNoticeService;
import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocRecService;
import cn.stylefeng.guns.modular.Docs.service.IDocService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.dao.DocMapper;
import cn.stylefeng.guns.modular.system.dao.DocRecMapper;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.CopyRecordNotice;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.guns.modular.system.model.DocRec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 交办公文表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements IDocService {
    @Autowired
    private DocMapper docsMapper;
    @Autowired
    private DocRecMapper docRecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IDocRecService docRecService;
    @Autowired
    private ICopyRecordNoticeService copyRecordNoticeService;
    @Override
    public ResponseData SreachPage(SreachMeetingDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachDocDto();
            }
            Page<HashMap<String,Object>> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            new Bettime(sreachDto);
            EntityWrapper<DocRec> ew = new EntityWrapper<>();
            ew.setEntity(new DocRec());
            if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
                ew.ge("m.assign_time", sreachDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
                ew.le("m.assign_time", sreachDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getId())){
                ew.eq("m.id", sreachDto.getId());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCreatorid())){
                ew.eq("m.creatorid", sreachDto.getCreatorid());
            }
//            拼接查询条件
            if (ToolUtil.isNotEmpty(sreachDto.getTitle())){
                ew.like("m.title", sreachDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getStatus())){
                ew.in("m.status", sreachDto.getStatus());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("mr.unitid", sreachDto.getCompanyIds());
            }
            ew.groupBy("mr.unitid");
            if (ToolUtil.isNotEmpty(sreachDto.getOrder())){
                ew.orderBy(sreachDto.getOrder());
            }else{
                ew.orderBy("m.id",false);
            }

            ArrayList<HashMap<String,Object>> arrayList = docRecMapper.getInfoByPidPage(page,ew,checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1)));
            page.setRecords(arrayList);
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData add(AddDocDto addDto) {
        try{
            addDto.setId(null);
            Doc docs = new Doc();
            BeanUtils.copyProperties(addDto, docs);
            if(ToolUtil.isEmpty(docs.getCreatorid())){
                docs.setCreatorid(ShiroKit.getUser().getId());
            }
            insert(docs);

            DocRec meetingrec= null;
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
            //循环插入交办单位
                for (DocRec map : addDto.getResc()) {
                    meetingrec= new DocRec();
                    BeanUtils.copyProperties(map, meetingrec);
                    meetingrec.setDocassignid(docs.getId());
                    meetingrec.setCheckvalue(1);
                    if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                        meetingrec.setCreatetime(new DateTime());
                    }
                    docRecMapper.insert(meetingrec);
                }
            }

            Map<String,String> map=new HashMap<>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
            map.put("title",docs.getTitle());
            map.put("date",sdf.format(docs.getAssignTime()));
            map.put("datetime",sdf.format(Calendar.getInstance().getTime()));
            if (ToolUtil.isNotEmpty(addDto.getCopyRecordNotices())) {
                for (CopyRecordNotice copyRecordNotice : addDto.getCopyRecordNotices()) {
                    map.put("check", copyRecordNotice.getContent());
                    copyRecordNotice.setType(3);
                    copyRecordNotice.setJoinId(docs.getId());
                    copyRecordNotice.setCreatetime(Calendar.getInstance().getTime());
                    copyRecordNotice.setSenderId(docs.getCreatorid());
                    copyRecordNotice.setJsonContent(JSONObject.toJSONString(map));
                    copyRecordNoticeService.insert(copyRecordNotice);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData edit(AddDocDto addDto) {
        if(ToolUtil.isEmpty(addDto.getId())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        try{
            Doc meeting = new Doc();
            BeanUtils.copyProperties(addDto, meeting);
            updateById(meeting);
            DocRec meetingrec= null;
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                List<DocRec> old=docRecService.selectList(Condition.create().eq("docassignid", meeting.getId()));
//                old.removeAll(addDto.getResc());
//                if(ToolUtil.isNotEmpty(old)){
//                    docRecService.deleteBatchIds(old);
//                }
//                循环修改交办单位
                for (DocRec map : addDto.getResc()) {
                    meetingrec= new DocRec();
                    CopyUtils.copyProperties(map, meetingrec);
                    meetingrec.setCheckvalue(1);
                    docRecMapper.updateById(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public void export(SreachMeetingDto sreachDocDto, HttpServletResponse response) {
//sheet名
        String sheetName = "区委公文数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = docRecService.export(sreachDocDto);
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"单位")));
        ArrayList<Checkitem> checkitems= (ArrayList<Checkitem>) checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1).orderBy("id", true));
        for (int i=0;i<checkitems.size();i++) {
            Checkitem ck=checkitems.get(i);
            title.getColVos().add(new ExportColVo(new ExportColSubVo(1,ck.getItemdesc())));
            //获取数据
        }
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"平均处理时长")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"总数")));
        titles.add(title);

        for (HashMap<String,Object> map : maps) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(1);
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, (String) map.get("title"))));
            for (int i=0;i<checkitems.size();i++) {
                Checkitem ck=checkitems.get(i);
                exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, TypeCastUtil.toString(TypeCastUtil.toInt(TypeCastUtil.toDouble(map.get(ck.getId().toString())))))));
            }
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, (String) map.get("pjsj"))));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, TypeCastUtil.toString(TypeCastUtil.toInt(map.get("total"))))));
            exportRowVos.add(exportRowVo);
        }

        ExportUtil.outExport(sreachDocDto, response, titles, sheetName, exportRowVos);
    }

    @Override
    public Boolean deleteMoreById(Integer id) {
        docRecMapper.delete(Condition.create().eq("docassignid", id));
        boolean sucess=deleteById(id);
        return sucess;
    }

    @Override
    public ResponseData selectAsMore(SreachMeetingDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto.getCompanyIds())){
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachDocDto();
            }
            Page<Doc> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            new Bettime(sreachDto);
            EntityWrapper<Doc> ew = new EntityWrapper<>();
            ew.setEntity(new Doc());
            if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
                ew.ge("m.assign_time", sreachDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
                ew.le("m.assign_time", sreachDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getId())){
                ew.eq("m.id", sreachDto.getId());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCreatorid())){
                ew.eq("m.creatorid", sreachDto.getCreatorid());
            }
//            拼接查询条件
            if (ToolUtil.isNotEmpty(sreachDto.getTitle())){
                ew.like("m.title", sreachDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getStatus())){
                ew.in("m.status", sreachDto.getStatus());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("mr.unitid", sreachDto.getCompanyIds());
            }
            ew.groupBy("m.id,mr.id");
            if (ToolUtil.isNotEmpty(sreachDto.getOrder())){
                ew.orderBy(sreachDto.getOrder());
            }else{
                ew.orderBy("m.id",false);
            }

            List<Doc> arrayList = docsMapper.selectAsMore(ew);
            page.setRecords(arrayList);
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData selectWithManyById(Integer id) {
        Doc meeting = docsMapper.selectWithManyById(id);
        return ResponseData.success(meeting);
    }

    @Override
    public ResponseData getReports(SreachMeetingDto sreachDto) {
        return SreachPage(sreachDto);
    }

    @Override
    public ResponseData sreachChart(SreachMeetingDto sreachDto) {

        //循环获取类型
//        List<EventStep> eventSteps=eventStepService.selectList(Condition.create().eq("event_type", 1));
//        List<Series> seriess;
//        Legend legend=new Legend();
//
//        switch (sreachDto.getChartType()){
//            case ChartUtil.PIE:
//                legend.setData(new ArrayList<>());
//
//                seriess = new LinkedList<>();
//                try {
//                    new Bettime(sreachDto);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                for (EventStep et:eventSteps){
//                    Series<DataBean> series=new Series<>();
//                    series.setData(new ArrayList<>());
//                    EntityWrapper<Taskassign> ew = new EntityWrapper<>();
//                    ew.setEntity(new Taskassign());
//                    if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
//                        ew.ge("assigntime", sreachDto.getBeforeTime());
//                    }
//                    if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
//                        ew.le("assigntime", sreachDto.getAfterTime());
//                    }
//                    ew.eq("status", et.getStatus());
//                    legend.getData().add(et.getStep());
//                    series.getData().add(new DataBean(et.getStep(),taskassignService.selectCount(ew)));
//                    seriess.add(series);
//                }
//                //填充数据到map
//
//                return BigResponseData.success(new ChartVo(seriess,legend));
//            default:
//                //拆分时间
//                Axis axis=new Axis();
//                axis.setData(new LinkedHashSet<>());
//                List<Date> dates=Bettime.getDates(sreachDto.getBeforeTime(), sreachDto.getAfterTime());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                legend.setData(new ArrayList<>());
//                seriess = new LinkedList<>();
//
//                try {
//                    new Bettime(sreachDto);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                for (EventStep et:eventSteps){
//                    Series<Integer> series=new Series<>();
//                    series.setData(new ArrayList<>());
//                    legend.getData().add(et.getStep());
//                    series.setName(et.getStep());
//
//
//                    EntityWrapper<Taskassign> ew = new EntityWrapper<>();
//                    ew.setEntity(new Taskassign());
//                    ew.setSqlSelect("date_format(assigntime, '%Y-%m-%d') time,count(id) size");
//                    if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
//                        ew.ge("assigntime", sreachDto.getBeforeTime());
//                    }
//                    if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
//                        ew.le("assigntime", sreachDto.getAfterTime());
//                    }
//                    ew.eq("status", et.getStatus());
//                    ew.groupBy("date_format(assigntime, '%Y-%m-%d')");
//                    List<Map<String, Object>> maps= taskassignService.selectMaps(ew);
//                    for (Date date :dates) {
//                        axis.getData().add(sdf.format(date));
//                        series.getData().add(0);
//                    }
//                    for (Map<String, Object> map:maps){
//                        try {
//                            series.getData().set(dates.indexOf(sdf.parse((String) map.get("time"))),((Number) map.get("size")).intValue());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    seriess.add(series);
//                }
//
//
//
//                return BigResponseData.success(new ChartVo(seriess,legend,axis));
        return ResponseData.success(null);

    }
}
