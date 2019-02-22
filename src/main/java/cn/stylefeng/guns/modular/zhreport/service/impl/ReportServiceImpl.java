package cn.stylefeng.guns.modular.zhreport.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.ExportUtil;
import cn.stylefeng.guns.core.util.vo.ExportColSubVo;
import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.system.dao.EventTypeMapper;
import cn.stylefeng.guns.modular.system.dao.ReportMapper;
import cn.stylefeng.guns.modular.system.model.EventType;
import cn.stylefeng.guns.modular.system.model.Report;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtask.service.ITaskService;
import cn.stylefeng.guns.modular.zhreport.common.enums.QueryType;
import cn.stylefeng.guns.modular.zhreport.dto.SreachReportDto;
import cn.stylefeng.guns.modular.zhreport.service.IReportService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private EventTypeMapper eventTypeMapper;
    @Autowired
    private ITaskService taskService;
    @Override
    public ResponseData getReport(SreachReportDto sreachReportDto) {
        try {
            switch (QueryType.getQueryType(sreachReportDto.getQueryType())){
                case TASK:
                    SreachTaskDto sreachTaskDto=new SreachTaskDto();
                    BeanUtils.copyProperties(sreachReportDto, sreachTaskDto);
                    return taskService.getDcdbReports(sreachTaskDto);
                case UNIT:
                    return selectByUnitCount(sreachReportDto);
                case PERSION:
                    return selectByPersionCount(sreachReportDto);
                case AFFAIR:
                    return selectByAffairCount(sreachReportDto);
                case UnitOnly:
                    return selectByUnitOnlyCount(sreachReportDto);
                default:
                    return null;
            }
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
    @Override
    public ResponseData selectByUnitOnlyCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseData.success(reportMapper.selectByUnitOnlyCount(sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }

    @Override
    public ResponseData getChart(SreachReportDto sreachReportDto) {

        try {
            switch (QueryType.getQueryType(sreachReportDto.getQueryType())){
                case TASK:
                    SreachTaskDto sreachTaskDto = new SreachTaskDto();
                    BeanUtils.copyProperties(sreachReportDto, sreachTaskDto);
                    return taskService.sreachChart(sreachTaskDto);
                case UNIT:
                    return sreachChartByUnitCount(sreachReportDto);
                case PERSION:
                    return sreachChartByPersionCount(sreachReportDto);
                case AFFAIR:
                    return sreachChartByAffairCount(sreachReportDto);
                case MeetChart:
                    return sreachChartByMeetCount(sreachReportDto);
                case DocChart:
                    return sreachChartByDocCount(sreachReportDto);
                case InfoChart:
                    return sreachChartByInfoCount(sreachReportDto);
                default:
                    return null;
            }
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }



    private ResponseData sreachChartByUnitCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        return ResponseData.success(reportMapper.selectByUnitChartCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }
    private ResponseData sreachChartByMeetCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        return ResponseData.success(reportMapper.selectByMeetChartCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }
    private ResponseData sreachChartByDocCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        return ResponseData.success(reportMapper.selectByDocChartCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }
    private ResponseData sreachChartByInfoCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        return ResponseData.success(reportMapper.selectByInfoChartCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }

    private ResponseData sreachChartByPersionCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        return ResponseData.success( reportMapper.selectByPersionChartCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }
    private ResponseData sreachChartByAffairCount(SreachReportDto sreachReportDto) {
        return ResponseData.success(reportMapper.selectByAffairChartCount(null,sreachReportDto.getBeforeTime()));
    }

    @Override
    public ResponseData selectByUnitCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime())){
            ew.ge("taskunit.createtime", sreachReportDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
            ew.le("taskunit.createtime", sreachReportDto.getAfterTime());
        }
        return ResponseData.success(reportMapper.selectByUnitCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }

    @Override
    public ResponseData selectByPersionCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime())){
            ew.ge("td_taskassign.assigntime", sreachReportDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
            ew.le("td_taskassign.assigntime", sreachReportDto.getAfterTime());
        }
        return ResponseData.success(reportMapper.selectByPersionCount(ew,sreachReportDto.getAfterTime(),sreachReportDto.getBeforeTime()));
    }

    @Override
    public ResponseData selectByAffairCount(SreachReportDto sreachReportDto) {
        return ResponseData.success(reportMapper.selectByAffairCount(null,sreachReportDto.getBeforeTime()));
    }

    @Override
    public void export(SreachReportDto sreachReportDto, HttpServletResponse response) throws Exception {
        switch (QueryType.getQueryType(sreachReportDto.getQueryType())){
            case TASK:
                SreachTaskDto sreachTaskDto = new SreachTaskDto();
                BeanUtils.copyProperties(sreachReportDto, sreachTaskDto);
                taskService.export(sreachTaskDto,response);
                break;
            case UNIT:
                ecportByUnitCount(sreachReportDto,response);
                break;
            case PERSION:
                 ecportByPersionCount(sreachReportDto,response);
                 break;
            case AFFAIR:
                 ecportByAffairCount(sreachReportDto,response);
                 break;
            default:
               break;
        }
    }

    private void ecportByUnitCount(SreachReportDto sreachReportDto, HttpServletResponse response) {
        String sheetName = "责任单位相关统计表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        ArrayList<EventType> eventTypes=eventTypeMapper.selectAllMore();
        List<java.util.LinkedHashMap<String,Object>> maps = (List<LinkedHashMap<String, Object>>) selectByUnitCount(sreachReportDto).getData();
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        ExportRowVo title1=  new ExportRowVo();
        title.setTotal(1);
        title1.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"")));
        title1.getColVos().add(new ExportColVo(new ExportColSubVo(1,"单位名称")));
        for (int i = 0; i <eventTypes.size(); i++) {
            title.getColVos().add(new ExportColVo(new ExportColSubVo(1,eventTypes.get(i).getCheckitems().size(),eventTypes.get(i).getReportAlias())));
            for (int j = 0; j <eventTypes.get(i).getCheckitems().size(); j++){
                title1.getColVos().add(new ExportColVo(new ExportColSubVo(1,eventTypes.get(i).getCheckitems().get(j).getItemdesc())));
            }
        }
        titles.add(title);
        titles.add(title1);
        int index=1;
        for (LinkedHashMap<String,Object> map : maps) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(1);
            map.remove("unitid");
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                if (ToolUtil.isNotEmpty(entry.getValue())){
                    if (entry.getValue() instanceof Double){
                        exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1,String.valueOf(Integer.parseInt(new java.text.DecimalFormat("0").format(entry.getValue()))))));
                    }else{
                        exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1,entry.getValue().toString())));
                    }
                }else{
                exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, "0")));

                }
            }
            exportRowVos.add(exportRowVo);
            index++;
        }

        ExportUtil.outExport(sreachReportDto, response, titles, sheetName, exportRowVos);
    }

    private void ecportByPersionCount(SreachReportDto sreachReportDto, HttpServletResponse response) {
        String sheetName = "区委办人员相关统计表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = (List<HashMap<String, Object>>) selectByPersionCount(sreachReportDto).getData();
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"序号")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"姓名")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"督办单位数")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"事项新建数")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"事项督办数")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"会议新建数")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"公文新建数")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"信息新建数")));
        titles.add(title);
        int index=1;
        for (HashMap<String,Object> map : maps) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(1);
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, String.valueOf(index))));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("name").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("taskCrenum").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("unitnum").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("dealnum").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("meetnum").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("assignnum").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("infonum").toString())));
            exportRowVos.add(exportRowVo);
            index++;
        }

        ExportUtil.outExport(sreachReportDto, response, titles, sheetName, exportRowVos);
    }

    private void ecportByAffairCount(SreachReportDto sreachReportDto, HttpServletResponse response) {
        String sheetName = "区委信息数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = (List<HashMap<String, Object>>) selectByAffairCount(sreachReportDto).getData();
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"序号")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"项目名称")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"一月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"二月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"三月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"四月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"五月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"六月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"七月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"八月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"九月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"十月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"十一月")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"十二月")));
        titles.add(title);
        int index=1;

        for (HashMap<String,Object> map : maps) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(1);
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, String.valueOf(index))));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("titlename").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("one").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("two").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("three").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("four").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("five").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("six").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("seven").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("eight").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("nine").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("ten").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("eleven").toString())));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, map.get("twelve").toString())));
            exportRowVos.add(exportRowVo);
            index++;
        }

        ExportUtil.outExport(sreachReportDto, response, titles, sheetName, exportRowVos);
    }


}
