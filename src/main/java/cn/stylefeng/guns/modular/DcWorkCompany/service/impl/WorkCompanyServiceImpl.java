package cn.stylefeng.guns.modular.DcWorkCompany.service.impl;

import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.dao.WorkCompanyMapper;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import cn.stylefeng.roses.core.util.ToolUtil;
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

    @Override
    public boolean updateByWorkCompany(WorkCompany workCompany) {
        WorkCompany workCompany1 = selectById(workCompany.getId());
        if (ToolUtil.isNotEmpty(workCompany1) && workCompany1.getStatus() != 1) {
            if (workCompany1.getIsUpdate() != 1) {
                workCompany1.setIsUpdate(1);
                workCompany1.setDeadline(workCompany.getDeadline());
                workCompany1.setRequirement(workCompany.getRequirement());
                updateById(workCompany1);
                return true;
            } else {
                WorkCompany workCompany2 = new WorkCompany();
                workCompany2.setId(workCompany.getId());
                workCompany2.setSituation(workCompany.getSituation());
                workCompany2.setStatus(workCompany.getStatus());
                updateById(workCompany2);
                return true;
            }
        }
        return false;
    }
}
