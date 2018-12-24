package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Docs;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 公文运转 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface DocsMapper extends BaseMapper<Docs> {
    Docs selectWithManyById(Integer id);
    ArrayList<Docs> selectAsPage(Pagination page, @Param("ew") Wrapper<Docs> wrapper);
    Long selectAsCount(@Param("ew") Wrapper<Docs> wrapper);
}
