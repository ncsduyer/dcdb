package cn.stylefeng.guns.modular.api.vo;

public class AppMenusVo implements Comparable<AppMenusVo> {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 菜单编号
     */
    private String code;


    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * url地址
     */
    private String url;

    private Integer num;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    @Override
    public int compareTo(AppMenusVo o) {
        //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
        if (this.id >= o.getId()) {
            return 1;
        }
        return -1;
    }
}
