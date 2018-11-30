package cn.stylefeng.guns.modular.DcdbReport.service.impl;

import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.DateGroup.service.IDateGroupService;
import cn.stylefeng.guns.modular.DcdbReport.service.IDcdbReportService;
import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.dao.DcdbReportMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.DateGroup;
import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 督察督办报表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-21
 */
@Service
public class DcdbReportServiceImpl extends ServiceImpl<DcdbReportMapper, DcdbReport> implements IDcdbReportService {
    @Autowired
    private AssignWorkMapper assignWorkMapper;
    @Autowired
    private IDateGroupService dateGroupService;

    @Override
    @Transactional
    public boolean addReport(Map<String, String> map) throws ParseException {
        if (ToolUtil.isEmpty(map) && ToolUtil.isEmpty(map.get("reportName"))) {
            return false;
        }
        String reportName = (String) map.get("reportName");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        Date frist = sdf.parse(sdf.format(calendar.getTime()));
        calendar.roll(Calendar.DATE, -1);
        Date last = sdf.parse(sdf.format(calendar.getTime()));

        Date beforeTime = ToolUtil.isNotEmpty(map.get("beforeTime")) ? sdf.parse(map.get("beforeTime")) : frist;
        Date afterTime = ToolUtil.isNotEmpty(map.get("afterTime")) ? sdf.parse(map.get("afterTime")) : last;
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {

            try {
                afterTime = sdf.parse(sdf.format(afterTime));
            } catch (ParseException e) {
                return false;
            }
            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }

        int size = assignWorkMapper.selectCount(null);
        int limit = 100;
        for (int i = 1; size > 0; i++) {
            ArrayList<AssignWork> assignWorks = (ArrayList<AssignWork>) assignWorkMapper.selectAsPage(new Page<>(i, limit), new SreachWorkDto(), beforeTime, afterTime);
            List<DcdbReport> dcdbReports = new ArrayList<>();
            DateGroup dateGroup = dateGroupService.selectOne(Condition.create().eq("name", reportName));
            if (ToolUtil.isEmpty(dateGroup)) {
                dateGroup = new DateGroup();
                dateGroup.setName(reportName);
                dateGroup.setBeforeTime(beforeTime);
                dateGroup.setAfterTime(afterTime);
                dateGroupService.insert(dateGroup);
                for (AssignWork assignWork : assignWorks) {
                    dcdbReports.add(new DcdbReport(assignWork, dateGroup.getId().toString()));
                }
                insertBatch(dcdbReports);
            } else {
                for (AssignWork assignWork : assignWorks) {
                    dcdbReports.add(new DcdbReport(assignWork, dateGroup.getId().toString()));
                }
                updateBatchById(dcdbReports);
            }

            size -= limit;
            if (size < limit) {
                limit = size;
            }
        }
        return true;
    }

    @Override
    public ResponseData getDcdbReports(Map<String, String> map) throws ParseException {
        if (ToolUtil.isEmpty(map)) {
            map = new HashMap<String, String>();
        }
        if (ToolUtil.isNotEmpty(map.get("dateGroup"))) {
            ArrayList<DcdbReport> arrayList;
//                arrayList= (ArrayList<DcdbReport>) selectList(Condition.create().between("created_time", beforeTime, afterTime).eq("date_group",map.get("dateGroup")));
            arrayList = (ArrayList<DcdbReport>) selectList(Condition.create().eq("date_group", map.get("dateGroup")));
            for (DcdbReport dcdbReport : arrayList) {
                if (ToolUtil.isEmpty(dcdbReport.getEndTime())) {
                    dcdbReport.setUseTime(getUseTime(dcdbReport.getCreatedTime(), new Date()));
                } else {
                    dcdbReport.setUseTime(getUseTime(dcdbReport.getCreatedTime(), dcdbReport.getEndTime()));
                }
            }
            return ResponseData.success(arrayList);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            Date frist = sdf.parse(sdf.format(calendar.getTime()));
            calendar.roll(Calendar.DATE, -1);
            Date last = sdf.parse(sdf.format(calendar.getTime()));
            Date beforeTime = ToolUtil.isNotEmpty(map.get("beforeTime")) ? sdf.parse(map.get("beforeTime")) : frist;
            Date afterTime = ToolUtil.isNotEmpty(map.get("afterTime")) ? sdf.parse(map.get("afterTime")) : last;
            if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
                Date tmp = beforeTime;
                beforeTime = afterTime;
                afterTime = tmp;
            }
            if (ToolUtil.isNotEmpty(afterTime)) {
                afterTime = sdf.parse(sdf.format(afterTime));
                afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
            }
            ArrayList<AssignWork> arrayList = new ArrayList<>();
            List<DcdbReport> dcdbReports = new ArrayList<>();
            int size = assignWorkMapper.selectCount(null);
            int limit = 100;
            for (int i = 1; size > 0; i++) {
                arrayList.addAll((ArrayList<AssignWork>) assignWorkMapper.selectAsPage(new Page<>(i, limit), new SreachWorkDto(), beforeTime, afterTime));

                size -= limit;
                if (size < limit) {
                    limit = size;
                }
            }
            for (AssignWork assignWork : arrayList) {
                dcdbReports.add(new DcdbReport(assignWork, ""));
            }
            for (DcdbReport dcdbReport : dcdbReports) {
                if (ToolUtil.isEmpty(dcdbReport.getEndTime())) {
                    dcdbReport.setUseTime(getUseTime(dcdbReport.getCreatedTime(), new Date()));
                } else {
                    dcdbReport.setUseTime(getUseTime(dcdbReport.getCreatedTime(), dcdbReport.getEndTime()));
                }
            }
            return ResponseData.success(dcdbReports);
//                arrayList= (ArrayList<DcdbReport>) selectList(Condition.create().between("created_time", beforeTime, afterTime));
        }

    }

    private String getUseTime(Date createdTime, Date endTime) {
        Long total = endTime.getTime() - createdTime.getTime();
        int days = (int) (total / (1000 * 60 * 60 * 24));
        int hours = (int) (total / (1000 * 60 * 60) % 24);
        int minutes = (int) (total / (1000 * 60) % 60);
        String date = "";
        date = String.format("%d天%d小时%d分", days, hours, minutes);
        return date;
    }
}
