package cn.stylefeng.guns.modular.AssignWork.service.impl;

import cn.stylefeng.guns.modular.AssignWork.dto.SreachWorkDto;
import cn.stylefeng.guns.modular.AssignWork.service.IAssignWorkService;
import cn.stylefeng.guns.modular.system.dao.AssignWorkMapper;
import cn.stylefeng.guns.modular.system.model.AssignWork;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 交办事项 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@Service
public class AssignWorkServiceImpl extends ServiceImpl<AssignWorkMapper, AssignWork> implements IAssignWorkService {
    @Autowired
    private AssignWorkMapper assignWorkMapper;

    @Override
    public Page<AssignWork> SreachPage(SreachWorkDto sreachWorkDto) throws ParseException {
        if (ToolUtil.isEmpty(sreachWorkDto)) {
            sreachWorkDto = new SreachWorkDto();
        }
        Page<AssignWork> page = new Page<>(sreachWorkDto.getPage(), sreachWorkDto.getLimit());
        Date beforeTime = sreachWorkDto.getBeforeTime();
        Date afterTime = sreachWorkDto.getAfterTime();
        if (ToolUtil.isNotEmpty(beforeTime) && ToolUtil.isNotEmpty(afterTime) && afterTime.before(beforeTime)) {
            Date tmp = beforeTime;
            beforeTime = afterTime;
            afterTime = tmp;
        }
        if (ToolUtil.isNotEmpty(afterTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            afterTime = sdf.parse(sdf.format(afterTime));

            afterTime = DateUtils.addSeconds(afterTime, 24 * 60 * 60 - 1);
        }
        return page.setRecords(assignWorkMapper.selectAsPage(page, sreachWorkDto, beforeTime, afterTime));
    }

    @Override
    public AssignWork selectWithManyById(Integer id) {
        return assignWorkMapper.selectWithManyById(id);
    }
}
