package cn.stylefeng.guns.modular.DcdbReport.service;

import cn.stylefeng.guns.modular.system.model.DcdbReport;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 督察督办报表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-21
 */
public interface IDcdbReportService extends IService<DcdbReport> {

    boolean addReport(Map<String, String> map) throws ParseException;

    ResponseData getDcdbReports(Map<String, String> map) throws ParseException;
}
