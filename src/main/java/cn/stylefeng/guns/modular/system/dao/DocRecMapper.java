package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.Docs.dto.SreachDocDto;
import cn.stylefeng.guns.modular.system.model.Checkitem;
import cn.stylefeng.guns.modular.system.model.Doc;
import cn.stylefeng.guns.modular.system.model.DocRec;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 公文督查记录 Mapper 接口
 * </p>
 *
 * @author 三千霜
 * @since 2019-04-12
 */
public interface DocRecMapper extends BaseMapper<DocRec> {
    List<HashMap<String,Object>> getInfoByPid(@Param(value = "ew") Wrapper<DocRec> ew, @Param(value = "courses") List<Checkitem> courses);
    List<HashMap<String, Object>> export(@Param(value = "sreachDto") SreachDocDto sreachDto, @Param(value = "courses") List<Checkitem> courses);

    ArrayList<HashMap<String, Object>> getInfoByPidPage(Pagination page, @Param(value = "ew") Wrapper<DocRec> ew, @Param(value = "courses") List<Checkitem> courses);

    List<Doc> getListByPid(EntityWrapper<Doc> ew);
}
