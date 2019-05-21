package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Taskassign;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 交办事项时间表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface TaskassignMapper extends BaseMapper<Taskassign> {
    ArrayList<Taskassign> selectAsPage(Pagination page, @Param("ew") Wrapper<Taskassign> wrapper);
    Taskassign selectByManyId(@Param("ew") Wrapper<Taskassign> wrapper);
    Integer selectCountByStatus(@Param("ew") Wrapper<Taskassign> wrapper);
}
