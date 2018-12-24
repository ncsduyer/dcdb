package cn.stylefeng.guns.modular.Docs.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocsService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.dao.DocassignrecMapper;
import cn.stylefeng.guns.modular.system.dao.DocsMapper;
import cn.stylefeng.guns.modular.system.model.Docassignrec;
import cn.stylefeng.guns.modular.system.model.Docs;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 公文运转 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class DocsServiceImpl extends ServiceImpl<DocsMapper, Docs> implements IDocsService {
    @Autowired
    private DocsMapper docsMapper;
    @Autowired
    private DocassignrecMapper docassignrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Override
    public ResponseData SreachPage(SreachMeetingDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachDocDto();
            }
            Page<Docs> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            new Bettime(sreachDto);
            EntityWrapper<Docs> ew = new EntityWrapper<>();
            ew.setEntity(new Docs());
            if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
                ew.gt("m.mtime", sreachDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
                ew.lt("m.mtime", sreachDto.getAfterTime());
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

            if (ToolUtil.isNotEmpty(sreachDto.getOrder())){
                ew.orderBy(sreachDto.getOrder());
            }else{
                ew.orderBy("d.id",false);
            }

            ArrayList<Docs> arrayList = docsMapper.selectAsPage(page,ew);
            for (Docs meeting: arrayList
            ) {
                meeting.setCompanys(docassignrecMapper.getInfoByPid((EntityWrapper<Docassignrec>) Condition.create().eq("rec.meetingid",  meeting.getId()),checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1))));
            }
            page.setRecords(arrayList);
            page.setTotal(docsMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData add(AddDocDto addDto) {
        try{

            Docs docs = new Docs();
            BeanUtils.copyProperties(addDto, docs);
            insert(docs);

            Docassignrec meetingrec= new Docassignrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环插入交办单位
                for (Docassignrec map : addDto.getResc()) {
                    BeanUtils.copyProperties(map, meetingrec);
                    if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                        meetingrec.setCreatetime(new DateTime());
                    }
                    docassignrecMapper.insert(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData edit(AddDocDto addDto) {
        try{
            Docs meeting = new Docs();
            BeanUtils.copyProperties(addDto, meeting);
            updateById(meeting);
            Docassignrec meetingrec= new Docassignrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环修改交办单位
                for (Docassignrec map : addDto.getResc()) {
                    CopyUtils.copyProperties(map, meetingrec);
                    docassignrecMapper.updateById(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
    @Override
    public ResponseData selectWithManyById(Integer id) {
        Docs meeting = docsMapper.selectWithManyById(id);
        meeting.setCompanys(docassignrecMapper.getInfoByPid((EntityWrapper<Docassignrec>) Condition.create().eq("rec.meetingid",  id),checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1))));
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
//                        ew.gt("assigntime", sreachDto.getBeforeTime());
//                    }
//                    if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
//                        ew.lt("assigntime", sreachDto.getAfterTime());
//                    }
//                    ew.eq("status", et.getStatus());
//                    legend.getData().add(et.getStep());
//                    series.getData().add(new DataBean(et.getStep(),taskassignService.selectCount(ew)));
//                    seriess.add(series);
//                }
//                //填充数据到map
//
//                return ResponseData.success(new ChartVo(seriess,legend));
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
//                        ew.gt("assigntime", sreachDto.getBeforeTime());
//                    }
//                    if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
//                        ew.lt("assigntime", sreachDto.getAfterTime());
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
//                return ResponseData.success(new ChartVo(seriess,legend,axis));
        return ResponseData.success(null);

    }
}


