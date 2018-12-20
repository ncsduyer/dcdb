package cn.stylefeng.guns.core.util;

import cn.stylefeng.roses.core.util.ToolUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class VoUtil {
    public static boolean hasHour=false;
    public static String getUseTime(Date createdTime, Date endTime){
        return getUseTime(createdTime,endTime,hasHour);
    }
    public static String getUseTime(Date createdTime, Date endTime,Boolean hasHour) {
        Date start = createdTime;
        Date end = endTime;
        if (ToolUtil.isEmpty(start)){
            return "";
        }
        if (ToolUtil.isEmpty(end)){
            end=new Date();
        }
        Long total = end.getTime() - start.getTime();
        if (hasHour){
            int days = (int) (total / (1000 * 60 * 60 * 24));
            int hours = (int) (total / (1000 * 60 * 60) % 24);
            int minutes = (int) (total / (1000 * 60) % 60);
            String date = "";
            date = String.format("%d天%d小时%d分", days, hours, minutes);
            return date;
        }else{
            float days = (float) (total / (1000 * 60 * 60 * 24));
            return String.format("%.1f天", days);
        }
    }
    private String accuracy(double num, double total, int scale){
        try {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
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
