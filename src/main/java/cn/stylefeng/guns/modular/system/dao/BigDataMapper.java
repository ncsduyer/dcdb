package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.BigData;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

public interface BigDataMapper extends BaseMapper<BigData> {

    List<HashMap<String, Integer>> countUnitStar();

    List<HashMap<String, Integer>> countAssignStatus();
}
