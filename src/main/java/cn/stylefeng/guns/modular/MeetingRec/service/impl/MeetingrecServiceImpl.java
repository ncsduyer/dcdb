package cn.stylefeng.guns.modular.MeetingRec.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.MeetingRec.service.IMeetingrecService;
import cn.stylefeng.guns.modular.attrs.service.IMeetingRecAttrService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.meeting.dto.MeetingrecDto;
import cn.stylefeng.guns.modular.meeting.dto.SreachMeetingDto;
import cn.stylefeng.guns.modular.system.dao.MeetingrecMapper;
import cn.stylefeng.guns.modular.system.model.Meetingrec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 会议督查记录 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MeetingrecServiceImpl extends ServiceImpl<MeetingrecMapper, Meetingrec> implements IMeetingrecService {
    @Autowired
    private MeetingrecMapper meetingrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IMeetingRecAttrService meetingRecAttrService;
    @Override
    public ResponseData selectListByDto(SreachMeetingRecDto sreachDto) {
        try{
            EntityWrapper<Meetingrec> ew = new EntityWrapper<>();
            ew.setEntity(new Meetingrec());
            if (ToolUtil.isNotEmpty(sreachDto.getPid())){
                ew.eq("meetingid", sreachDto.getPid());
            }

            if (ToolUtil.isNotEmpty(sreachDto.getCheckitemid())){
                ew.in("checkitemid", sreachDto.getCheckitemid());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("unitid", sreachDto.getCompanyIds());
            }
            ew.orderBy("createtime");
            return ResponseData.success(meetingrecMapper.getInfoByPid(ew,checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1))));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public List<HashMap<String, Object>> export(SreachMeetingDto sreachDto) {
        if (ToolUtil.isEmpty(sreachDto)){
            sreachDto=new SreachMeetingDto();
        }
        try {
            new Bettime(sreachDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return meetingrecMapper.export(sreachDto,checkitemService.selectList(Condition.create().eq("itemclass", 2).eq("status", 1)));
    }

    @Override
    public List<HashMap<String, Object>> getInfoByUnitid(Wrapper eq) {
        return meetingrecMapper.getInfoByUnitid(eq);
    }

    @Override
    public boolean add(List<MeetingrecDto> meetingrecs) {
        Meetingrec meetingrec=null;
        if (ToolUtil.isNotEmpty(meetingrecs)) {
//                循环插入交办单位
            for (MeetingrecDto map : meetingrecs) {
                meetingrec= new Meetingrec();
                BeanUtils.copyProperties(map, meetingrec);
                meetingrec.setCheckvalue("1");
                if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                    meetingrec.setCreatetime(new DateTime());
                }
                meetingrecMapper.insert(meetingrec);
//                List<MeetingRecAttr> meetingRecAttrs=null;
//                if(ToolUtil.isNotEmpty(map.getFiles())){
//                    meetingRecAttrs = AttrUnit.getMeetingRecAttrs(meetingrec.getId(), map.getFiles(), 2,meetingRecAttrs,null,meetingRecAttrService);
//                    meetingRecAttrService.insertOrUpdateBatch(meetingRecAttrs);
//                }
//                if(ToolUtil.isNotEmpty(map.getPictures())){
//                    meetingRecAttrs = AttrUnit.getMeetingRecAttrs(meetingrec.getId(),map.getPictures(), 1,meetingRecAttrs,null,meetingRecAttrService);
//                    meetingRecAttrService.insertOrUpdateBatch(meetingRecAttrs);
//                }
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean edit(MeetingrecDto map) {
        Meetingrec meetingrec=new Meetingrec();
        CopyUtils.copyProperties(map, meetingrec);
        meetingrec.setCheckvalue("1");
        meetingrecMapper.updateById(meetingrec);
//        List<MeetingRecAttr> meetingRecAttrs=null;
//        if(ToolUtil.isNotEmpty(map.getFiles())){
//            meetingRecAttrs = AttrUnit.getMeetingRecAttrs(meetingrec.getId(), map.getFiles(), 2,meetingRecAttrs,meetingRecAttrService.selectList(Condition.create().eq("pid", meetingrec.getId()).eq("type", 2)),meetingRecAttrService);
//            meetingRecAttrService.insertOrUpdateBatch(meetingRecAttrs);
//        }
//        if(ToolUtil.isNotEmpty(map.getPictures())){
//            meetingRecAttrs = AttrUnit.getMeetingRecAttrs(meetingrec.getId(),map.getPictures(), 1,meetingRecAttrs,meetingRecAttrService.selectList(Condition.create().eq("pid", meetingrec.getId()).eq("type", 1)),meetingRecAttrService);
//            meetingRecAttrService.insertOrUpdateBatch(meetingRecAttrs);
//        }
        return true;
    }

    @Override
    public boolean editBatch(List<MeetingrecDto> meetingrecs) {
        Boolean sucess=false;
        for (MeetingrecDto m :meetingrecs) {
            sucess=edit(m);
        }
        return sucess;
    }

    @Override
    public Meetingrec getInfoById(Integer id) {
        return meetingrecMapper.getInfoById(id);
    }

}
