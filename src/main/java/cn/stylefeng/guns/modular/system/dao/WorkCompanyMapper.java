package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.WorkCompany;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 事项单位关联表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
public interface WorkCompanyMapper extends BaseMapper<WorkCompany> {
    WorkCompany selectWithManyById(Integer id);

    List<WorkCompany> selectManyList(Integer id);
}
