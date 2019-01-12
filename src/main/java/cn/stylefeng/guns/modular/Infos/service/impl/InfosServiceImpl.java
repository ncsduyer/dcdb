package cn.stylefeng.guns.modular.Infos.service.impl;

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
import cn.stylefeng.guns.modular.Infos.dto.AddInfoDto;
import cn.stylefeng.guns.modular.Infos.dto.SreachInfoDto;
import cn.stylefeng.guns.modular.Infos.service.IInfosService;
import cn.stylefeng.guns.modular.Infosrec.service.IInfosrecService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.dao.InfosMapper;
import cn.stylefeng.guns.modular.system.dao.InfosrecMapper;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Infos;
import cn.stylefeng.guns.modular.system.model.Infosrec;
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
 * 区委信息 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class InfosServiceImpl extends ServiceImpl<InfosMapper, Infos> implements IInfosService {
    @Autowired
    private InfosMapper infosMapper;
    @Autowired
    private InfosrecMapper infosrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IEventStepService eventStepService;
    @Autowired
    private IInfosrecService infosrecService;

    @Override
    public ResponseData SreachPage(SreachInfoDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachInfoDto();
            }
            Page<Infos> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            new Bettime(sreachDto);
            EntityWrapper<Infos> ew = new EntityWrapper<>();
            ew.setEntity(new Infos());
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

            ArrayList<Infos> arrayList = infosMapper.selectAsPage(page,ew);
            for (Infos meeting: arrayList
            ) {
                meeting.setCompanys(infosrecMapper.getInfoByPid(Condition.create().eq("rec.infosid",  meeting.getId()).in("rec.unitid", sreachDto.getCompanyIds()),checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1))));
            }
            page.setRecords(arrayList);
//            page.setTotal(infosMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData add(AddInfoDto addDto) {
        try{

            Infos meeting = new Infos();
            BeanUtils.copyProperties(addDto, meeting);
            if(ToolUtil.isEmpty(meeting.getCreatorid())){
                meeting.setCreatorid(ShiroKit.getUser().getId());
            }
            insert(meeting);

            Infosrec meetingrec= new Infosrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环插入交办单位
                for (Infosrec map : addDto.getResc()) {
                    BeanUtils.copyProperties(map, meetingrec);
            meetingrec.setInfosid(meeting.getId());
                    if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                        meetingrec.setCreatetime(new DateTime());
                    }
                    infosrecMapper.insert(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData edit(AddInfoDto addDto) {
        try{
            Infos meeting = new Infos();
            BeanUtils.copyProperties(addDto, meeting);
            updateById(meeting);
            Infosrec meetingrec= new Infosrec();
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                循环修改交办单位
                for (Infosrec map : addDto.getResc()) {
                    CopyUtils.copyProperties(map, meetingrec);
                    infosrecMapper.updateById(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public void export(SreachMeetingDto sreachInfoDto, HttpServletResponse response) {
        //sheet名
        String sheetName = "区委信息数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = infosrecService.export(sreachInfoDto);
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"单位")));
        ArrayList<Checkitem> checkitems= (ArrayList<Checkitem>) checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1).orderBy("id", true));
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

        ExportUtil.outExport(sreachInfoDto, response, titles, sheetName, exportRowVos);
    }

    @Override
    public ResponseData selectWithManyById(Integer id) {
        Infos meeting = infosMapper.selectWithManyById(id);
        meeting.setCompanys(infosrecMapper.getInfoByPid(Condition.create().eq("rec.infosid",  id),checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1))));
        return ResponseData.success(meeting);
    }

    @Override
    public ResponseData getReports(SreachInfoDto sreachDto) {
        return SreachPage(sreachDto);
    }

    @Override
    public ResponseData sreachChart(SreachInfoDto sreachDto) {

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
