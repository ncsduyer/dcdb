package cn.stylefeng.guns.modular.bigDataStatistics.vo;

import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.util.ArrayList;


public class InfoVo<T> implements Serializable {
    private ArrayList<TitleVo> titles;
    private Page<T> content;

    public ArrayList<TitleVo> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<TitleVo> titles) {
        this.titles = titles;
    }

    public Page<T> getContent() {
        return content;
    }

    public void  setContent(Page<T> content) {
        this.content = content;
    }

}
