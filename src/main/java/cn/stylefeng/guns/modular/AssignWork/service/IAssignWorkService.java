package cn.stylefeng.guns.modular.AssignWork.service;

import cn.stylefeng.guns.modular.AssignWork.dto.AddWorkDto;
import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;

/**
 * <p>
 * 交办事项 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
public interface IAssignWorkService extends IService<AssignWork> {

    Page<AssignWork> SreachPage(SreachWorkDto sreachWorkDto) throws ParseException;

    Object selectWithManyById(Integer id);

    ResponseData add(AddWorkDto addWorkDto);

    Object selectAsPage1(SreachWorkDto sreachWorkDto) throws ParseException;

    ResponseData update1(AssignWork assignWork);
}
