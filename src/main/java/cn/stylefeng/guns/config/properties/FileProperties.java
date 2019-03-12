package cn.stylefeng.guns.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {
    public static final String PREFIX = "file";
    static final String URL = "/admin";

    public static String getURL() {
        return URL;
    }
    public static String getPREFIX() {
        return PREFIX;
    }
}
