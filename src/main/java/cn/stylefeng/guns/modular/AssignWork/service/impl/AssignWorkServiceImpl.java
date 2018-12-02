package cn.stylefeng.guns.modular.AssignWork.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.AssignWork.dto.AddWorkDto;
import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 交办事项 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@Service
public class AssignWorkServiceImpl extends ServiceImpl<AssignWorkMapper, AssignWork> implements IAssignWorkService {
    @Autowired
    private AssignWorkMapper assignWorkMapper;
    @Autowired
    private IWorkCompanyService workCompanyService;

    @Override
    public Page<AssignWork> SreachPage(SreachWorkDto sreachWorkDto) throws ParseException {
        if (ToolUtil.isEmpty(sreachWorkDto)) {
            sreachWorkDto = new SreachWorkDto();
        }

        Date beforeTime = sreachWorkDto.getBeforeTime();
        Date afterTime = sreachWorkDto.getAfterTime();
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            afterTime = sdf.parse(sdf.format(afterTime));

            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }

        Page<AssignWork> page = new Page<>(sreachWorkDto.getPage(), sreachWorkDto.getLimit());

        ArrayList<AssignWork> arrayList = (ArrayList) assignWorkMapper.selectAsPage(page, sreachWorkDto, beforeTime, afterTime);

        for (AssignWork assignWork : arrayList) {
            if (ToolUtil.isEmpty(assignWork.getEndTime())) {
                assignWork.setEndTime(new Date());
            }
            assignWork.setUseTime(getUseTime(assignWork.getCreatedTime(), assignWork.getEndTime()));
        }
        page.setRecords(arrayList);
        page.setTotal(arrayList.size());
        return page;

    }

    private String getUseTime(Date createdTime, Date endTime) {
        Long total = endTime.getTime() - createdTime.getTime();
        int days = (int) (total / (1000 * 60 * 60 * 24));
        int hours = (int) (total / (1000 * 60 * 60) % 24);
        int minutes = (int) (total / (1000 * 60) % 60);
        String date = "";
        date = String.format("%d天%d小时%d分", days, hours, minutes);
        return date;
    }

    @Override
    public AssignWork selectWithManyById(Integer id) {
        AssignWork assignWork = assignWorkMapper.selectWithManyById(id);
        if (ToolUtil.isEmpty(assignWork.getEndTime())) {
            assignWork.setEndTime(new Date());
        }
        assignWork.setUseTime(getUseTime(assignWork.getCreatedTime(), assignWork.getEndTime()));
        return assignWork;
    }

    @Override
    @Transactional
    public ResponseData add(AddWorkDto addWorkDto) {
        if (ToolUtil.isNotEmpty(addWorkDto.getCompanyIds())) {
            AssignWork assignWork = new AssignWork();
            BeanUtils.copyProperties(addWorkDto, assignWork);
            assignWork.setCreatedId(ShiroKit.getUser().getId());
            assignWork.setCreatedTime(new Date());
            assignWork.setStatus(1);
            insert(assignWork);
            WorkCompany workCompany = new WorkCompany();
            workCompany.setaWId(assignWork.getId());

            for (Integer id : addWorkDto.getCompanyIds()) {
                workCompany.setCompanyId(id);
                workCompanyService.insert1(workCompany);
            }
            return ResponseData.success(assignWork);
        } else {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public boolean updateById(AssignWork entity) {
        return super.updateById(entity);
    }

    @Override
    public Object selectAsPage1(SreachWorkDto sreachWorkDto) throws ParseException {
        if (ToolUtil.isEmpty(sreachWorkDto)) {
            sreachWorkDto = new SreachWorkDto();
        }

        Date beforeTime = sreachWorkDto.getBeforeTime();
        Date afterTime = sreachWorkDto.getAfterTime();
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            afterTime = sdf.parse(sdf.format(afterTime));

            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }

        Page<Map<String, Object>> page = new Page<>(sreachWorkDto.getPage(), sreachWorkDto.getLimit());
        ArrayList<Map<String, Object>> arrayList = (ArrayList<Map<String, Object>>) assignWorkMapper.selectAsPage1(page, sreachWorkDto, beforeTime, afterTime);

        for (Map<String, Object> assignWork : arrayList) {
            if (ToolUtil.isEmpty(assignWork.get("end_time"))) {
                assignWork.put("useTime", getUseTime((Date) assignWork.get("created_time"), new Date()));
            } else {
                assignWork.put("useTime", getUseTime((Date) assignWork.get("created_time"), (Date) assignWork.get("end_time")));
            }
        }

        return page.setRecords(arrayList);

    }

    @Override
    public ResponseData update1(AssignWork assignWork) {

        try {
            if (assignWork.getStatus() != 9 || assignWork.getStatus() != 6) {
                AssignWork assignWork2 = new AssignWork();
                BeanUtils.copyProperties(assignWork,assignWork2,"createdId,createdTime,endTime,deadline,remarks");
                updateById(assignWork2);
            } else {
                if(assignWork.getEndTime().before(assignWork.getCreatedTime())){
                    return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(),"办结时间错误");
                }
                AssignWork assignWork1 = new AssignWork();
                assignWork1.setId(assignWork.getId());
                assignWork1.setStatus(assignWork.getStatus());
                assignWork1.setRemarks(assignWork.getRemarks());
                assignWork1.setEndTime(assignWork.getEndTime());
                updateById(assignWork1);
            }
            return ResponseData.success();
        } catch (Exception e) {
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }



    }
}
