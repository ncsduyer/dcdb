package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.dto;

import cn.stylefeng.guns.modular.system.model.CopyRecordNotice;
import cn.stylefeng.guns.modular.system.model.TaskassignUnitdeal;

import java.util.List;

public class TaskassignUnitdealDto extends TaskassignUnitdeal {
    private List<CopyRecordNotice> copyRecordNotices;

    public List<CopyRecordNotice> getCopyRecordNotices() {
        return copyRecordNotices;
    }

    public void setCopyRecordNotices(List<CopyRecordNotice> copyRecordNotices) {
        this.copyRecordNotices = copyRecordNotices;
    }
}
