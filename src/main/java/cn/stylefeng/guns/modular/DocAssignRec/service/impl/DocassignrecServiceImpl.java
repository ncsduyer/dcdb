package cn.stylefeng.guns.modular.DocAssignRec.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.DocAssignRec.service.IDocassignrecService;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.system.dao.DocassignrecMapper;
import cn.stylefeng.guns.modular.system.model.Docassignrec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公文运转记录 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
public class DocassignrecServiceImpl extends ServiceImpl<DocassignrecMapper, Docassignrec> implements IDocassignrecService {
    @Autowired
    private DocassignrecMapper docassignrecMapper;
    @Override
    public ResponseData selectListByDto(SreachMeetingRecDto sreachDto) {
        try{
            EntityWrapper<Docassignrec> ew = new EntityWrapper<>();
            ew.setEntity(new Docassignrec());
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
            return ResponseData.success(docassignrecMapper.getInfoByPid(ew));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
}
