package cn.stylefeng.guns.modular.api.service;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.Asset;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileService {
    @NotNull
    public static ResponseData upload(@RequestPart("files") List<MultipartFile> files, GunsProperties gunsProperties, IAssetService assetService) {
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        List<Integer> imgids=new ArrayList<>();
        List<Integer> fileids=new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(date);
        String path = gunsProperties.getFileUploadPath()+"/../" ;
        if(files.isEmpty()){
            return ResponseData.error("上传文件不能为空");
        }
        Asset asset=null;
        for (MultipartFile file:files
        ) {
            // 获取图片的原文件名
            String fileOldName = file.getOriginalFilename();
            // 获取图片的扩展名
            String extensionName = fileOldName
                    .substring(fileOldName.lastIndexOf(".") + 1);
            // 文件大小
            int size = (int) file.getSize();


            String fileName = time+"/"+ UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(file.getOriginalFilename());

            if(file.isEmpty()){
                return ResponseData.error("上传文件不能为空");
            }else{
                File dest = new File(path + "/" + fileName);
                //判断文件父目录是否存在
                if(!dest.getParentFile().exists()){
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                    //插入资源记录
                    asset=new Asset();
                    asset.setUserId(ShiroKit.getUser().getId());
                    asset.setStatus(1);
                    asset.setFileSize((long) size);
                    asset.setFileKey(fileName);
                    asset.setFilename(fileOldName);
                    asset.setFilePath(fileName);
                    asset.setCreateTime(date);
                    asset.setSuffix(extensionName);
                    assetService.insert(asset);
                    if (suffixList.contains(extensionName.trim().toLowerCase())){
                        imgids.add(asset.getId());
                    }else{
                        fileids.add(asset.getId());
                    }
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return ResponseData.error("上传失败");
                }
            }
        }
        Map<String, List<Integer>> map=new HashMap<>();
        map.put("photos",imgids);
        map.put("files",fileids);
        return ResponseData.success(map);
    }
}
