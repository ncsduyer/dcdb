package cn.stylefeng.guns.modular.Infos.service;

import cn.stylefeng.guns.modular.Infos.dto.AddInfoDto;
import cn.stylefeng.guns.modular.Infos.dto.SreachInfoDto;
import cn.stylefeng.guns.modular.system.model.Infos;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 区委信息 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IInfosService extends IService<Infos> {

    ResponseData SreachPage(SreachInfoDto sreachDto);

    ResponseData add(AddInfoDto addDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData sreachChart(SreachInfoDto sreachDto);

    ResponseData getReports(SreachInfoDto sreachDto);

    ResponseData edit(AddInfoDto addDto);

    void export(SreachInfoDto sreachInfoDto, HttpServletResponse response);
}
