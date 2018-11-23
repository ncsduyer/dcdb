package cn.stylefeng.guns.modular.DcdbReport.service.impl;

import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.DcdbReport.service.IDcdbReportService;
import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.dao.DcdbReportMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean addReport(Map<String, Date> map) throws ParseException {
        if (ToolUtil.isEmpty(map)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        Date frist = sdf.parse(sdf.format(calendar.getTime()));
        calendar.roll(Calendar.DATE, -1);
        Date last = sdf.parse(sdf.format(calendar.getTime()));

        Date beforeTime = ToolUtil.isNotEmpty(map.get("beforeTime")) ? map.get("beforeTime") : frist;
        Date afterTime = ToolUtil.isNotEmpty(map.get("afterTime")) ? map.get("afterTime") : last;
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
            for (AssignWork assignWork : assignWorks) {
                dcdbReports.add(new DcdbReport(assignWork));
            }
            insertOrUpdateBatch(dcdbReports);
            size -= limit;
            if (size < limit) {
                limit = size;
            }
        }
        return true;
    }
}
