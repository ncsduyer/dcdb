package cn.stylefeng.guns.modular.bigDataStatistics.vo;

import java.io.Serializable;

public class TitleVo implements Serializable {
    //    title	列头显示文字	String	#
    private String title;
    //    key	对应列内容的字段名	String	-
    private String key;
    //    sortable	对应列是否可以排序，如果设置为 custom，则代表用户希望远程排序，需要监听 Table 的 on-sort-change 事件	Boolean	false
    private Boolean sortable=false;
    // width	列宽	Number	-
    private Integer width;
    //    align	对齐方式，可选值为 left 左对齐、right 右对齐和 center 居中对齐	String	left
    private String align;
    //    className	列的样式名称	String	-
    private String className;
    //    fixed	列是否固定在左侧或者右侧，可选值为 left 左侧和 right 右侧	String	-
    private String fixed;
    //    ellipsis	开启后，文本将不换行，超出部分显示为省略号	Boolean	false
    private Boolean ellipsis=false;

    public TitleVo() {
    }

    public TitleVo(String title, String key) {
        this.title = title;
        this.key = key;
    }

    public TitleVo(String title, String key, Boolean sortable) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
    }

    public TitleVo(String title, String key, Boolean sortable, Integer width) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
        this.width = width;
    }

    public TitleVo(String title, String key, Boolean sortable, Integer width, String align) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
        this.width = width;
        this.align = align;
    }

    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
        this.width = width;
        this.align = align;
        this.className = className;
    }

    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className, String fixed) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
        this.width = width;
        this.align = align;
        this.className = className;
        this.fixed = fixed;
    }

    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className, String fixed, Boolean ellipsis) {
        this.title = title;
        this.key = key;
        this.sortable = sortable;
        this.width = width;
        this.align = align;
        this.className = className;
        this.fixed = fixed;
        this.ellipsis = ellipsis;
    }
}
