package com.mhc.gwsti.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2018/9/12.
 */
@Slf4j
public class ImageUtils {


    private BufferedImage uploadSingleImage(String url) {
        BufferedImage image = null;
        try {
            image = Thumbnails.of(new URL(url))
                    .outputQuality(1f)
                    .width(1000)
                    .asBufferedImage();
        } catch (Exception e) {
            log.error("ImageUtils.uploadSingleImage下载图片异常，当前下载图片url：{}，异常原因：{}", url, e.getMessage(), e);
        }
        return image;
    }


    public byte[] merge(List<String> srcs, int uploadRetryCount) throws Exception {

        List<BufferedImage> images = new ArrayList<>();
        Integer h = 0;
        Integer w = 0;

        //下载图片
        String currentUrl = null;
        try {
            for (String src : srcs) {
                currentUrl = src;

                BufferedImage image = uploadSingleImage(src);

                int retryCounter = 0;
                while (null == image) {
                    Thread.sleep(100);

                    image = uploadSingleImage(src);
                    if (image != null) {
                        break;
                    }

                    //最多重试三次
                    retryCounter++;
                    if (image == null && retryCounter > uploadRetryCount) {
                        log.error("ImageUtils下载图片重试拉取失败，当前下载图片url：{}", currentUrl);
                        throw new RuntimeException(src + "重试失败");
                    }
                }


                images.add(image);

                w = image.getWidth() < w ? w : image.getWidth();
                h += image.getHeight();
            }
        } catch (Exception e) {
            log.error("ImageUtils下载图片异常，当前下载图片url：{}，异常原因：{}", currentUrl, e.getMessage(), e);
            throw e;
        }

        //缓解磁盘压力
        Thread.sleep(500);

        try {
            //压缩图片
            BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Integer startHeight = 0;
            for (BufferedImage image : images) {

                int[] rgb = new int[w * image.getHeight()];

                rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), rgb, 0, image.getWidth());

                imageNew.setRGB(0, startHeight, w, image.getHeight(), rgb, 0, w);

                startHeight += image.getHeight();
            }


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(imageNew, "jpeg", stream);// 写图片 ，输出到硬盘

            return stream.toByteArray();
        } catch (Exception e) {
            log.error("ImageUtils压缩图片异常，异常原因：{}", e.getMessage(), e);
            throw e;
        }
    }

}
