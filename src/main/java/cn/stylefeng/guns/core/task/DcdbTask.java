package cn.stylefeng.guns.core.task;

import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import com.baomidou.mybatisplus.mapper.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DcdbTask {
    @Autowired
    private IAssignWorkService assignWorkService;
    @Autowired
    private IWorkCompanyService workCompanyService;

    @Scheduled(cron = "0 30 23 * * ?")
    public void setStatus() {
        AssignWork assignWork = new AssignWork();
        assignWork.setStatus(5);
        assignWorkService.update(assignWork, Condition.create()
                .lt("deadline", new Date()).isNull("end_time").ne("status", 5));

        WorkCompany workCompany = new WorkCompany();
        workCompany.setSituationType(1);
        workCompanyService.update(workCompany, Condition.create()
                .lt("deadline", new Date()).eq("status", 0));
    }
}
