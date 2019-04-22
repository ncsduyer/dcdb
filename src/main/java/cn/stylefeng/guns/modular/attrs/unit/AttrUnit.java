package cn.stylefeng.guns.modular.attrs.unit;

import cn.stylefeng.guns.modular.system.model.InfoAttr;
import cn.stylefeng.guns.modular.system.model.MeetingAttr;
import cn.stylefeng.guns.modular.system.model.MeetingRecAttr;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.service.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttrUnit {
    public static List<MeetingAttr> getMeetingAttrs(Integer pid, List<Integer> ids, int type, List<MeetingAttr> meetingAttrs, List<MeetingAttr> olds, IService iService) {
        meetingAttrs=null;
        meetingAttrs = new ArrayList<>();
        MeetingAttr meetingAttr = null;
        for (Integer id : ids) {
            meetingAttr = new MeetingAttr();
            meetingAttr.setPid(pid);
            meetingAttr.setAssetid(id);
            meetingAttr.setType(type);
            if (ToolUtil.isNotEmpty(olds)) {
                for (MeetingAttr old : olds) {
                    if (old.getAssetid().equals(id) && old.getPid().equals(pid)) {
                        meetingAttr.setId(old.getId());
                        olds.remove(old);
                    }
                }
              if(ToolUtil.isNotEmpty(olds)){
                    for (MeetingAttr old : olds) {
                        if (old.getAssetid().equals(id) && old.getPid().equals(pid)) {
                            meetingAttr.setId(old.getId());
                            olds.remove(old);
                        }
                    }
                    //获取oldsid列表；
                   List<Integer> oldids= olds.stream()
                            .map(MeetingAttr::getId).distinct()
                            .collect(Collectors.toList());
                    iService.deleteBatchIds(olds);

                }
            }
            meetingAttrs.add(meetingAttr);
        }
        return meetingAttrs;
    }
    public static List<MeetingRecAttr> getMeetingRecAttrs(Integer pid, List<Integer> ids, int type, List<MeetingRecAttr> meetingAttrs, List<MeetingRecAttr> olds,IService iService) {
        meetingAttrs=null;
        meetingAttrs = new ArrayList<>();
        MeetingRecAttr meetingAttr = null;
        for (Integer id : ids) {
            meetingAttr = new MeetingRecAttr();
            meetingAttr.setPid(pid);
            meetingAttr.setAssetid(id);
            meetingAttr.setType(type);
            if (ToolUtil.isNotEmpty(olds)){
                for (MeetingRecAttr old:olds){
                    if(old.getAssetid().equals(id)&&old.getPid().equals(pid)){
                        meetingAttr.setId(old.getId());
                        olds.remove(old);
                    }
                }
                //获取oldsid列表；
                List<Integer> oldids= olds.stream()
                        .map(MeetingRecAttr::getId).distinct()
                        .collect(Collectors.toList());
                iService.deleteBatchIds(olds);
            }
            meetingAttrs.add(meetingAttr);
        }
        return meetingAttrs;
    }

    public static List<InfoAttr> getInfoAttrs(Integer pid, List<Integer> ids, int type, List<InfoAttr> meetingAttrs, List<InfoAttr> olds,IService iService) {
        meetingAttrs=null;
        meetingAttrs = new ArrayList<>();
        InfoAttr meetingAttr = null;
        for (Integer id : ids) {
            meetingAttr = new InfoAttr();
            meetingAttr.setPid(pid);
            meetingAttr.setAssetid(id);
            meetingAttr.setType(type);
            if (ToolUtil.isNotEmpty(olds)){
                for (InfoAttr old:olds){
                    if(old.getAssetid().equals(id)&&old.getPid().equals(pid)){
                        meetingAttr.setId(old.getId());
                        olds.remove(old);
                    }
                }
            }
            meetingAttrs.add(meetingAttr);
        }
        return meetingAttrs;
    }
}
