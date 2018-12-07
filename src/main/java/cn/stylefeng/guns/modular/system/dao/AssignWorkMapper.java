package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交办事项 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
public interface AssignWorkMapper extends BaseMapper<AssignWork> {
    AssignWork selectWithManyById(Integer id);

    List<AssignWork> selectAsPage(Pagination page, @Param("dto") SreachWorkDto sreachWorkDto, @Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime);

    List<Map<String, Object>> selectAsPage1(Pagination page, @Param("dto") SreachWorkDto sreachWorkDto, @Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime);

    Integer selectCountByCompany(@Param("ew") Wrapper<T> wrapper);
    List<HashMap<Integer,Object>> selectCountByCompanyids(@Param("ew") Wrapper<T> wrapper);
}
