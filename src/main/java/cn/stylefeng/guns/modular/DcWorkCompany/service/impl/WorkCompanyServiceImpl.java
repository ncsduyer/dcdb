package cn.stylefeng.guns.modular.DcWorkCompany.service.impl;

import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.dao.WorkCompanyMapper;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 事项单位关联表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@Service
public class WorkCompanyServiceImpl extends ServiceImpl<WorkCompanyMapper, WorkCompany> implements IWorkCompanyService {

    @Autowired
    private WorkCompanyMapper workCompanyMapper;

    @Override
    public WorkCompany selectWithManyById(Integer id) {
        return workCompanyMapper.selectWithManyById(id);
    }

    @Override
    public List<WorkCompany> selectManyList(Integer id) {
        return workCompanyMapper.selectManyList(id);
    }

    @Override
    public boolean insert1(WorkCompany workCompany) {
        if (selectCount(Condition.create().eq("a_w_id", workCompany.getaWId()).eq("company_id", workCompany.getCompanyId())) > 0) {
            return false;
        } else {
            return insert(workCompany);
        }
    }
}
