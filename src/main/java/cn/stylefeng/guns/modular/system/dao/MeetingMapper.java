package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Meeting;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 区委会议 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
public interface MeetingMapper extends BaseMapper<Meeting> {

    Meeting selectWithManyById(Integer id);
    ArrayList<Meeting> selectAsPage(Pagination page, @Param("ew") Wrapper<Meeting> wrapper);
    Long selectAsCount(@Param("ew") Wrapper<Meeting> wrapper);
}
