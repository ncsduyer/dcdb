package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Infosrec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 区委信息督查表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface InfosrecMapper extends BaseMapper<Infosrec> {
    List<HashMap<String,Object>> getInfoByPid(@Param(value = "ew") EntityWrapper<Infosrec> ew, @Param(value = "courses") List<Checkitem> courses);
}
