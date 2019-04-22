package cn.stylefeng.guns.modular.Docs.service.impl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocRecService;
import cn.stylefeng.guns.modular.MeetingRec.dto.SreachMeetingRecDto;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.system.dao.DocRecMapper;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.guns.modular.system.model.DocRec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公文督查记录 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
@Service
public class DocRecServiceImpl extends ServiceImpl<DocRecMapper, DocRec> implements IDocRecService {
    @Autowired
    private DocRecMapper docassignrecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Override
    public ResponseData selectListByDto(SreachMeetingRecDto sreachDto) {
        try{
            EntityWrapper<Doc> ew = new EntityWrapper<>();
            ew.setEntity(new Doc());
            if (ToolUtil.isNotEmpty(sreachDto.getPid())){
                ew.eq("mr.docassignid", sreachDto.getPid());
            }

            if (ToolUtil.isNotEmpty(sreachDto.getCheckitemid())){
                ew.in("mr.checkitemid", sreachDto.getCheckitemid());
            }
            if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())){
                ew.in("m.unitid", sreachDto.getCompanyIds());
            }
            ew.orderBy("m.assign_time");
            return ResponseData.success(docassignrecMapper.getListByPid(ew));
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public List<HashMap<String, Object>> export(SreachDocDto sreachDto) {
        try {
            new Bettime(sreachDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return docassignrecMapper.export(sreachDto,checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1)));
    }
}
