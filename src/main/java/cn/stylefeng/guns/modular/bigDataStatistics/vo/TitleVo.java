package cn.stylefeng.guns.modular.bigDataStatistics.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

public class TitleVo implements Serializable {
    //    title	列头显示文字	String	#
    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
    private String title=null;
    //    key	对应列内容的字段名	String	-
    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
    private String key=null;
    //    sortable	对应列是否可以排序，如果设置为 custom，则代表用户希望远程排序，需要监听 Table 的 on-sort-change 事件	Boolean	false
    private Boolean sortable=false;
    // width	列宽	Number	-
//    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
//    private Integer width=null;
//    //    align	对齐方式，可选值为 left 左对齐、right 右对齐和 center 居中对齐	String	left
//    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
//    private String align=null;
//    //    className	列的样式名称	String	-
//    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
//    private String className=null;
//    //    fixed	列是否固定在左侧或者右侧，可选值为 left 左侧和 right 右侧	String	-
//    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
//    private String fixed=null;
//    //    ellipsis	开启后，文本将不换行，超出部分显示为省略号	Boolean	false
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

//    public TitleVo(String title, String key, Boolean sortable, Integer width) {
//        this.title = title;
//        this.key = key;
//        this.sortable = sortable;
//        this.width = width;
//    }
//
//    public TitleVo(String title, String key, Boolean sortable, Integer width, String align) {
//        this.title = title;
//        this.key = key;
//        this.sortable = sortable;
//        this.width = width;
//        this.align = align;
//    }
//
//    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className) {
//        this.title = title;
//        this.key = key;
//        this.sortable = sortable;
//        this.width = width;
//        this.align = align;
//        this.className = className;
//    }
//
//    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className, String fixed) {
//        this.title = title;
//        this.key = key;
//        this.sortable = sortable;
//        this.width = width;
//        this.align = align;
//        this.className = className;
//        this.fixed = fixed;
//    }
//
//    public TitleVo(String title, String key, Boolean sortable, Integer width, String align, String className, String fixed, Boolean ellipsis) {
//        this.title = title;
//        this.key = key;
//        this.sortable = sortable;
//        this.width = width;
//        this.align = align;
//        this.className = className;
//        this.fixed = fixed;
//        this.ellipsis = ellipsis;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

//    public Integer getWidth() {
//        return width;
//    }
//
//    public void setWidth(Integer width) {
//        this.width = width;
//    }
//
//    public String getAlign() {
//        return align;
//    }
//
//    public void setAlign(String align) {
//        this.align = align;
//    }
//
//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//
//    public String getFixed() {
//        return fixed;
//    }
//
//    public void setFixed(String fixed) {
//        this.fixed = fixed;
//    }

    public Boolean getEllipsis() {
        return ellipsis;
    }

    public void setEllipsis(Boolean ellipsis) {
        this.ellipsis = ellipsis;
    }
}
