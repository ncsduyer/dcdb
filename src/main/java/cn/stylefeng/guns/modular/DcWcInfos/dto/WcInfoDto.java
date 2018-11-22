package cn.stylefeng.guns.modular.DcWcInfos.dto;

import cn.stylefeng.guns.modular.system.model.WcInfos;

public class WcInfoDto extends WcInfos {
    private Integer aWId;
    private Integer companyId;

    public Integer getaWId() {
        return aWId;
    }

    public void setaWId(Integer aWId) {
        this.aWId = aWId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
