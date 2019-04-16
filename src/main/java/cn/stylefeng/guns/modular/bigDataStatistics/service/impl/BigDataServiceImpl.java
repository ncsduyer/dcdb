package cn.stylefeng.guns.modular.bigDataStatistics.service.impl;

import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.bigDataStatistics.dto.SreachBigDateDto;
import cn.stylefeng.guns.modular.bigDataStatistics.service.IBigDataServiceImpl;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.InfoVo;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.TitleVo;
import cn.stylefeng.guns.modular.system.dao.BigDataMapper;
import cn.stylefeng.guns.modular.system.model.BigData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-22
 */
@Service
public class BigDataServiceImpl extends ServiceImpl<BigDataMapper, BigData> implements IBigDataServiceImpl{
    @Autowired
    private BigDataMapper bigDataMapper;
    @Autowired
    private ICompanyService companyService;
    @Override
    public List<HashMap<String, Integer>> countUnitStar() {
        return bigDataMapper.countUnitStar();
    }

    @Override
    public List<HashMap<String, Integer>> countAssignStatus() {
        return bigDataMapper.countAssignStatus();
    }

    @Override
    public InfoVo infos(SreachBigDateDto sreachBigDateDto) {
        switch (sreachBigDateDto.getQueryType()){
            case 1:
                return selectDcdbInfos(sreachBigDateDto);
            case 2:
                return selectMeetInfos(sreachBigDateDto);
            case 3:
                return selectDocInfos(sreachBigDateDto);
            case 4:
                return selectInfoInfos(sreachBigDateDto);
            default:
                return null;
        }
    }
    @Override
    public InfoVo selectDcdbInfos(SreachBigDateDto sreachBigDateDto) {
        if (ToolUtil.isEmpty(sreachBigDateDto)) {
            sreachBigDateDto = new SreachBigDateDto();
        }
        try {
            new Bettime(sreachBigDateDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        InfoVo infoVo=new InfoVo();
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));


        Condition ew=Condition.create();
        Page<T> page = new Page<>(sreachBigDateDto.getPage(), sreachBigDateDto.getLimit());

        if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.ge("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.le("ta.assigntime", sreachTaskDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCreatorid())){
                ew.eq("ta.creatorid", sreachTaskDto.getCreatorid());
            }
//            拼接查询条件
            if (ToolUtil.isNotEmpty(sreachTaskDto.getTitle())){
                ew.like("t.title", sreachTaskDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getWorkType())){
                ew.in("ta.worktype", sreachTaskDto.getWorkType());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
                ew.in("ta.status", sreachTaskDto.getStatus());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAgent())){
                ew.in("tu.personid", sreachTaskDto.getAgent());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCompanyIds())){
                ew.in("tu.unitid", sreachTaskDto.getCompanyIds());
            }
            if (sreachTaskDto.getIsExceed()==1){
                ew.le("tu.endtime",new Date()).isNull("ta.endtime");
            }
            ew.groupBy("t.id,ta.id,tu.id");
            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
            }else{
                ew.orderBy("t.id,ta.id,tud.id",false);
            }

        ArrayList<HashMap<String,Object>> arrayList = bigDataMapper.selectDcdbInfos(page,ew);
//        page.setRecords(arrayList);
        infoVo.setContent(page);
        return infoVo;

    }
    @Override
    public InfoVo selectMeetInfos(SreachBigDateDto sreachBigDateDto) {
        if (ToolUtil.isEmpty(sreachBigDateDto)) {
            sreachBigDateDto = new SreachBigDateDto();
        }
        try {
            new Bettime(sreachBigDateDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            InfoVo infoVo=new InfoVo();
            infoVo.getTitles().add(new TitleVo("部门","ctitle"));
            infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
            infoVo.getTitles().add(new TitleVo("总数","total",true));
            Condition ew=Condition.create();
//            infoVo.setContent(bigDataMapper.selectMeetInfos(ew));
            return infoVo;

    }
    @Override
    public InfoVo selectDocInfos(SreachBigDateDto sreachBigDateDto) {
        if (ToolUtil.isEmpty(sreachBigDateDto)) {
            sreachBigDateDto = new SreachBigDateDto();
        }
        try {
            new Bettime(sreachBigDateDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            InfoVo infoVo=new InfoVo();
            infoVo.getTitles().add(new TitleVo("部门","ctitle"));
            infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
            infoVo.getTitles().add(new TitleVo("总数","total"));
            Condition ew=Condition.create();
//            infoVo.setContent(bigDataMapper.selectDocInfos(ew));
            return infoVo;

    }
    @Override
    public InfoVo selectInfoInfos(SreachBigDateDto sreachBigDateDto) {
        if (ToolUtil.isEmpty(sreachBigDateDto)) {
            sreachBigDateDto = new SreachBigDateDto();
        }
        try {
            new Bettime(sreachBigDateDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            InfoVo infoVo=new InfoVo();
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            infoVo.getTitles().add(new TitleVo());
            Condition ew=Condition.create();
//            infoVo.setContent(bigDataMapper.selectInfoInfos(ew));
            return infoVo;

    }
}
