package cn.stylefeng.guns.modular.tdtaskassignUnit.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.core.util.LogUtil;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitMapper;
import cn.stylefeng.guns.modular.system.model.Taskassign;
import cn.stylefeng.guns.modular.system.model.TaskassignUnit;
import cn.stylefeng.guns.modular.tdtask.dto.SreachTaskDto;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.vo.TaskAssignUnitVo;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 交办事项时间-责任单位责任人表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskassignUnitServiceImpl extends ServiceImpl<TaskassignUnitMapper, TaskassignUnit> implements ITaskassignUnitService {
    @Autowired
    private TaskassignUnitMapper taskassignUnitMapper;
    @Autowired
    private ITaskassignService taskassignService;
    @Autowired
    private ICompanyService companyService;
    private boolean isall=false;
    private Integer taskassignId;
    private Integer count=-1;
    private Taskassign taskassign;
    private List<TaskassignUnit> taskassignUnits=new ArrayList<>();

    @Override
    public ResponseData updateByTaskassignUnit(List<TaskassignUnit> taskassignUnits) {
        try{
            for (TaskassignUnit taskassignUnit:taskassignUnits
                 ) {
                if (!updateOne(taskassignUnit)){
                    return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), "无权操作！");
                }
            }
            taskassign=taskassignService.selectById(taskassignId);

            if (count > 0){

                if (selectCount(Condition.create().eq("status", 2).eq("tassignid", taskassignId))==count){
                    if (taskassign.getStatus()<=2){
                        taskassign.setStatus(2);
                    }
                }else if (selectCount(Condition.create().eq("status", 1).eq("tassignid", taskassignId))>0){
                    if (taskassign.getStatus()<2){
                        taskassign.setStatus(1);
                    }
                }else if (selectCount(Condition.create().eq("status", 4).eq("tassignid", taskassignId)) == count) {
                            taskassign.setStatus(4);
                    taskassign.setEndtime(new DateTime());
                } else if (selectCount(Condition.create().eq("status", 4).eq("tassignid", taskassignId))>0){
                    taskassign.setStatus(3);
                    }else{
                    if (taskassign.getStatus()<2){
                        taskassign.setStatus(1);
                    }
                }
            if (taskassignUnits.size()>1){
            int index=1;
            StringBuilder st=new StringBuilder();
            st.append(ShiroKit.getUser().getName());
            st.append("，反馈了交办事项");
            for (TaskassignUnit ts:taskassignUnits
                 ) {
                switch (ts.getStatus()) {
                    case 2:
                        st.append("序号:");
                        st.append(index);
                        st.append("责任单位:");
                        st.append(companyService.selectById(ts.getUnitid()).getTitle());
                        st.append(";办结时限:");
                        st.append(VoUtil.getDate(ts.getEndtime()));
                        st.append(";反馈信息:(");
                        st.append(ts.getRequirements());
                        st.append("),");
                        index++;
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
            st.deleteCharAt(st.length()-1);
            LogUtil.addLog(taskassign, st.toString());
            }

            }


            taskassignService.updateByTaskassign(taskassign);
            count=-1;


            return ResponseData.success();
        }catch (Exception e){
            count=-1;
            isall=false;
            taskassignId=-1;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    public boolean updateOne(TaskassignUnit taskassignUnit) {
        TaskassignUnit ts=selectOne(Condition.create().eq("personid", ShiroKit.getUser().getId()).eq("id", taskassignUnit.getId()));
        if (ToolUtil.isEmpty(ts)){
            return false;
        }
        taskassignId=ts.getTassignid();
        taskassign=taskassignService.selectById(taskassignId);
        if (count<=0){

        count=selectCount(Condition.create().eq("tassignid", taskassignId));
        }
        CopyUtils.copyProperties(taskassignUnit, ts);
        if (ts.getStatus()<2){
            if (ToolUtil.isNotEmpty(taskassignUnit.getEndtime())){
                ts.setEndtime(taskassignUnit.getEndtime());
            }
        }
        if (ToolUtil.isNotEmpty(taskassignUnit.getRequirements())){
            ts.setRequirements(taskassignUnit.getRequirements());
        }
        if (ToolUtil.isNotEmpty(taskassignUnit.getStatus())){
            ts.setStatus(taskassignUnit.getStatus());
        }

        ts.setUpdatetime(new DateTime());
        updateById(ts);
        taskassignUnits.add(ts);


        return true;
    }

    @Override
    public List<TaskassignUnit> selectList1(int id, Integer personid) {
        return taskassignUnitMapper.selectList1(id,personid);
    }

    @Override
    public ResponseData selectAsPage(SreachTaskDto sreachTaskDto) {
        try {
            if (ToolUtil.isEmpty(sreachTaskDto)) {
                sreachTaskDto = new SreachTaskDto();
            }
            Page<TaskAssignUnitVo> page = new Page<>(sreachTaskDto.getPage(), sreachTaskDto.getLimit());
            new Bettime(sreachTaskDto);
            EntityWrapper<TaskassignUnit> ew = new EntityWrapper<>();
            ew.setEntity(new TaskassignUnit());
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCreatorid())) {
                ew.eq("ta.creatorid", sreachTaskDto.getCreatorid());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAgent())){
                ew.in("tu.personid", sreachTaskDto.getAgent());
            }

//          拼接查询条件
            if (ToolUtil.isNotEmpty(sreachTaskDto.getTitle())){
                ew.like("t.title", sreachTaskDto.getTitle());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getWorkType())){
                ew.in("ta.worktype", sreachTaskDto.getWorkType());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getCompanyIds())){
                ew.in("tu.unitid", sreachTaskDto.getCompanyIds());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getIsExceed())&&sreachTaskDto.getIsExceed()==1){
                //    ew.le("tu.endtime",new Date()).isNull("ta.endtime");
                ew.eq("tu.status", 3);
                //    ew.eq("ta.status",3);
            }else{
                if (ToolUtil.isNotEmpty(sreachTaskDto.getStatus())){
                    ew.in("ta.status", sreachTaskDto.getStatus());
                }
                if (ToolUtil.isNotEmpty(sreachTaskDto.getTustatus())){
                    ew.in("tu.status", sreachTaskDto.getTustatus());
                }
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTime())){
                ew.ge("ta.assigntime", sreachTaskDto.getBeforeTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTime())){
                ew.le("ta.assigntime", sreachTaskDto.getAfterTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getBeforeTuEndTime())){
                ew.ge("tu.endtime", sreachTaskDto.getBeforeTuEndTime());
            }
            if (ToolUtil.isNotEmpty(sreachTaskDto.getAfterTuEndTime())){
                ew.le("tu.endtime", sreachTaskDto.getAfterTuEndTime());
            }

            if (ToolUtil.isNotEmpty(sreachTaskDto.getOrder())){
                ew.orderBy(sreachTaskDto.getOrder());
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
//            page.setTotal(taskassignUnitMapper.selectAsCount(ew));
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public TaskassignUnit selectMoreById(Integer id) {
        return taskassignUnitMapper.selectMoreById(id);
    }
}
