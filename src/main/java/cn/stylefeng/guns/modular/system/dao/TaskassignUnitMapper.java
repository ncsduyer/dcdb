package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 交办事项时间-责任单位责任人表 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
public interface TaskassignUnitMapper extends BaseMapper<TaskassignUnit> {

    List<TaskassignUnit> selectList1(@Param("id") int id,@Param("personid") Integer personid);
}
