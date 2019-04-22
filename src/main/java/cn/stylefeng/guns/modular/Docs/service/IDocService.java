package cn.stylefeng.guns.modular.Docs.service;

import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 交办公文表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-22
 */
public interface IDocService extends IService<Doc> {
    ResponseData SreachPage(SreachDocDto sreachDto);

    ResponseData add(AddDocDto addDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData sreachChart(SreachDocDto sreachDto);

    ResponseData getReports(SreachDocDto sreachDto);

    ResponseData edit(AddDocDto addDto);

    void export(SreachDocDto sreachDocDto, HttpServletResponse response);

    Boolean deleteMoreById(Integer id);

    ResponseData selectAsMore(SreachDocDto sreachDocDto);
}
