package cn.stylefeng.guns.modular.bigDataStatistics.service.impl;

import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.bigDataStatistics.dto.SreachBigDateDto;
import cn.stylefeng.guns.modular.bigDataStatistics.service.IBigDataServiceImpl;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.InfoVo;
import cn.stylefeng.guns.modular.bigDataStatistics.vo.TitleVo;
import cn.stylefeng.guns.modular.system.dao.BigDataMapper;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitMapper;
import cn.stylefeng.guns.modular.system.model.BigData;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtaskassignUnit.vo.TaskAssignUnitVo;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    @Autowired
    private TaskassignUnitMapper taskassignUnitMapper;


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
        fomartSreachDto(sreachBigDateDto);
        InfoVo infoVo=new InfoVo();
        ArrayList<TitleVo> titles= new ArrayList<>();
        titles.add(new TitleVo("交办事项","title"));
        titles.add(new TitleVo("交办时间","assigntime",true));
        titles.add(new TitleVo("交办类型","workType"));
        titles.add(new TitleVo("责任单位","campany"));
        titles.add(new TitleVo("督办责任人","agent"));
        titles.add(new TitleVo("督办要求","assignmemo"));
        titles.add(new TitleVo("办结期限","endtime",true));
        titles.add(new TitleVo("目前状态","step"));
        titles.add(new TitleVo("是否超期","is_exceed"));
        titles.add(new TitleVo("总共用时","usetime"));
        infoVo.setTitles(titles);
        Page<TaskAssignUnitVo> page = new Page<>(sreachBigDateDto.getPage(), sreachBigDateDto.getLimit());
        EntityWrapper<TaskassignUnit> ew = new EntityWrapper<>();
        ew.setEntity(new TaskassignUnit());
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getCreatorid())) {
//            ew.eq("ta.creatorid", sreachTaskDto.getCreatorid());
//        }
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getAgent())){
//            ew.in("tu.personid", sreachTaskDto.getAgent());
//        }

//          拼接查询条件
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getTitle())){
//            ew.like("t.title", sreachTaskDto.getTitle());
//        }
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getWorkType())){
//            ew.in("ta.worktype", sreachTaskDto.getWorkType());
//        }
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getCompanyIds())){
//            ew.in("tu.unitid", sreachTaskDto.getCompanyIds());
//        }
//        if (ToolUtil.isNotEmpty(sreachTaskDto.getIsExceed())&&sreachTaskDto.getIsExceed()==1){
//            //    ew.le("tu.endtime",new Date()).isNull("ta.endtime");
//            ew.eq("tu.status", 3);
//            //    ew.eq("ta.status",3);
//        }else{
            if (ToolUtil.isNotEmpty(sreachBigDateDto.getCheckItemId())){
                ew.eq("ta.status", sreachBigDateDto.getCheckItemId());
            }
//            if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
//                if(VoUtil.getMaxNum(sreachTaskDto.getStatus())<5){
//                    ew.in("tu.status", sreachTaskDto.getStatus());
//                }
//            }
//        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getBeforeTime())){
            ew.ge("ta.assigntime", sreachBigDateDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getAfterTime())){
            ew.le("ta.assigntime", sreachBigDateDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getOrder())){
            ew.orderBy(sreachBigDateDto.getOrder());
        }else{
            ew.orderBy("tu.id",false);
        }

        ArrayList<TaskassignUnit> arrayList = taskassignUnitMapper.selectAsPage(page,ew.groupBy("tu.id"));
        ArrayList<TaskAssignUnitVo> taskVos=new ArrayList<>();
        TaskAssignUnitVo taskAssignUnitVo=null;
        for (TaskassignUnit task : arrayList) {
            task.getTaskassign().setUseTime(VoUtil.getUseTime(task.getTaskassign().getAssigntime(), task.getTaskassign().getEndtime()));
            taskAssignUnitVo=new TaskAssignUnitVo(task);
            taskVos.add(taskAssignUnitVo);
        }
        taskAssignUnitVo=null;
        page.setRecords(taskVos);
        infoVo.setContent(page);
        return infoVo;

    }
    @Override
    public InfoVo selectMeetInfos(SreachBigDateDto sreachBigDateDto) {
        fomartSreachDto(sreachBigDateDto);
        InfoVo infoVo=new InfoVo();
        infoVo.setTitles(new ArrayList<>());
            infoVo.getTitles().add(new TitleVo("部门","ctitle"));
            infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
            infoVo.getTitles().add(new TitleVo("总数","total",true));
            Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getBeforeTime())){
            ew.ge("createtime", sreachBigDateDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getAfterTime())){
            ew.le("createtime", sreachBigDateDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getCheckItemId())){
            ew.eq("checkitemid", sreachBigDateDto.getCheckItemId());
        }


        Page<HashMap<String, Object>> page = new Page<>(sreachBigDateDto.getPage(), sreachBigDateDto.getLimit());
        page.setRecords(bigDataMapper.selectMeetInfos(page,ew));
        infoVo.setContent(page);
        return infoVo;

    }


    @Override
    public InfoVo selectDocInfos(SreachBigDateDto sreachBigDateDto) {
        fomartSreachDto(sreachBigDateDto);
        InfoVo infoVo=new InfoVo();
        infoVo.setTitles(new ArrayList<>());
            infoVo.getTitles().add(new TitleVo("部门","ctitle"));
            infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
            infoVo.getTitles().add(new TitleVo("总数","total"));
            Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getBeforeTime())){
            ew.ge("createtime", sreachBigDateDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getAfterTime())){
            ew.le("createtime", sreachBigDateDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getCheckItemId())){
            ew.eq("checkitemid", sreachBigDateDto.getCheckItemId());
        }


        Page<HashMap<String, Object>> page = new Page<>(sreachBigDateDto.getPage(), sreachBigDateDto.getLimit());
        page.setRecords(bigDataMapper.selectDocInfos(page,ew));
        infoVo.setContent(page);
        return infoVo;

    }
    @Override
    public InfoVo selectInfoInfos(SreachBigDateDto sreachBigDateDto) {
        fomartSreachDto(sreachBigDateDto);
        InfoVo infoVo=new InfoVo();
        infoVo.setTitles(new ArrayList<>());
        infoVo.getTitles().add(new TitleVo("部门","ctitle"));
        infoVo.getTitles().add(new TitleVo("督查类型","chitemdesc"));
        infoVo.getTitles().add(new TitleVo("总数","total",true));
        Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getBeforeTime())){
            ew.ge("createtime", sreachBigDateDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getAfterTime())){
            ew.le("createtime", sreachBigDateDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachBigDateDto.getCheckItemId())){
            ew.eq("checkitemid", sreachBigDateDto.getCheckItemId());
        }


        Page<HashMap<String, Object>> page = new Page<>(sreachBigDateDto.getPage(), sreachBigDateDto.getLimit());
        page.setRecords(bigDataMapper.selectInfoInfos(page,ew));
        infoVo.setContent(page);
        return infoVo;

    }
    private void fomartSreachDto(SreachBigDateDto sreachBigDateDto) {
        if (ToolUtil.isEmpty(sreachBigDateDto)) {
            sreachBigDateDto = new SreachBigDateDto();
        }
        try {
            new Bettime(sreachBigDateDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
