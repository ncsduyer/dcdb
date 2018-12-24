package cn.stylefeng.guns.modular.MeetingRec.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.system.dao.MeetingrecMapper;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会议督查记录 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class MeetingrecServiceImpl extends ServiceImpl<MeetingrecMapper, Meetingrec> implements IMeetingrecService {
    @Autowired
    private MeetingrecMapper meetingrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Override
    public ResponseData selectListByDto(SreachMeetingRecDto sreachDto) {
        try{
            EntityWrapper<Meetingrec> ew = new EntityWrapper<>();
            ew.setEntity(new Meetingrec());
            if (ToolUtil.isNotEmpty(sreachDto.getPid())){
                ew.eq("rec.meetingid", sreachDto.getPid());
            }

            if (ToolUtil.isNotEmpty(sreachDto.getCheckitemid())){
                ew.in("rec.checkitemid", sreachDto.getCheckitemid());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("rec.unitid", sreachDto.getCompanyIds());
            }
            ew.orderBy("rec.createtime");
            return ResponseData.success(meetingrecMapper.getInfoByPid(ew,checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1))));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
