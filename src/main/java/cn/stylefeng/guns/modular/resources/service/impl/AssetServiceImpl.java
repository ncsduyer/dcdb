package cn.stylefeng.guns.modular.resources.service.impl;

import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.dao.AssetMapper;
import cn.stylefeng.guns.modular.system.model.Asset;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author 三千霜
 * @since 2019-03-01
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements IAssetService {

}
