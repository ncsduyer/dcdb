package cn.stylefeng.guns.modular.DocAssignRec.service;

import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Docassignrec;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公文运转记录 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IDocassignrecService extends IService<Docassignrec> {
    ResponseData selectListByDto(SreachMeetingRecDto sreachDto);

    List<HashMap<String, Object>> export(SreachMeetingDto sreachDocDto);
}
