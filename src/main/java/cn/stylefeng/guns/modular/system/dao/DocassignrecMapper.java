package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Docassignrec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

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
    List<HashMap<String,Object>> getInfoByPid(EntityWrapper<Docassignrec> ew);
}
