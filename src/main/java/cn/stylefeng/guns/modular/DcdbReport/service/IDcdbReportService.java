package cn.stylefeng.guns.modular.DcdbReport.service;

import cn.stylefeng.guns.modular.system.model.DcdbReport;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;
import java.util.Date;
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

    boolean addReport(Map<String, Date> map) throws ParseException;
}
