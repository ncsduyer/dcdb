package cn.stylefeng.guns.modular.MeetingRec.service;

import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.meeting.dto.MeetingrecDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

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

    List<HashMap<String, Object>> export(SreachMeetingDto sreachMeetingDto);

    List<HashMap<String, Object>> getInfoByUnitid(Wrapper eq);

    boolean add(List<MeetingrecDto> meetingrecs);

    boolean edit(MeetingrecDto meetingrec);

    boolean editBatch(List<MeetingrecDto> meetingrecs);

    Meetingrec getInfoById(Integer id);
}
