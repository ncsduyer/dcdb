package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.config.properties.SmsProperties;
import cn.stylefeng.guns.modular.system.model.AppNotice;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsUtil {
    static Logger log = LoggerFactory.getLogger(SmsUtil.class);
    /**
     *产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";
    /**
     *产品域名,开发者无需替换
     */
    static final String domain = "dysmsapi.aliyuncs.com";

    /**
    *   业务短信发送间隔有效期为1分钟
     */
    public static final Long SMS_SEND_EXPIRE = 60*1000L;
    /**
     *  引入短信配置文件
     */



    public static SendSmsResponse sendSms(String signName,AppNotice appNotice,String templateCode,String templateParam,String outId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        System.out.println(SmsProperties.getAccessKeyId()+"||"+ SmsProperties.getAccessKeySecret());
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsProperties.getAccessKeyId(), SmsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(appNotice.getTel());
        //必填:短信签名-可在短信控制台中找到
        if (ToolUtil.isNotEmpty(signName)){
            request.setSignName(signName);
        }else{
            request.setSignName(SmsProperties.getSignName());
        }
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        if(null != outId && outId.length() > 0){
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(outId);
        }

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("\n " + request.getPhoneNumbers() + ":" + request.getTemplateParam());
        System.out.println(JsonUtils.beanToJson(sendSmsResponse) + "\n" );

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(AppNotice appNotice,String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsProperties.getAccessKeyId(), SmsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(appNotice.getTel());
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

        public static void main(String[] args) {
            AppNotice appNotice=new AppNotice();
            appNotice.setTel("18048955061");
//            appNotice.setTel("15390401964");
            appNotice.setTitle("会议精神传达");
            ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
            json.put("status", "未反馈");
            json.put("remark", appNotice.getTitle());
            try {
//                SendSmsResponse sendSmsResponse = SmsUtil.sendSms(null,appNotice,SmsProperties.getAddDcDbtmpCode(), JsonUtils.beanToJson(json), null);

                System.out.println(JsonUtils.beanToJson(SmsUtil.querySendDetails(appNotice, "790115451337295180^0")) + "\n" );
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
}
