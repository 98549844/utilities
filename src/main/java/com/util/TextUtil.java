package com.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Classname: TextUtil
 * @Date: 2022/8/10 上午 12:22
 * @Author: kalam_au
 * @Description:
 */


public class TextUtil {
    private static final Logger log = LogManager.getLogger(TextUtil.class.getName());


    /**
     * 读取图片的文字拼输出
     *
     * @param imagePath
     * @return
     * @throws IOException
     * @throws TesseractException
     */
    public static String getTextImage(String imagePath) throws IOException, TesseractException {

        log.info("path pattern: src/main/resources/xxx/xxx.png");
        //加载待读取图片
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.out.println("找不到图片");
            return "找不到图片";
        }
        // BufferedImage textImage = ImageIO.read(imageFile);
        //创建tess对象
        ITesseract tesseracts = new Tesseract();
        //设置训练文件目录
        tesseracts.setDatapath("src/main/resources/traineddata/");
        //设置训练语言
        tesseracts.setLanguage("chi_sim");
        log.info("Image reading ...");
        //执行转换
        String result = tesseracts.doOCR(imageFile);
        result = StringUtil.trimAll(result);
        System.out.println(result);
        log.info("Complete ! ");
        return result;
    }


    public static void main(String[] args) throws TesseractException, IOException {
        getTextImage("src/main/resources/file/images/img.png");
    }
}

