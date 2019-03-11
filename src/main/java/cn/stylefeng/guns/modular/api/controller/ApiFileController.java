package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.Asset;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.FileUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/file")
@Api(value = "文件管理api集合", tags = "app 通用外层api集合")
public class ApiFileController extends BaseController {

    @Autowired
    private IAssetService assetService;
    @Autowired
    GunsProperties gunsProperties;

    @ApiOperation(value = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "Long"),
    })
    @RequestMapping("/download/{id}")
    public void download(@PathVariable("id") Integer id,HttpServletResponse response){
        Asset asset=assetService.selectById(id);
        String filename=asset.getFilePath();
        String filePath = gunsProperties.getFileUploadPath()+"/../";
        File file = new File(filePath + "/" + filename);
        //判断文件父目录是否存在
        if(file.exists()){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(asset.getFilename(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            //文件输入流
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            //输出流
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @ApiOperation(value = "返回图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "Long"),
    })
    @RequestMapping("/renderPicture/{id}")
    public void renderPicture(@PathVariable("id") Integer id, HttpServletResponse response) {
        String filename=assetService.selectById(id).getFilePath();
        String path = gunsProperties.getFileUploadPath()+"/../"+ filename;
        byte[] bytes = FileUtil.toByteArray(path);
        try {
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData upload(@RequestPart(value="files") List<MultipartFile> files) {
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


            String fileName = time+"/"+UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(file.getOriginalFilename());

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
