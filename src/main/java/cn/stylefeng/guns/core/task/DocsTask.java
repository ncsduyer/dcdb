package cn.stylefeng.guns.core.task;

import cn.stylefeng.guns.modular.Docs.service.IDocService;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DocsTask {
    @Autowired
    private IDocService docService;

    @Scheduled(cron = "* 0/30 * * * ?")
    @Async
    public void setStatus() {
        List<Doc> docs=docService.selectList(Condition.create().eq("exceed", 0).eq("status", 0));
        List<Doc> updateDocs=new ArrayList<>();
        for (Doc doc :docs) {
            if (this.daysBetween(doc.getAssignTime(),new Date())>3){
                doc.setExceed(1);
                updateDocs.add(doc);
            }
            if (ToolUtil.isNotEmpty(updateDocs)){
                docService.updateBatchById(updateDocs);
            }
        }
    }
    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     * calendar 对日期进行时间操作
     * getTimeInMillis() 获取日期的毫秒显示形式
     */
    private int daysBetween(Date smdate, Date bdate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
