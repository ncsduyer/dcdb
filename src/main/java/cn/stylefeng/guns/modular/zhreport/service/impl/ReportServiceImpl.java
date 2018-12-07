package cn.stylefeng.guns.modular.zhreport.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.reportGroup.service.IReportGroupService;
import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.dao.MeetingSituationMapper;
import cn.stylefeng.guns.modular.system.dao.ReportMapper;
import cn.stylefeng.guns.modular.system.dao.SupervisionTypeMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.system.model.ReportGroup;
import cn.stylefeng.guns.modular.zhreport.dto.ReportDto;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.guns.modular.zhreport.vo.ReportVo;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Autowired
    private AssignWorkMapper assignWorkMapper;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private IReportGroupService reportGroupService;
    @Autowired
    private IReportService reportService;
//    @Autowired
//    private CompanyMapper companyMapper;
    @Autowired
    private SupervisionTypeMapper supervisionTypeMapper;
    @Autowired
    private MeetingSituationMapper meetingSituationMapper;


    @Override
    public ResponseData getList(ReportDto sreachReportDto) throws ParseException {
        if (ToolUtil.isNotEmpty(sreachReportDto.getGroup_id())) {
            ArrayList<Report> arrayList;
            arrayList = reportMapper.selectMoreList(Condition.create().eq("group_id",sreachReportDto.getGroup_id()));
            //todo 转化ReportVo
            ArrayList<ReportVo> reportVos=new ArrayList<>();
           //list转换map
            Map<String, List<Report>> groupBy = arrayList.stream().collect(Collectors.groupingBy( x->x.getCompany().getTitle()));
            for(Map.Entry<String, List<Report>> entry : groupBy.entrySet()){
                String companyName=entry.getKey();
                List<Report> list=entry.getValue();
                Map<String, List<Report>> map=list.stream().collect(Collectors.groupingBy( x->x.getEventTypeObj().getReportAlias()));
                reportVos.add(new ReportVo(list.get(0).getGroup().getName(),companyName,map));
            }
            return ResponseData.success(reportVos);
        } else {
            List<ReportVo> reportVos = selectReportVos(sreachReportDto);


            return ResponseData.success(reportVos);

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(ReportDto addReportDto){
        if (ToolUtil.isEmpty(addReportDto.getGroupName())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), "报表名称必须填写");
        }
        try {
            Bettime bettime = new Bettime(addReportDto).invoke();
            Date beforeTime = bettime.getBeforeTime();
            Date afterTime = bettime.getAfterTime();
            ReportGroup reportGroup = reportGroupService.selectOne(Condition.create().eq("name", addReportDto.getGroupName()));
            if (ToolUtil.isEmpty(reportGroup)) {
                reportGroup = new ReportGroup();
                reportGroup.setName(addReportDto.getGroupName());
                reportGroup.setBeforeTime(beforeTime);
                reportGroup.setAfterTime(afterTime);
                reportGroupService.insert(reportGroup);
            }
            int groupid=reportGroup.getId();
            List<ReportVo> reportVos  = selectReportVos(addReportDto);
            List<Report> reports=new ArrayList<>();
            for (ReportVo reportVo:reportVos){
                for (Object reports2:reportVo.getMap().values()) {
                    for (Report report : (List<Report>) reports2) {
                        report.setGroupId(groupid);
                        report.setCreatedTime(beforeTime);
                        reports.add(report);
                    }
                }
            }
            reportService.insertBatch(reports);
            return ResponseData.success();
        } catch (ParseException e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public Boolean deleteByGidCompany(Integer groupId, String company) {
        return reportService.delete(Condition.create().eq("group_id",groupId).eq("company_id",company));
    }

    private String accuracy(double num, double total, int scale){
        try {
            DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
            //可以设置精确几位小数
            df.setMaximumFractionDigits(scale);
            //模式 例如四舍五入
            df.setRoundingMode(RoundingMode.HALF_UP);
            double accuracy_num = num / total * 100;
            return df.format(accuracy_num)+"%";
        }catch (Exception e) {
            return "0%";
        }

    }
    private String getPjUseTime(Long total, int size) {
        if (size==0){
            return "0天0小时0分";
        }
        total=total/size;
        int days = (int) (total / (1000 * 60 * 60 * 24));
        int hours = (int) (total / (1000 * 60 * 60) % 24);
        int minutes = (int) (total / (1000 * 60) % 60);
        String date = "";
        date = String.format("%d天%d小时%d分", days, hours, minutes);
        return date;
    }

    private List<ReportVo> selectReportVos(ReportDto sreachReportDto) throws ParseException {
        Bettime bettime = new Bettime(sreachReportDto).invoke();
        Date beforeTime = bettime.getBeforeTime();
        Date afterTime = bettime.getAfterTime();
        List<ReportVo> reportVos = new ArrayList<>();

        //获取所有单位列表
        List<HashMap<Integer,Object>> companyList= assignWorkMapper.selectCountByCompanyids(Condition.create().between("created_time",beforeTime,afterTime));
        for (HashMap<Integer, Object> company:companyList){
            LinkedHashMap map=new LinkedHashMap();
            int  companyId= (int) company.get("companyId");
            String[] arr = ((String)company.get("assingids")).split(",");
            String eventTpye =(String) company.get("eventType");
            String companyName=(String)company.get("companyName");
            Integer eventType=1;
            int count=arr.length;
            Report report=new Report(eventType,companyId,"交办事项总数",String.valueOf(count));
            String xqbj=accuracy(assignWorkMapper.selectCountByCompany(Condition.create().in("id",arr).in("status",new Integer[]{6,9}).addFilter("end_time<deadline")),count,2);
            Report report1=new Report(eventType,companyId,"按期办结率",xqbj);
            String cqbj=accuracy(assignWorkMapper.selectCountByCompany(Condition.create().in("id",arr).in("status",new Integer[]{6,9}).addFilter("end_time>deadline")),count,2);
            Report report2=new Report(eventType,companyId,"逾期办结率",cqbj);
            ArrayList<AssignWork> arrayList = (ArrayList<AssignWork>) assignWorkMapper.selectList(Condition.create().in("id",arr).isNotNull("end_time"));
            Long total=0L;
            for (AssignWork assignWork :arrayList) {
                total+=assignWork.getEndTime().getTime()-assignWork.getCreatedTime().getTime();
            }
            String pjyongs=getPjUseTime(total,arrayList.size());
            Report report3=new Report(eventType,companyId,"平均办结用时",pjyongs);
            ArrayList<Report> reports=new ArrayList<>();
            reports.add(report);
            reports.add(report1);
            reports.add(report2);
            reports.add(report3);
            map.put(eventTpye,reports);
            ReportVo reportVo=new ReportVo("",companyName,map);
            reportVos.add(reportVo);
        }
        return reportVos;
    }

    private class Bettime {
        private ReportDto sreachReportDto;
        private Date beforeTime;
        private Date afterTime;

        public Bettime(ReportDto sreachReportDto) {
            this.sreachReportDto = sreachReportDto;
        }

        public Date getBeforeTime() {
            return beforeTime;
        }

        public Date getAfterTime() {
            return afterTime;
        }

        public Bettime invoke() throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            Date frist = sdf.parse(sdf.format(calendar.getTime()));
            calendar.roll(Calendar.DATE, -1);
            Date last = sdf.parse(sdf.format(calendar.getTime()));
            beforeTime = ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime()) ? sreachReportDto.getBeforeTime() : frist;
            afterTime = ToolUtil.isNotEmpty(sreachReportDto.getAfterTime()) ? sreachReportDto.getAfterTime() : last;
            if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
                Date tmp = beforeTime;
                beforeTime = afterTime;
                afterTime = tmp;
            }
            if (ToolUtil.isNotEmpty(afterTime)) {
                afterTime = sdf.parse(sdf.format(afterTime));
                afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
            }
            return this;
        }
    }
}
