package cn.stylefeng.guns.modular.DcWorkCompany.service;

import cn.stylefeng.guns.modular.system.model.WorkCompany;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 事项单位关联表 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
public interface IWorkCompanyService extends IService<WorkCompany> {

    WorkCompany selectWithManyById(Integer id);

    List<WorkCompany> selectManyList(Integer id);

    boolean insert1(WorkCompany workCompany);
}
