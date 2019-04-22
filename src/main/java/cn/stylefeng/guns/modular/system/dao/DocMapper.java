package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Doc;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 交办公文表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-22
 */
public interface DocMapper extends BaseMapper<Doc> {
    Doc selectWithManyById(Integer id);
    ArrayList<Doc> selectAsPage(Pagination page, @Param("ew") Wrapper<Doc> wrapper);
    Long selectAsCount(@Param("ew") Wrapper<Doc> wrapper);
    List<Doc> selectAsMore(@Param("ew") Wrapper<Doc> wrapper);
}
