package cn.stylefeng.guns.modular.Docs.service;

import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
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
 * @since 2019-04-12
 */
public interface IDocService extends IService<Doc> {
    ResponseData SreachPage(SreachMeetingDto sreachDto);

    ResponseData add(AddDocDto addDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData sreachChart(SreachMeetingDto sreachDto);

    ResponseData getReports(SreachMeetingDto sreachDto);

    ResponseData edit(AddDocDto addDto);

    void export(SreachMeetingDto sreachDocDto, HttpServletResponse response);

    Boolean deleteMoreById(Integer id);

    ResponseData selectAsMore(SreachMeetingDto sreachMeetingRecDto);
}
