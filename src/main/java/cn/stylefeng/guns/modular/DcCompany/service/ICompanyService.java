package cn.stylefeng.guns.modular.DcCompany.service;

import cn.stylefeng.guns.modular.system.model.Company;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 责任单位 服务类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
public interface ICompanyService extends IService<Company> {

    List<Company> selectMoreList(Wrapper<Company> wrapper);
}
