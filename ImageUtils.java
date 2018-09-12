package com.mhc.gwsti.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2018/9/12.
 */
public class ImageUtils {


    public static byte[] merge(List<String> srcs) throws IOException {


        List<BufferedImage> images = new ArrayList<>();
        Integer h = 0;
        Integer w = 0;

        for (String src : srcs) {

            BufferedImage image = Thumbnails.of(new URL(src))
                    .outputQuality(0.5f)
                    .width(600)
                    .asBufferedImage();

            images.add(image);

            w = image.getWidth() < w ? w : image.getWidth();
            h += image.getHeight();
        }

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
    }
}
