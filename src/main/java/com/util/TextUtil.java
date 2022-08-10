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


    public static void main(String[] args) throws IOException, TesseractException {

        String imagePath="C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\images\\sample.png";
        //加载待读取图片
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.out.println("找不到图片");
            return ;
        }
        BufferedImage textImage = ImageIO.read(imageFile);
        //创建tess对象
        ITesseract tesseracts = new Tesseract();
        //设置训练文件目录
        tesseracts.setDatapath("src/main/resources/traineddata/");
        //设置训练语言
        tesseracts.setLanguage("chi_sim");
        //执行转换
        String result = tesseracts.doOCR(imageFile);

        System.out.println(result);
    }

}

