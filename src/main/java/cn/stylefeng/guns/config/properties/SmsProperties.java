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
    static final String AddDcDbtmpCode = "SMS_152207559";

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


}
