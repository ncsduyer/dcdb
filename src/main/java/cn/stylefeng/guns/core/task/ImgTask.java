package cn.stylefeng.guns.core.task;

import cn.stylefeng.guns.modular.resources.service.IAssetService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImgTask {
    @Autowired
    private IAssetService assetService;
    @Async
    public static void CompressPictures(File img){
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(img);
            Thumbnails.of(originalImage)
                    .scale(0.5f)
                    .outputQuality(0.5f)
                    .outputFormat("jpg")
                    .toFile(img);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
