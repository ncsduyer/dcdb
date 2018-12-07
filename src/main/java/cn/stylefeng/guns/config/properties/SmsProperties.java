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
    static final String SIGN_NAME = "电大助手";
    static final String accessKeyId = "LTAIfwJzYqznM6jI";
    static final String accessKeySecret = "ocwhBKzkGVxdi0yM5jn7UkFyjFdfkk";

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
