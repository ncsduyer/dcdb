package cn.stylefeng.guns.modular.Docs.service.impl;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.Bettime;
import cn.stylefeng.guns.core.util.CopyUtils;
import cn.stylefeng.guns.core.util.ExportUtil;
import cn.stylefeng.guns.core.util.TypeCastUtil;
import cn.stylefeng.guns.core.util.vo.ExportColSubVo;
import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import cn.stylefeng.guns.modular.CopyRecordNotice.service.ICopyRecordNoticeService;
import cn.stylefeng.guns.modular.Docs.dto.AddDocDto;
import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.Docs.service.IDocRecService;
import cn.stylefeng.guns.modular.Docs.service.IDocService;
import cn.stylefeng.guns.modular.checkitem.service.ICheckitemService;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.dao.DocMapper;
import cn.stylefeng.guns.modular.system.dao.DocRecMapper;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.CopyRecordNotice;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.guns.modular.system.model.DocRec;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 交办公文表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements IDocService {
    @Autowired
    private DocMapper docsMapper;
    @Autowired
    private DocRecMapper docRecMapper;
    @Autowired
    private ICheckitemService checkitemService;
    @Autowired
    private IDocRecService docRecService;
    @Autowired
    private ICopyRecordNoticeService copyRecordNoticeService;
    @Autowired
    private IAssetService assetService;
    @Override
    public ResponseData SreachPage(SreachDocDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachDocDto();
            }
            Page<HashMap<String,Object>> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            Condition ew = getCondition(sreachDto, "mr.unitid");

            ArrayList<HashMap<String,Object>> arrayList = docRecMapper.getInfoByPidPage(page,ew,checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1)));
            page.setRecords(arrayList);
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }


    @Override
    public ResponseData add(AddDocDto addDto) {
        try{
            addDto.setId(null);
            Doc docs = new Doc();
            BeanUtils.copyProperties(addDto, docs);
            if(ToolUtil.isEmpty(docs.getCreatorid())){
                docs.setCreatorid(ShiroKit.getUser().getId());
            }
            insert(docs);

            DocRec meetingrec= null;
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
                //循环插入交办单位
                for (DocRec map : addDto.getResc()) {
                    meetingrec= new DocRec();
                    BeanUtils.copyProperties(map, meetingrec);
                    meetingrec.setDocassignid(docs.getId());
                    meetingrec.setCheckvalue(1);
                    if (ToolUtil.isEmpty(meetingrec.getCreatetime())) {
                        meetingrec.setCreatetime(new DateTime());
                    }
                    docRecMapper.insert(meetingrec);
                }
            }

            Map<String,String> map=new HashMap<>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
            map.put("title",docs.getTitle());
            map.put("date",sdf.format(docs.getAssignTime()));
            map.put("datetime",sdf.format(Calendar.getInstance().getTime()));
            if (ToolUtil.isNotEmpty(addDto.getCopyRecordNotices())) {
                for (CopyRecordNotice copyRecordNotice : addDto.getCopyRecordNotices()) {
                    map.put("check", copyRecordNotice.getContent());
                    copyRecordNotice.setType(3);
                    copyRecordNotice.setJoinId(docs.getId());
                    copyRecordNotice.setCreatetime(Calendar.getInstance().getTime());
                    copyRecordNotice.setSenderId(docs.getCreatorid());
                    copyRecordNotice.setJsonContent(JSONObject.toJSONString(map));
                    copyRecordNoticeService.insert(copyRecordNotice);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData edit(AddDocDto addDto) {
        if(ToolUtil.isEmpty(addDto.getId())){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
        try{
            Doc meeting = new Doc();
            BeanUtils.copyProperties(addDto, meeting);
            updateById(meeting);
            DocRec meetingrec= null;
            if (ToolUtil.isNotEmpty(addDto.getResc())) {
//                List<DocRec> old=docRecService.selectList(Condition.create().eq("docassignid", meeting.getId()));
//                old.removeAll(addDto.getResc());
//                if(ToolUtil.isNotEmpty(old)){
//                    docRecService.deleteBatchIds(old);
//                }
//                循环修改交办单位
                for (DocRec map : addDto.getResc()) {
                    meetingrec= new DocRec();
                    CopyUtils.copyProperties(map, meetingrec);
                    meetingrec.setCheckvalue(1);
                    docRecService.insertOrUpdate(meetingrec);
                }
            }
            return ResponseData.success();

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public void export(SreachDocDto sreachDocDto, HttpServletResponse response) {
//sheet名
        String sheetName = "区委公文数据分析表";
        List<ExportRowVo> exportRowVos = new ArrayList<>();
        List<HashMap<String,Object>> maps = docRecService.export(sreachDocDto);
        List<ExportRowVo> titles=new ArrayList<>();
        ExportRowVo title=  new ExportRowVo();
        title.setTotal(1);
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"单位")));
        ArrayList<Checkitem> checkitems= (ArrayList<Checkitem>) checkitemService.selectList(Condition.create().eq("itemclass", 3).eq("status", 1).orderBy("id", true));
        for (int i=0;i<checkitems.size();i++) {
            Checkitem ck=checkitems.get(i);
            title.getColVos().add(new ExportColVo(new ExportColSubVo(1,ck.getItemdesc())));
            //获取数据
        }
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"平均处理时长")));
        title.getColVos().add(new ExportColVo(new ExportColSubVo(1,"总数")));
        titles.add(title);

        for (HashMap<String,Object> map : maps) {
            ExportRowVo exportRowVo = new ExportRowVo();
            exportRowVo.setColVos(new ArrayList<>());
            exportRowVo.setTotal(1);
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, (String) map.get("title"))));
            for (int i=0;i<checkitems.size();i++) {
                Checkitem ck=checkitems.get(i);
                exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, TypeCastUtil.toString(TypeCastUtil.toInt(TypeCastUtil.toDouble(map.get(ck.getId().toString())))))));
            }
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, (String) map.get("pjsj"))));
            exportRowVo.getColVos().add(new ExportColVo(new ExportColSubVo(1, TypeCastUtil.toString(TypeCastUtil.toInt(map.get("total"))))));
            exportRowVos.add(exportRowVo);
        }

        ExportUtil.outExport(sreachDocDto, response, titles, sheetName, exportRowVos);
    }

    @Override
    public Boolean deleteMoreById(Integer id) {
        docRecMapper.delete(Condition.create().eq("docassignid", id));
        boolean sucess=deleteById(id);
        return sucess;
    }

    @Override
    public ResponseData selectAsMore(SreachDocDto sreachDto) {
        try {
            if (ToolUtil.isEmpty(sreachDto.getCompanyIds())){
                return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
            }
            if (ToolUtil.isEmpty(sreachDto)) {
                sreachDto = new SreachDocDto();
            }
            Page<Doc> page = new Page<>(sreachDto.getPage(), sreachDto.getLimit());
            Condition ew = getCondition(sreachDto, "m.id,mr.id");

            List<Doc> arrayList = docsMapper.selectAsMore(ew);
            page.setRecords(arrayList);
            return ResponseData.success(page);
        }catch (Exception e){
            return new ErrorResponseData(BizExceptionEnum.REQUEST_INVALIDATE.getCode(), BizExceptionEnum.REQUEST_INVALIDATE.getMessage());
        }
    }

    @Override
    public ResponseData selectWithManyById(Integer id) {
        Doc meeting = docsMapper.selectWithManyById(id);
        meeting.setImgs(assetService.selectList(Condition.create().in("id", meeting.getPictures())));
        meeting.setFileList(assetService.selectList(Condition.create().in("id", meeting.getFiles())));
        return ResponseData.success(meeting);
    }

    @Override
    public ResponseData getReports(SreachDocDto sreachDto) {
        return SreachPage(sreachDto);
    }

    @Override
    public ResponseData sreachChart(SreachDocDto sreachDto) {
        return ResponseData.success(null);

    }
    @NotNull
    private Condition getCondition(SreachDocDto sreachDto, String s) throws ParseException {
        new Bettime(sreachDto);
        Condition ew = Condition.create();
        if (ToolUtil.isNotEmpty(sreachDto.getBeforeTime())) {
            ew.ge("m.assign_time", sreachDto.getBeforeTime());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getAfterTime())) {
            ew.le("m.assign_time", sreachDto.getAfterTime());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getId())) {
            ew.eq("m.id", sreachDto.getId());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getCreatorids())) {
            ew.in("m.creatorid", sreachDto.getCreatorids());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getDoctypes())) {
            ew.in("m.doctype", sreachDto.getDoctypes());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getSenderIds())) {
            ew.in("m.sender_id", sreachDto.getSenderIds());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getExceed())) {
            ew.eq("m.exceed", sreachDto.getExceed());
        }
//            拼接查询条件
        if (ToolUtil.isNotEmpty(sreachDto.getTitle())) {
            ew.like("m.title", sreachDto.getTitle());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getStatus())) {
            ew.in("m.status", sreachDto.getStatus());
        }
        if (ToolUtil.isNotEmpty(sreachDto.getCompanyIds())) {
            ew.in("mr.unitid", sreachDto.getCompanyIds());
        }
        ew.groupBy(s);
        if (ToolUtil.isNotEmpty(sreachDto.getOrder())) {
            ew.orderBy(sreachDto.getOrder());
        } else {
            ew.orderBy("m.id", false);
        }
        return ew;
    }
}
