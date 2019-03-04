package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.Asset;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
}
