package cn.stylefeng.guns.core.task;

import cn.stylefeng.guns.core.util.SmsUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SmsTask {
    @Async
    public SendSmsResponse sendSms(String signName, String tel, String templateCode, String templateParam, String outId) throws ClientException {
        return SmsUtil.sendSms(signName,tel, templateCode, templateParam, outId);
    }

}
