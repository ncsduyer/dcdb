package cn.stylefeng.guns.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置
 *
 * @author stylefeng
 * @Date 2017/5/23 22:31
 */
@Component
@ConfigurationProperties(prefix = SmsProperties.PREFIX)
public class SmsProperties {
    public static final String PREFIX = "sms";
    /**
     * 签名
     */
    static final String SIGN_NAME = "顺庆区委办信息化办公系统";
    static final String accessKeyId = "LTAIF8Z66n5HFs0B";
    static final String accessKeySecret = "riZoozIWNzMKGwNY8VbQjzlJ7cl4gK";
    //新工作任务通知
    static final String AddDcDbtmpCode = "SMS_159490087";
//    工作进度通知
    static final String DealDcDbtmpCode = "SMS_159490091";

    //    提醒上报
    static final String ReporttmpCode = "SMS_162197690";
    //    会议督查
    static final String MeetingtmpCode = "SMS_162197837";
    //    公文督查
    static final String DoctmpCode = "SMS_162197969";
    //    信息督查
    static final String InfotmpCode = "SMS_162197971";

    public static String getReporttmpCode() {
        return ReporttmpCode;
    }

    public static String getDealDcDbtmpCode() {
        return DealDcDbtmpCode;
    }


    public static String getAddDcDbtmpCode() {
        return AddDcDbtmpCode;
    }



    static final Boolean isAutoRemind = false;

    public static Boolean getIsAutoRemind() {
        return isAutoRemind;
    }
    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getSignName() {
        return SIGN_NAME;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static String getMeetingtmpCode() {
        return MeetingtmpCode;
    }

    public static String getDoctmpCode() {
        return DoctmpCode;
    }

    public static String getInfotmpCode() {
        return InfotmpCode;
    }
}
