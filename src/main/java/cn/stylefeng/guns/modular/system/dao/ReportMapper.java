package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Report;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
public interface ReportMapper extends BaseMapper<Report> {

    ArrayList<Report> selectMoreList(@Param("ew") Wrapper<T> wrapper);
}
