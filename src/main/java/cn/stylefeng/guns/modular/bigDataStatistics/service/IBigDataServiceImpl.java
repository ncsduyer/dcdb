package cn.stylefeng.guns.modular.bigDataStatistics.service;

import cn.stylefeng.guns.modular.bigDataStatistics.dto.SreachBigDateDto;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.InfoVo;
import cn.stylefeng.guns.modular.system.model.BigData;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

public interface IBigDataServiceImpl extends IService<BigData> {
   List<HashMap<String,Integer>> countUnitStar();
   List<HashMap<String,Integer>> countAssignStatus();

   InfoVo infos(SreachBigDateDto sreachBigDateDto);
   InfoVo selectDcdbInfos(SreachBigDateDto sreachBigDateDto);
   InfoVo selectMeetInfos(SreachBigDateDto sreachBigDateDto);
   InfoVo selectDocInfos(SreachBigDateDto sreachBigDateDto);
   InfoVo selectInfoInfos(SreachBigDateDto sreachBigDateDto);
}
