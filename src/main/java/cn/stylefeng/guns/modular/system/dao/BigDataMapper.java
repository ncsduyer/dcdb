package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.BigData;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BigDataMapper extends BaseMapper<BigData> {

    List<HashMap<String, Integer>> countUnitStar();

    List<HashMap<String, Integer>> countAssignStatus();

    ArrayList<HashMap<String, Object>> selectDcdbInfos(Pagination page, @Param("ew") Condition ew);

    ArrayList<HashMap<String, Object>> selectMeetInfos(Pagination page, @Param("ew") Condition ew);

    ArrayList<HashMap<String, Object>> selectDocInfos(Pagination page, @Param("ew") Condition ew);

    ArrayList<HashMap<String, Object>> selectInfoInfos(Pagination page, @Param("ew") Condition ew);

    Integer countAssignStatus1(@Param("ew") Wrapper<Taskassign> ew);
}
