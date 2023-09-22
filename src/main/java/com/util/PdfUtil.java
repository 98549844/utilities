package com.util;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * @Classname: PdfUtil
 * @Date: 20/6/2021 11:19 上午
 * @Author: garlam
 * @Description:
 */


public class PdfUtil {

    private static final Logger log = LogManager.getLogger(PdfUtil.class.getName());

    public static void main(String[] args) throws IOException {
      //  String input = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/";
       // String output = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/output/wood.pdf";
        String input = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\";
        String output = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\output\\wood.pdf";
        jpgsMergeToPdf(input, output);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(RandomUtil.getInt(100));
//        }
    }


    /** 支持一张或多张图片转换成pdf
     * @param input
     * @param output
     * @throws IOException
     */
    public static void toPdf(String input, String output) throws IOException {
        if (NullUtil.isNull(input) || NullUtil.isNull(output)) {
            log.error("Input param is null");
            return;
        }

        File f = new File(input);
        if (f.exists()) {
            if (new File(output).isDirectory()) {
                output = output + "pdf_ver_" + RandomUtil.getInt(100) + ".pdf";
            }

            if (f.isDirectory()) {
                jpgsMergeToPdf(input, output);
            } else {
                jpgToPdf(input, output);
            }
        }

    }


    /**
     * 使用pdfbox将jpg转成pdf
     *
     * @param input   jpg输入流
     * @param pdfPath pdf文件存储路径
     * @throws IOException IOException
     */
    private static void jpgToPdf(String input, String pdfPath) throws IOException {

        InputStream jpgStream = new FileInputStream(input);

        PDDocument pdDocument = new PDDocument();
        BufferedImage image = ImageIO.read(jpgStream);
        PDPage pdPage = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
        pdDocument.addPage(pdPage);
        PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
        contentStream.drawImage(pdImageXObject, 0, 0, image.getWidth(), image.getHeight());
        contentStream.close();
        pdDocument.save(pdfPath);
        pdDocument.close();
    }


    /** 文件夹下多张图片转成一个pdf(多页文件)
     * @param imageFolderPath
     * @param pdfPath
     */
    private static void jpgsMergeToPdf(String imageFolderPath, String pdfPath) {
        try {
            // 图片文件夹地址
            // 图片地址
            String imagePath;
            // PDF文件保存地址
            // 输入流
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img;
            // 实例化图片
            Image image;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            // 循环获取图片文件夹内的图片
            for (File f : files) {
                if (f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif") || f.getName().endsWith(".jpeg") || f.getName().endsWith(".tif")) {
                    System.out.println(f.getName());
                    imagePath = imageFolderPath + f.getName();
                    System.out.println(f.getName());
                    // 读取图片流
                    img = ImageIO.read(new FileInputStream(imagePath));
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

