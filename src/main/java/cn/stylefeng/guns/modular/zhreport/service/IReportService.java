package cn.stylefeng.guns.modular.zhreport.service;

import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
public interface IReportService extends IService<Report> {

    ResponseData getReport(SreachReportDto sreachReportDto);

    ResponseData getChart(SreachReportDto sreachReportDto);
    ResponseData selectByUnitCount(SreachReportDto sreachReportDto);
    ResponseData selectByUnitOnlyCount(SreachReportDto sreachReportDto);
    ResponseData selectByPersionCount(SreachReportDto sreachReportDto);
    ResponseData selectByAffairCount(SreachReportDto sreachReportDto);

    void export(SreachReportDto sreachReportDto, HttpServletResponse response);
}