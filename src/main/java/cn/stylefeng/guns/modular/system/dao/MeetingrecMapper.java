package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 会议督查记录 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface MeetingrecMapper extends BaseMapper<Meetingrec> {

    List<HashMap<String,Object>> getInfoByPid(@Param(value = "ew") Wrapper<Meetingrec> ew, @Param(value = "courses") List<Checkitem> courses);

    List<HashMap<String, Object>> export(@Param(value = "ew") Wrapper<Meetingrec> ew, @Param(value = "courses") List<Checkitem> courses);
}
