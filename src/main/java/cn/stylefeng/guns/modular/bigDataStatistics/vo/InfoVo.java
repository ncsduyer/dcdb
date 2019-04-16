package cn.stylefeng.guns.modular.bigDataStatistics.vo;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;


public class InfoVo {
    private ArrayList<TitleVo> titles=new ArrayList<>();
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

    public <T> void  setContent(Page<org.apache.poi.ss.formula.functions.T> content) {
        this.content = content;
    }

}
