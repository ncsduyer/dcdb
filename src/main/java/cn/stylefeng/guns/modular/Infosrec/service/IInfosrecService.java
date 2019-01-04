package cn.stylefeng.guns.modular.Infosrec.service;

import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Infosrec;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 区委信息督查表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface IInfosrecService extends IService<Infosrec> {
    ResponseData selectListByDto(SreachMeetingRecDto sreachDto);

    List<HashMap<String, Object>> export(SreachMeetingDto sreachInfoDto);
}
