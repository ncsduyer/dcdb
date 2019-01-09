package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Docassignrec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公文运转记录 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface DocassignrecMapper extends BaseMapper<Docassignrec> {
    List<HashMap<String,Object>> getInfoByPid(@Param(value = "ew") Wrapper<Docassignrec> ew, @Param(value = "courses") List<Checkitem> courses);
    List<HashMap<String, Object>> export(@Param(value = "sreachDto") SreachMeetingDto sreachDto, @Param(value = "courses") List<Checkitem> courses);
}
