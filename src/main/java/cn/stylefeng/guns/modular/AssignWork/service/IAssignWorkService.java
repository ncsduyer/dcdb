package cn.stylefeng.guns.modular.AssignWork.service;

import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    Page<AssignWork> SreachPage(SreachWorkDto sreachWorkDto) throws ParseException;

    Object selectWithManyById(Integer id);
}
