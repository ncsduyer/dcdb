package cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.LogUtil;
import cn.stylefeng.guns.core.util.VoUtil;
import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.CopyRecordNotice.service.ICopyRecordNoticeService;
import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.system.dao.TaskassignUnitdealMapper;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.tdtaskassign.service.ITaskassignService;
import cn.stylefeng.guns.modular.tdtaskassignUnit.service.ITaskassignUnitService;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.dto.TaskassignUnitdealDto;
import cn.stylefeng.guns.modular.tdtaskassignUnitDeal.service.ITaskassignUnitdealService;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 交办事项时间-责任单位责任人-处理登记表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskassignUnitdealServiceImpl extends ServiceImpl<TaskassignUnitdealMapper, TaskassignUnitdeal> implements ITaskassignUnitdealService {
    @Autowired
    private ITaskassignService taskassignService;
    @Autowired
    private ITaskassignUnitService taskassignUnitService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IAppNoticeService appNoticeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICopyRecordNoticeService copyRecordNoticeService;
    @Override
    public ResponseData updateByTaskassignUnitdeal(TaskassignUnitdealDto taskassignUnitdeal) {
        try {
                TaskassignUnit taskassignUnit=taskassignUnitService.selectOne(Condition.create().eq("personid", ShiroKit.getUser().getId()).eq("id", taskassignUnitdeal.getTaunitid()));
                if (ToolUtil.isEmpty(taskassignUnit)||ToolUtil.isEmpty(taskassignUnit.getStatus())||taskassignUnit.getStatus()<2){
                    return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), "无权操作");
                }
                int oldStatus =taskassignUnit.getStatus();
                if (selectCount(Condition.create().eq("status", 1).eq("taunitid", taskassignUnitdeal.getTaunitid()))>0||taskassignUnit.getStatus()==4){
                    return new ErrorResponseData(45000, "已经完成不可重复提交！");
                }

                Taskassign taskassign=taskassignService.selectByManyId(taskassignUnit.getTassignid());
            if (taskassignUnitdeal.getStatus()==1){
                if (ToolUtil.isEmpty(taskassignUnitdeal.getFinishtime())){
                  taskassignUnitdeal.setFinishtime(new DateTime());
                }
                if (taskassignUnitdeal.getFinishtime().before(taskassignUnitdeal.getCreatetime())){
                   return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
                }
                taskassignUnitdeal.setPretime(null);
                taskassignUnitdeal.setDelaytime(null);
                taskassignUnit.setStatus(4);
            }else{
                taskassignUnitdeal.setFinishtime(null);
                if (taskassignUnitdeal.getIsdelay()==1){
                    if (ToolUtil.isNotEmpty(taskassignUnitdeal.getId())){
                        taskassignUnitdeal.setPretime(selectById(taskassignUnitdeal.getId()).getDelaytime());
                    }
                        taskassignUnitdeal.setPretime(taskassignUnitdeal.getDelaytime());
                    taskassignUnit.setStatus(3);
                }else{
                    taskassignUnit.setStatus(2);
                }

            }
            if (ToolUtil.isEmpty(taskassignUnitdeal.getId())){
                insert(taskassignUnitdeal);
            }else{
                updateById(taskassignUnitdeal);
            }
            StringBuilder st=new StringBuilder();
            st.append(ShiroKit.getUser().getName());
            st.append("，督办了交办事项:"+taskassign.getTask().getTitle()+"，");

            st.append("责任单位:");
            st.append(companyService.selectById(taskassignUnit.getUnitid()).getTitle());
            st.append(";督办时间:");
            if (ToolUtil.isNotEmpty(taskassignUnitdeal.getCreatetime())){
                st.append(VoUtil.getDate(taskassignUnitdeal.getCreatetime()));
            }else {
                st.append(" ");
            }
            st.append(";督办描述:（");
            if (ToolUtil.isNotEmpty(taskassignUnitdeal.getDealdesc())){
                st.append(taskassignUnitdeal.getDealdesc());
                st.append("）");
            }else {
                st.append("（ ）");
            }
            st.append(";完成状态:");
            if (ToolUtil.isEmpty(taskassignUnitdeal.getStatus())||taskassignUnitdeal.getStatus()==0){
                st.append("未完成");
            }else {
                st.append("完成");
            }
            st.append(";完成时间:");
            if (ToolUtil.isNotEmpty(taskassignUnitdeal.getFinishtime())){
                st.append(VoUtil.getDate(taskassignUnitdeal.getFinishtime()));
            }else {
                st.append(" ");
            }
            st.append(";是否延期:");
            if (ToolUtil.isEmpty(taskassignUnitdeal.getIsdelay())||taskassignUnitdeal.getIsdelay()==0){
                st.append("否");
            }else {
                st.append("是");
            }
            st.append(";延期时间:");
            if (ToolUtil.isNotEmpty(taskassignUnitdeal.getDelaytime())){
                st.append(VoUtil.getDate(taskassignUnitdeal.getDelaytime()));
            }else {
                st.append(" ");
            }
            LogUtil.addLog(taskassign, st.toString());
                //获取手机号
                AppNotice appNotice = new AppNotice();
                appNotice.setTitle(taskassign.getTask().getTitle());
                Map<String,String> map=new HashMap<>();
                User user = userService.selectById(taskassignUnit.getPersonid());
                map.put("title",appNotice.getTitle());
                map.put("status",taskassign.getEventStep().getStep());
                map.put("name",user.getName());
                map.put("phone",user.getPhone());
                map.put("unit",companyService.selectById(taskassignUnit.getUnitid()).getTitle());
                map.put("make",taskassignUnitdeal.getDealdesc());
                appNotice.setContent(JSONObject.toJSONString(map));
//                    appNotice.setContent(taskassign.getAssignmemo());
                appNotice.setCreatetime(new DateTime());
                appNotice.setType(1);
                appNotice.setSendee(taskassign.getCharge());
                appNotice.setTel(taskassign.getPhone());
                appNotice.setSender_id(0);
                appNotice.setNow_status(taskassign.getStatus());
                appNotice.setStep(taskassign.getEventStep().getStep());
                appNoticeService.insert(appNotice);
//                循环抄送
            if (ToolUtil.isNotEmpty(taskassignUnitdeal.getCopyRecordNotices())){
                for (CopyRecordNotice copyRecordNotice:taskassignUnitdeal.getCopyRecordNotices()){
                    copyRecordNotice.setType(1);
                    copyRecordNotice.setJoinId(taskassignUnitdeal.getId());
                    copyRecordNotice.setCreatetime(Calendar.getInstance().getTime());
                    copyRecordNotice.setSenderId(appNotice.getSender_id());
                    copyRecordNotice.setJsonContent(appNotice.getContent());
                    copyRecordNoticeService.insert(copyRecordNotice);
                }
            }



//            判断是否完成 修改unit状态 1-新建未反馈；2-已反馈限期办理中；3-已反馈超期办理中；4-办理完成；）
            if (oldStatus<taskassignUnit.getStatus()){
                List<TaskassignUnit> tsus=new ArrayList<>();
                tsus.add(taskassignUnit);
                taskassignUnitService.updateByTaskassignUnit(tsus);
            }
            return ResponseData.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
