package cn.stylefeng.guns.modular.DcCompany.service.impl;

import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.system.dao.CompanyMapper;
import cn.stylefeng.guns.modular.system.model.Company;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 责任单位 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public List<Company> selectMoreList(Wrapper ew) {
        return companyMapper.selectMoreList(ew);
    }
}
