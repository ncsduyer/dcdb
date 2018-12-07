package cn.stylefeng.guns.modular.zhreport.vo;

import cn.stylefeng.guns.modular.system.model.Report;

import java.util.List;
import java.util.Map;

public class ReportVo {
    private String groupName;
    private String campanyName;
    private Map<String,List<Report>> map;

    public ReportVo(String groupName, String campanyName, Map<String, List<Report>> map) {
        this.groupName = groupName;
        this.campanyName = campanyName;
        this.map = map;
    }

    public String getGroupName() {

        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCampanyName() {
        return campanyName;
    }

    public void setCampanyName(String campanyName) {
        this.campanyName = campanyName;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
