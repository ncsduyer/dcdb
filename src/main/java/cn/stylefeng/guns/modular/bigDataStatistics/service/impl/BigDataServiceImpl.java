package cn.stylefeng.guns.modular.bigDataStatistics.service.impl;

import cn.stylefeng.guns.modular.DcCompany.service.ICompanyService;
import cn.stylefeng.guns.modular.bigDataStatistics.service.IBigDataServiceImpl;
import cn.stylefeng.guns.modular.system.dao.BigDataMapper;
import cn.stylefeng.guns.modular.system.model.BigData;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-22
 */
@Service
public class BigDataServiceImpl extends ServiceImpl<BigDataMapper, BigData> implements IBigDataServiceImpl{
    @Autowired
    private BigDataMapper bigDataMapper;
    @Autowired
    private ICompanyService companyService;
    @Override
    public List<HashMap<String, Integer>> countUnitStar() {
        return bigDataMapper.countUnitStar();
    }

    @Override
    public List<HashMap<String, Integer>> countAssignStatus() {
        return bigDataMapper.countAssignStatus();
    }
}
