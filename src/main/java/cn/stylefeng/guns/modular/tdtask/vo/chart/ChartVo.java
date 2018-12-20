package cn.stylefeng.guns.modular.tdtask.vo.chart;

import java.io.Serializable;
import java.util.List;

public class ChartVo implements Serializable {
    private Legend legend;
    private Axis axis;
    private List<Series> series;

    public ChartVo() {
    }
    public ChartVo(List<Series> series,Legend legend) {
        this.legend = legend;
        this.series = series;
    }

    public ChartVo(List<Series> series,Legend legend, Axis axis) {
        this.legend = legend;
        this.axis = axis;
        this.series = series;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
