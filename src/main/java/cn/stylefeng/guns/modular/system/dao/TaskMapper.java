package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Task;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 交办事项表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface TaskMapper extends BaseMapper<Task> {
    Task selectWithManyById(Integer id);
    ArrayList<Task> selectAsPage(Pagination page, @Param("ew") Wrapper<Task> wrapper);
    Integer selectCountByUnit(@Param("ew") Wrapper<T> wrapper);
    List<HashMap<Integer,Object>> selectCountByUnitIds(@Param("ew") Wrapper<T> wrapper);
//    ArrayList<Task> selectExport(Pagination page, @Param("ew") Wrapper<Task> wrapper);
}
