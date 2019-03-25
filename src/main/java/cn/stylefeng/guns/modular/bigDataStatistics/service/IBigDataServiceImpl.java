package cn.stylefeng.guns.modular.bigDataStatistics.service;

import cn.stylefeng.guns.modular.system.model.BigData;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

public interface IBigDataServiceImpl extends IService<BigData> {
   List<HashMap<String,Integer>> countUnitStar();
   List<HashMap<String,Integer>> countAssignStatus();
}
