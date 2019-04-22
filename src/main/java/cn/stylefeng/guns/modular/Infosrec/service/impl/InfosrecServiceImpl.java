package cn.stylefeng.guns.modular.Infosrec.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.modular.Infos.dto.SreachInfoDto;
import cn.stylefeng.guns.modular.Infosrec.service.IInfosrecService;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.system.dao.InfosrecMapper;
import cn.stylefeng.guns.modular.system.model.Infosrec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 区委信息督查表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-12-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InfosrecServiceImpl extends ServiceImpl<InfosrecMapper, Infosrec> implements IInfosrecService {
    @Autowired
    private InfosrecMapper infosrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Override
    public ResponseData selectListByDto(SreachMeetingRecDto sreachDto) {
        try{
            EntityWrapper<Infosrec> ew = new EntityWrapper<>();
            ew.setEntity(new Infosrec());
            if (ToolUtil.isNotEmpty(sreachDto.getPid())){
                ew.eq("rec.infosid", sreachDto.getPid());
            }

            if (ToolUtil.isNotEmpty(sreachDto.getCheckitemid())){
                ew.in("rec.checkitemid", sreachDto.getCheckitemid());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("rec.unitid", sreachDto.getCompanyIds());
            }
            ew.orderBy("rec.createtime");
            return ResponseData.success(infosrecMapper.getInfoByPid(ew,checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1))));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }
    @Override
    public List<HashMap<String, Object>> export(SreachInfoDto sreachDto) {
        try {
            new Bettime(sreachDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return infosrecMapper.export(sreachDto,checkitemService.selectList(Condition.create().eq("itemclass", 4).eq("status", 1)));
    }


}
