package cn.stylefeng.guns.modular.EventType.service.impl;

import cn.stylefeng.guns.modular.EventType.service.IEventTypeService;
import cn.stylefeng.guns.modular.system.dao.EventTypeMapper;
import cn.stylefeng.guns.modular.system.model.EventType;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事件类型 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2018-10-15
 */
@Service
public class EventTypeServiceImpl extends ServiceImpl<EventTypeMapper, EventType> implements IEventTypeService {

}
