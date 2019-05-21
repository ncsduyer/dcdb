package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.modular.api.service.FileService;
import cn.stylefeng.guns.modular.resources.service.IAssetService;
import cn.stylefeng.guns.modular.system.model.Asset;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.FileUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

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
    @RequestMapping(value = "/download/{id}",method = {RequestMethod.GET})
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
    @RequestMapping(value = "/renderPicture/{id}",method = RequestMethod.GET)
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
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @ApiOperation(value = "返回文件资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "文件id数组", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/getFiles/{ids}",method = RequestMethod.GET)
    public ResponseData getFiles(@PathVariable("ids") String ids) {
        return ResponseData.success(assetService.selectList(Condition.create().in("id", ids)));
    }
    /**
     * 上传文件
     */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData upload(@RequestPart(value="files") List<MultipartFile> files) {
        return FileService.upload(files, gunsProperties, assetService);
    }
    /**
     * 下载文件
     */
    @ApiOperation(value = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = {"/downloadfile/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public void downloadfile(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newUrl=request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort() + "/static/admin/"+assetService.selectById(id).getFilePath();
        response.sendRedirect(newUrl);
    }

}
