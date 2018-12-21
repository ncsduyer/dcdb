package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Taskassign;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 交办事项时间表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface TaskassignMapper extends BaseMapper<Taskassign> {

    Taskassign selectByManyId(@Param("id") Integer id);
}
