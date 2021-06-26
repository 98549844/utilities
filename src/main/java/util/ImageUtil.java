package util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.HexConverter;
//import org.apache.logging.log4j.core.config.plugins.convert.HexConverter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


/**
 * @Classname: PictureUtil
 * @Date: 26/6/2021 5:14 上午
 * @Author: garlam
 * @Description:
 */


public class ImageUtil {
    private static Logger log = LogManager.getLogger(ImageUtil.class.getName());

    public static void main(String[] args) throws IOException, JpegProcessingException {
        String destPath = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/temp/";

        String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/wood.jpg";
        // String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/img.png";
        // String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/WechatIMG127.jpeg";
        // getWidth(s);
        // getHeight(s);
        //  ImageInfo(srcPic);
        // getImageType(s);
       // modifyImageFormat(s, destPath, "bmp");


    }

    public static BufferedImage getSque(String image) {
        BufferedImage bi = null;
        int init_width = bi.getWidth();
        int init_height = bi.getHeight();
        if (init_width != init_height){
            int width_height = 0;
            int x = 0;
            int y = 0;
            if (init_width > init_height) {
                width_height = init_height;//原图是宽大于高的长方形
                x = (init_width-init_height)/2;
                y = 0;
            } else if (init_width < init_height) {
                width_height = init_width;//原图是高大于宽的长方形
                y = (init_height-init_width)/2;
                x = 0;
            }
            bi = bi.getSubimage(x, y, width_height, width_height);
        }
        return bi;
    }

    public static boolean createImage(BufferedImage bufferedImage, String type, File file) throws IOException {
        Graphics g = bufferedImage.getGraphics();//获取图片画笔
        try {
            int backgroundX = 10;//背景x坐标
            int backgroundY = 40;//背景y坐标
            int backgroundWith = 180;//背景宽
            int backgroundHeight = 120;//背景高
            g.fillRect(backgroundX, backgroundY, backgroundWith, backgroundHeight);//填充背景，默认白色

            g.setColor(new Color(120, 120, 120));//设置画笔颜色

            int fontSize = 28;//字体大小
            g.setFont(new Font("宋体", Font.BOLD, fontSize));//设置字体

            int stringX = 10;//文字x坐标
            int stringY = 100;//文字y坐标
            g.drawString("绘制简单图片", stringX, stringY);
            return ImageIO.write(bufferedImage, type, file);
        } finally {
            g.dispose();//释放画笔
        }
    }



    //////////////////////////////////////////////

    /**
     * 修改原图的文件格式
     *
     * @param srcPath    原图路径
     * @param destPath   新图路径
     * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
     * @return
     */
    public static boolean modifyImageFormat(String srcPath, String destPath, String formatName) {
        if (NullUtil.isNull(srcPath) || NullUtil.isNull(destPath) || NullUtil.isNull(formatName)) {
            log.error("Param is null");
            return false;
        } else if (!FileUtil.isFile(srcPath)) {
            log.error("input param incorrect");
            return false;
        }

        if (!FileUtil.isFile(destPath)) {
            String[] srcFileNameArray = Paths.get(srcPath).getFileName().toString().split(".");
            String srcFileName = srcFileNameArray[0];
            destPath = destPath + "/" + srcFileName + formatName.startsWith(".") == "." ? formatName : "." + formatName;
        }


        boolean isSuccess = false;
        InputStream fis = null;
        try {
            fis = new FileInputStream(srcPath);
            BufferedImage bufferedImg = ImageIO.read(fis);
            isSuccess = ImageIO.write(bufferedImg, formatName, new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isSuccess == false) {
            log.info("isSuccess: {}, Maybe image format not support", false);
        } else {
            System.out.println("isSuccess: " + true);
        }
        return isSuccess;
    }


    /**
     * 获取图片类型
     * JPG图片头信息:FFD8FF
     * PNG图片头信息:89504E47
     * GIF图片头信息:47494638
     * BMP图片头信息:424D
     *
     * @param image 图片文件流
     * @return 图片类型:jpg|png|gif|bmp
     */
    public static String getImageType(String image) throws IOException {
        if (!FileUtil.isFile(image)) {
            log.info("It is not a file");
            return null;
        }
        String type = null;
        // get image format in a file
        File file = new File(image);
        // create an image input stream from the specified file
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        // get all currently registered readers that recognize the image format
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            throw new RuntimeException("No readers found!");
        }
        // get the first reader
        ImageReader reader = iter.next();
        log.info("Format: " + reader.getFormatName());
        type = reader.getFormatName();
        // close stream
        iis.close();
        return type;
    }


    public static int getWidth(String image) throws IOException {
        if (!FileUtil.isFile(image)) {
            log.info("It is not a file");
            return 0;
        }
        BufferedImage imageBuff = ImageIO.read(new FileInputStream(image));
        log.info("Image.getWidth: {} pixel", imageBuff.getWidth());
        return imageBuff.getWidth();
    }

    public static int getHeight(String image) throws IOException {
        if (!FileUtil.isFile(image)) {
            log.info("It is not a file");
            return 0;
        }
        BufferedImage imageBuff = ImageIO.read(new FileInputStream(image));
        log.info("Image.getHeight: {} pixel", imageBuff.getHeight());
        return imageBuff.getHeight();
    }


    public static void ImageInfo(String image) throws IOException, JpegProcessingException {
        if (!FileUtil.isFile(image)) {
            log.info("It is not a file");
            return;
        }

        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(new File(image));
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = tags.next();
                    System.out.println(tag);

                }

            }
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

