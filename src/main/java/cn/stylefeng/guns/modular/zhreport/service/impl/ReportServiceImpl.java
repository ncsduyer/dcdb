package cn.stylefeng.guns.modular.zhreport.service.impl;

import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.dao.MeetingSituationMapper;
import cn.stylefeng.guns.modular.system.dao.ReportMapper;
import cn.stylefeng.guns.modular.system.dao.SupervisionTypeMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.zhreport.dto.AddReportDto;
import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.guns.modular.zhreport.vo.ReportVo;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
//    @Autowired
//    private CompanyMapper companyMapper;
    @Autowired
    private SupervisionTypeMapper supervisionTypeMapper;
    @Autowired
    private MeetingSituationMapper meetingSituationMapper;


    @Override
    public ResponseData getList(SreachReportDto sreachReportDto) throws ParseException {

        if (ToolUtil.isNotEmpty(sreachReportDto.getGroup_id())) {
            ArrayList<Report> arrayList;
            arrayList = (ArrayList<Report>) selectList(Condition.create().eq("group_id",sreachReportDto.getGroup_id()));
            //todo 转化ReportVo
            ArrayList<ReportVo> reportVos=new ArrayList<>();
            return ResponseData.success(reportVos);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            Date frist = sdf.parse(sdf.format(calendar.getTime()));
            calendar.roll(Calendar.DATE, -1);
            Date last = sdf.parse(sdf.format(calendar.getTime()));
            Date beforeTime = ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime()) ? sreachReportDto.getBeforeTime(): frist;
            Date afterTime = ToolUtil.isNotEmpty(sreachReportDto.getAfterTime()) ? sreachReportDto.getAfterTime() : last;
            if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
                Date tmp = beforeTime;
                beforeTime = afterTime;
                afterTime = tmp;
            }
            if (ToolUtil.isNotEmpty(afterTime)) {
                afterTime = sdf.parse(sdf.format(afterTime));
                afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
            }
            List<ReportVo> reportVos = new ArrayList<>();
            //获取所有单位列表
            List<HashMap<Integer,String>> companyList= assignWorkMapper.selectCountByCompanyids(Condition.create().between("created_time",beforeTime,afterTime));
            for (HashMap<Integer,String> company:companyList){
                String[] arr = company.get("assingids").split(",");
                int count=arr.length;
                 String xqbj=accuracy(assignWorkMapper.selectCountByCompany(Condition.create().in("id",arr).in("status",new Integer[]{6,9}).addFilter("end_time<deadline")),count,2);
                 String cqbj=accuracy(assignWorkMapper.selectCountByCompany(Condition.create().in("id",arr).in("status",new Integer[]{6,9}).addFilter("end_time>deadline")),count,2);
                ArrayList<AssignWork> arrayList = (ArrayList<AssignWork>) assignWorkMapper.selectList(Condition.create().in("id",arr).isNotNull("end_time"));
                Long total=0L;
                for (AssignWork assignWork :arrayList) {
                    total+=assignWork.getEndTime().getTime()-assignWork.getCreatedTime().getTime();
                }
                String pjyongs=getPjUseTime(total,arrayList.size());
            }


            return ResponseData.success(reportVos);

        }
    }

    @Override
    public ResponseData add(AddReportDto addReportDto) {
        return null;
    }

    @Override
    public Boolean deleteByGidCompany(Integer groupId, String company) {
        return null;
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
}
