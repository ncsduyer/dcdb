package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.BigData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BigDataMapper extends BaseMapper<BigData> {

    List<HashMap<String, Integer>> countUnitStar();

    List<HashMap<String, Integer>> countAssignStatus();

    ArrayList<HashMap<String, Object>> selectDcdbInfos(Pagination page, @Param("ew")Condition ew);
}
