package cn.stylefeng.guns.modular.AppNotice.service.impl;

import cn.stylefeng.guns.modular.AppNotice.service.IAppNoticeService;
import cn.stylefeng.guns.modular.system.dao.AppNoticeMapper;
import cn.stylefeng.guns.modular.system.model.AppNotice;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-11-22
 */
@Service
public class AppNoticeServiceImpl extends ServiceImpl<AppNoticeMapper, AppNotice> implements IAppNoticeService {
    @Override
    public boolean insert(AppNotice entity) {
        return super.insert(entity);
    }
}
