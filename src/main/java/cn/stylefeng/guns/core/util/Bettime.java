package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.modular.api.dto.SreachDto;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Bettime {
    private Date beforeTime;
    private Date afterTime;

    /**
     * 获取某段时这里写代码片间内的所有日期
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> getDates(Date dBegin, Date dEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> lDate = new ArrayList<Date>();
        if (ToolUtil.isNotEmpty(dBegin) && ToolUtil.isNotEmpty(dEnd) && dEnd.before(dBegin)) {
            Date tmp = dBegin;
            dBegin = dEnd;
            dEnd = tmp;
        }
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
            try {
                lDate.add(sdf.parse(sdf.format(calBegin.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return lDate;
    }

    public Bettime() {
    }
    public Bettime(Date date) {
                beforeTime=getStartOfDay(date);
            afterTime = getEndOfDay(date);
    }
    public void setBettime(Date date) {
                beforeTime=getStartOfDay(date);
            afterTime = getEndOfDay(date);
    }


    public Bettime(SreachDto sreachDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE, 1);
//        Date frist = sdf.parse(sdf.format(calendar.getTime()));
        Date last = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
        beforeTime =  sreachDto.getBeforeTime();
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
        sreachDto.setBeforeTime(beforeTime);
        sreachDto.setAfterTime(afterTime);
    }
    public Date getBeforeTime() {
        return beforeTime;
    }

    public Date getAfterTime() {
        return afterTime;
    }

    // 获得某天最大时间 2018-03-20 23:59:59
    @NotNull
    public static Date getEndOfDay(Date date) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        //防止mysql自动加一秒,毫秒设为0
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    // 获得某天最小时间 2018-03-20 00:00:00
    @NotNull
    public static Date getStartOfDay(Date date) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        //防止mysql自动加一秒,毫秒设为0
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();

    }

}
