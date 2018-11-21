package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.WorkFlowLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 督察督办流程记录 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-05
 */
public interface WorkFlowLogMapper extends BaseMapper<WorkFlowLog> {
    List<WorkFlowLog> selectByManyId(@Param("id") Integer id);
}
