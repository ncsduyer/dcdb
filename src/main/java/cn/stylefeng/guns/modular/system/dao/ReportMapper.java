package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Report;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
public interface ReportMapper extends BaseMapper<Report> {
    List<java.util.LinkedHashMap<String,Object>> selectByUnitCount(@Param("ew") Wrapper<T> wrapper, @Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByPersionCount(@Param("ew") Wrapper<T> wrapper,@Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByAffairCount(@Param("ew") Wrapper<T> wrapper,@Param("beforeTime") Date beforeTime);

    List<HashMap<String,Object>> selectByUnitChartCount(@Param("ew") Wrapper<T> wrapper, @Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByMeetChartCount(@Param("ew") Wrapper<T> wrapper, @Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByPersionChartCount(@Param("ew") Wrapper<T> wrapper,@Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByDocChartCount(@Param("ew") Wrapper<T> wrapper, @Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByInfoChartCount(@Param("ew") Wrapper<T> wrapper,@Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime);
    List<HashMap<String,Object>> selectByAffairChartCount(@Param("ew") Wrapper<T> wrapper,@Param("beforeTime") Date beforeTime);
}
