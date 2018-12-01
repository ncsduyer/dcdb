package cn.stylefeng.guns.modular.DcWorkCompany.service.impl;

import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.DcWorkCompany.service.IWorkCompanyService;
import cn.stylefeng.guns.modular.system.dao.WorkCompanyMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.guns.modular.system.model.WorkCompany;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private IAssignWorkService assignWorkService;

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
    public boolean updateByWorkCompany(Map<String, List<WorkCompany>> workCompanyList) {
        if (ToolUtil.isNotEmpty(workCompanyList) && ToolUtil.isNotEmpty(workCompanyList.get("list"))) {
            List<WorkCompany> workCompanys = new ArrayList<>();
            List<AssignWork> assignWorks = new ArrayList<>();

            for (WorkCompany workCompany : workCompanyList.get("list")) {
                WorkCompany workCompany1 = selectById(workCompany.getId());
//        if (!(ShiroKit.getUser().getId().equals(assignWorkService.selectById(workCompany1.getaWId()).getAgent()))){
//            return false;
//        }
                if (ToolUtil.isNotEmpty(workCompany1) && workCompany1.getStatus() != 1) {
                    if (workCompany1.getIsUpdate() != 1) {
                        workCompany1.setIsUpdate(1);
                        workCompany1.setDeadline(workCompany.getDeadline());
                        workCompany1.setRequirement(workCompany.getRequirement());
                        workCompanys.add(workCompany1);
                        AssignWork assignWork = new AssignWork();
                        assignWork.setId(workCompany1.getaWId());
                        assignWork.setStatus(2);
                        assignWorks.add(assignWork);
                    } else {
                        WorkCompany workCompany2 = new WorkCompany();
                        workCompany2.setId(workCompany.getId());
                        workCompany2.setSituation(workCompany.getSituation());
                        workCompany2.setRequirement(workCompany.getRequirement());
                        workCompany2.setStatus(workCompany.getStatus());
                        workCompanys.add(workCompany2);
                        AssignWork assignWork = new AssignWork();
                        assignWork.setId(workCompany1.getaWId());
                        assignWork.setStatus(2);
                        assignWorks.add(assignWork);
                    }
                }
            }
            updateBatchById(workCompanys);
            assignWorkService.updateBatchById(assignWorks);
            return true;

        }
        return false;
    }
}
