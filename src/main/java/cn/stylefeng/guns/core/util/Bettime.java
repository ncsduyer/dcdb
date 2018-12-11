package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Bettime {
    private Date beforeTime;
    private Date afterTime;
    public Bettime(SreachDto sreachDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        Date frist = sdf.parse(sdf.format(calendar.getTime()));
        calendar.roll(Calendar.DATE, -1);
        Date last = sdf.parse(sdf.format(calendar.getTime()));
        beforeTime = ToolUtil.isNotEmpty(sreachDto.getBeforeTime()) ? sreachDto.getBeforeTime() : frist;
        afterTime = ToolUtil.isNotEmpty(sreachDto.getAfterTime()) ? sreachDto.getAfterTime() : last;
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {
            afterTime = sdf.parse(sdf.format(afterTime));
            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }
    }
    public Date getBeforeTime() {
        return beforeTime;
    }

    public Date getAfterTime() {
        return afterTime;
    }
}
