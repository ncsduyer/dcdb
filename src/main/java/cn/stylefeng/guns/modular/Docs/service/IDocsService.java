package cn.stylefeng.guns.modular.Docs.service;

import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Docs;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 公文运转 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IDocsService extends IService<Docs> {
    ResponseData SreachPage(SreachMeetingDto sreachDto);

    ResponseData add(AddDocDto addDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData sreachChart(SreachMeetingDto sreachDto);

    ResponseData getReports(SreachMeetingDto sreachDto);

    ResponseData edit(AddDocDto addDto);
}
