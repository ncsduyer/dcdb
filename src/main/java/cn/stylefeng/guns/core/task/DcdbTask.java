package cn.stylefeng.guns.core.task;


import cn.stylefeng.guns.config.properties.SmsProperties;
import cn.stylefeng.guns.core.util.JsonUtils;
import cn.stylefeng.guns.core.util.SmsUtil;
import cn.stylefeng.guns.modular.system.model.AppNotice;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.Condition;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 三千霜
 */
@Component
public class DcdbTask {
    @Autowired
    private IUserService userService;

    @Scheduled(cron = "0 30 15 ? * 5,6")
//    @Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void setStatus() {
        AppNotice appNotice = new AppNotice();
        if (SmsProperties.getIsAutoRemind()) {
//            获取所有督办负责人
            List<User> users = userService.selectList(Condition.create().eq("status", 1).eq("isagent", 1));
            for (User user : users) {
                appNotice.setTel(user.getPhone());
//                appNotice.setTel("18048955061");

                //发送短信
                ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
                json.put("status", "等待提交");
                json.put("remark", "温馨提示：请于本周5下午4点前提交工作情况。");
                try {
                    SmsUtil.sendSms(null,appNotice,SmsProperties.getAddDcDbtmpCode(), JsonUtils.beanToJson(json), null);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                System.out.println("发送短信"+user.getName());
            }
        }
    }
}
