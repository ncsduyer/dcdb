package cn.stylefeng.guns.modular.Docs.service;

import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.system.model.DocRec;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公文督查记录 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
public interface IDocRecService extends IService<DocRec> {
    ResponseData selectListByDto(SreachMeetingRecDto sreachDto);
    List<HashMap<String, Object>> export(SreachDocDto sreachDocDto);
}
