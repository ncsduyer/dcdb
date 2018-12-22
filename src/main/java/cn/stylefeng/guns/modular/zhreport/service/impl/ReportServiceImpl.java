package cn.stylefeng.guns.modular.zhreport.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.modular.system.dao.ReportMapper;
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

import java.text.ParseException;

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
    private ITaskService taskService;
    @Override
    public ResponseData getReport(SreachReportDto sreachReportDto) {
        try {
            switch (QueryType.getQueryType(sreachReportDto.getQueryType())){
                case TASK:
                    SreachTaskDto sreachTaskDto=new SreachTaskDto();
                    BeanUtils.copyProperties(sreachReportDto, sreachTaskDto);
                     return taskService.getDcdbReports( sreachTaskDto);
                case UNIT:
                     return selectByUnitCount(sreachReportDto);
                 case PERSION:
                     return selectByPersionCount(sreachReportDto);
                case AFFAIR:
                     return selectByAffairCount(sreachReportDto);
                default:
                    return null;
            }
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
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
        if (ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime())){
            ew.gt("taskunit.createtime", sreachReportDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
            ew.lt("taskunit.createtime", sreachReportDto.getAfterTime());
        }
        reportMapper.selectByUnitCount(ew);
        return null;
    }

    private ResponseData sreachChartByPersionCount(SreachReportDto sreachReportDto) {
        try {
            new Bettime(sreachReportDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Condition ew=Condition.create();
        if (ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime())){
            ew.gt("td_taskassign.assigntime", sreachReportDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
            ew.lt("td_taskassign.assigntime", sreachReportDto.getAfterTime());
        }
        reportMapper.selectByPersionCount(ew);
        return null;
    }
    private ResponseData sreachChartByAffairCount(SreachReportDto sreachReportDto) {
        reportMapper.selectByAffairCount(null);
        return null;
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
                ew.gt("taskunit.createtime", sreachReportDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
                ew.lt("taskunit.createtime", sreachReportDto.getAfterTime());
            }
        return ResponseData.success(reportMapper.selectByUnitCount(ew));
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
            ew.gt("td_taskassign.assigntime", sreachReportDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
            ew.lt("td_taskassign.assigntime", sreachReportDto.getAfterTime());
        }
        return ResponseData.success(reportMapper.selectByPersionCount(ew));
    }

    @Override
    public ResponseData selectByAffairCount(SreachReportDto sreachReportDto) {
//        try {
//            new Bettime(sreachReportDto);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Condition ew=Condition.create();
//        if (ToolUtil.isNotEmpty(sreachReportDto.getBeforeTime())){
//            ew.gt("taskunit.createtime", sreachReportDto.getBeforeTime());
//        }
//        if (ToolUtil.isNotEmpty(sreachReportDto.getAfterTime())){
//            ew.lt("taskunit.createtime", sreachReportDto.getAfterTime());
//        }
        return ResponseData.success(reportMapper.selectByAffairCount(null));
    }
}
