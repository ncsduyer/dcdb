package cn.stylefeng.guns.modular.zhreport.common.enums;

/**
 * @author 三千霜
 * @Description 所有查询类型的枚举
 */
public enum QueryType {
    /**
     * 类型
     */
    TASK(1, "督办事项统计"),
    UNIT(2, "责任单位统计"),
    PERSION(3, "责任人员统计"),
    AFFAIR(4, "事务统计"),
    MeetChart(5, "会议图形报表"),
    DocChart(6, "公文图形报表"),
    InfoChart(7, "信息图形报表");

    private Integer code;

    private String title;

    QueryType(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static QueryType getQueryType(int max) {
        switch (max) {
            case 1:
                return QueryType.TASK;
            case 2:
                return QueryType.UNIT;
            case 3:
                return QueryType.PERSION;
            case 4:
                return QueryType.AFFAIR;
            case 5:
                return QueryType.MeetChart;
            case 6:
                return QueryType.DocChart;
            case 7:
                return QueryType.InfoChart;

            default:
                return null;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String message) {
        this.title = message;
    }
}
