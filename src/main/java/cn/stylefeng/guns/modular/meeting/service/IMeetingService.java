package cn.stylefeng.guns.modular.meeting.service;

import cn.stylefeng.guns.modular.meeting.dto.AddMeetingDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Meeting;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 区委会议 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IMeetingService extends IService<Meeting> {

    ResponseData SreachPage(SreachMeetingDto sreachDto);

    ResponseData add(AddMeetingDto addDto);

    ResponseData selectWithManyById(Integer id);

    ResponseData sreachChart(SreachMeetingDto sreachDto);

    ResponseData getReports(SreachMeetingDto sreachDto);

    ResponseData edit(AddMeetingDto meeting);

    void export(SreachMeetingDto sreachMeetingDto, HttpServletResponse response);

    Boolean deleteMoreById(Integer assignWorkId);
}
