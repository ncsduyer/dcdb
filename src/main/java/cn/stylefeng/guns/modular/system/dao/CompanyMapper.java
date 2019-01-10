package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Company;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 责任单位 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2019-01-10
 */
public interface CompanyMapper extends BaseMapper<Company> {

    List<Company> selectMoreList(@Param("ew") Wrapper<Company> wrapper);
}
