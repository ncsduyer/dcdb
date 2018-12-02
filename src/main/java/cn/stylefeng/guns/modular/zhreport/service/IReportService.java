package cn.stylefeng.guns.modular.zhreport.service;

import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.zhreport.dto.AddReportDto;
import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
public interface IReportService extends IService<Report> {

    ResponseData getList(SreachReportDto sreachReportDto) throws ParseException;
    ResponseData add(AddReportDto addReportDto);

    Boolean deleteByGidCompany(Integer groupId, String company);
}
