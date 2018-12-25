package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Infos;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 区委信息 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface InfosMapper extends BaseMapper<Infos> {
    Infos selectWithManyById(Integer id);
    ArrayList<Infos> selectAsPage(Pagination page, @Param("ew") Wrapper<Infos> wrapper);
    Long selectAsCount(@Param("ew") Wrapper<Infos> wrapper);
}
