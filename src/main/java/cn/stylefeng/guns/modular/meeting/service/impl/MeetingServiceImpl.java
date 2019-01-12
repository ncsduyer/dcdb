package cn.stylefeng.guns.modular.meeting.service.impl;

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
import cn.stylefeng.guns.modular.EventStep.service.IEventStepService;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.dto.AddMeetingDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.meeting.service.IMeetingService;
import cn.stylefeng.guns.modular.system.dao.MeetingMapper;
import cn.stylefeng.guns.modular.system.dao.MeetingrecMapper;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Meeting;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 区委会议 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {
    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private MeetingrecMapper meetingrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IMeetingrecService meetingrecService;
    @Autowired
    private IEventStepService eventStepService;

    @Override
    public ResponseData SreachPage(SreachMeetingDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachMeetingDto();
            }
            Page<Meeting> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            new Bettime(sreachDto);
            EntityWrapper<Meeting> ew = new EntityWrapper<>();
            ew.setEntity(new Meeting());
            if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())){
                ew.ge("m.mtime", sreachDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())){
                ew.le("m.mtime", sreachDto.getAfterTime());
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
            ew.groupBy("m.id");
            if (ToolUtil.isNotEmpty(sreachDto.getOrder())){
                ew.orderBy(sreachDto.getOrder());
            }else{
                ew.orderBy("m.id",false);
            }

            ArrayList<Meeting> arrayList = meetingMapper.selectAsPage(page,ew);
            for (Meeting meeting: arrayList
                 ) {
                List<HashMap<String,Object>> compangys= meetingrecMapper.getInfoByPid(Condition.create().eq("rec.meetingid",  meeting.getId()).in("rec.unitid", sreachDto.getCompanyIds()),checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1)));
                meeting.setCompanys(compangys);
            }
            page.setRecords(arrayList);
//            page.setTotal(meetingMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData add(AddMeetingDto addDto) {
        try{

                Meeting meeting = new Meeting();
                BeanUtils.copyProperties(addDto, meeting);
                if(ToolUtil.isEmpty(meeting.getCreatorid())){
                    meeting.setCreatorid(ShiroKit.getUser().getId());
                }
                insert(meeting);

                   Meetingrec meetingrec= new Meetingrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环插入交办单位
                for (Meetingrec map : addDto.getResc()) {
                    BeanUtils.copyProperties(map, meetingrec);
                   meetingrec.setMeetingid(meeting.getId());
                    if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                        meetingrec.setCreatetime(new DateTime());
                    }
                    meetingrecMapper.insert(meetingrec);
                }
            }
                return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData edit(AddMeetingDto addDto) {
        try{
            Meeting meeting = new Meeting();
            BeanUtils.copyProperties(addDto, meeting);
            updateById(meeting);
            Meetingrec meetingrec= new Meetingrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环修改交办单位
                for (Meetingrec map : addDto.getResc()) {
                    CopyUtils.copyProperties(map, meetingrec);
                    meetingrecMapper.updateById(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public void export(SreachMeetingDto sreachMeetingDto, HttpServletResponse response) {
        //sheet名
        String sheetName = "会议督查数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = meetingrecService.export(sreachMeetingDto);
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"单位")));
        ArrayList<Checkitem> checkitems= (ArrayList<Checkitem>) checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1).orderBy("id", true));
        for (int i=0;i<checkitems.size();i++) {
            Checkitem ck=checkitems.get(i);
            title.getColVos().add(new ExportColVo(new ExportColSubVo(1,ck.getItemdesc())));
            //获取数据
        }
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
            exportRowVos.add(exportRowVo);
        }

        ExportUtil.outExport(sreachMeetingDto, response, titles, sheetName, exportRowVos);
    }

    @Override
    public ResponseData selectWithManyById(Integer id) {
        Meeting meeting = meetingMapper.selectWithManyById(id);
        meeting.setCompanys(meetingrecMapper.getInfoByPid(Condition.create().eq("rec.meetingid",  id),checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1))));
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
//                return ResponseData.success(new ChartVo(seriess,legend,axis));
                return ResponseData.success(null);

        }

}
