package cn.stylefeng.guns.core.util;

import cn.stylefeng.roses.core.util.ToolUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VoUtil {
    public static boolean hasHour=true;
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
            StringBuilder date = new StringBuilder();
            if(days>0){
                date.append(days+"天");
            }else if(hours>0){
//                date.append(hours+"小时");
                date.append("1天");
            }else if(minutes>0){
//                date.append(minutes+"分");
                date.append("1天");
            }
//            date = String.format("%d天%d小时%d分", days, hours, minutes);
            return date.toString();
        }else{
            float days = (float) (total / (1000 * 60 * 60 * 24));
            return String.format("%.1f天", days);
        }
    }
    public static String accuracy(double num, double total, int scale){
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
    public static String getPjUseTime(Long total, int size) {
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
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static int getMaxNum(Integer[] arr) {
        //将数组的第一个元素赋给max
        int max=arr[0];
        for(int i=1;i<arr.length;i++){
            //从数组的第二个元素开始赋值，依次比较
            if(arr[i]>max){
                //如果arr[i]大于最大值，就将arr[i]赋给最大值
                max=arr[i];
            }
        }
        return max;
    }
}
