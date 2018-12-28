package cn.stylefeng.guns.modular.MeetingRec.service;

import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会议督查记录 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IMeetingrecService extends IService<Meetingrec> {

    ResponseData selectListByDto(SreachMeetingRecDto sreachDto);
}