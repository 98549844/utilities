package util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        String desc = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/temp/a.jpg";

          String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/wood.jpg";
        // String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/img.png";
        //String s = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/WechatIMG164.jpeg";
          resizeByRatio(s, 0.01f);
    }


    /**
     * 指定大小进行缩放, 以边长值大进行缩放
     *
     * @throws IOException
     */
    public static void resizeByPixel(String src, int p) throws IOException {
        String desc = getTempFile(src).getAbsolutePath();
        Thumbnails.of(src).size(p, p).toFile(desc);
    }

    /**
     * 指定大小进行缩放, 以边长值大进行缩放
     *
     * @throws IOException
     */
    public static void rotation(String src, int rotate) throws IOException {
        String desc = getTempFile(src).getAbsolutePath();
        Thumbnails.of(src).scale(1).rotate(rotate).toFile(desc);
    }

    /**
     * 旋转
     *
     * @throws IOException
     */
    public static void resizeByRatio(String src, float f) throws IOException {
        String desc = getTempFile(src).getAbsolutePath();
        Thumbnails.of(src).scale(f).toFile(desc);
    }


    /**
     * 矩形图片转换成正方形
     *
     * @param src
     * @return
     * @throws IOException
     */
    private static boolean square(String src) throws IOException {
        File file = getTempFile(src);
        int width = ImageUtil.getWidth(src);
        int height = ImageUtil.getHeight(src);

        int square;
        int x = 0;
        int y = 0;
        if (width > height) {
            square = width;
            y = (width - height) / 2;
        } else {
            square = height;
            x = (height - width) / 2;
        }

        BufferedImage bufferedImage = new BufferedImage(square, square, BufferedImage.TYPE_INT_BGR);
        Graphics g = bufferedImage.getGraphics();//获取图片画笔
        try {
            int backgroundX = 0;//背景x坐标
            int backgroundY = 0;//背景y坐标
            int backgroundWith = square;//背景宽
            int backgroundHeight = square;//背景高
            g.setColor(new Color(255, 255, 255));//设置画笔颜色
            g.fillRect(backgroundX, backgroundY, backgroundWith, backgroundHeight);//填充背景，默认白色


            Image image = ImageIO.read(new FileInputStream(src));
            g.drawImage(image, x, y, width, height, null);

            String type = "jpg";
            return ImageIO.write(bufferedImage, type, file);

        } finally {
            g.dispose();//释放画笔
            log.info("Success");
        }
    }


    /**
     * 修改原图的文件格式
     *
     * @param src        原图路径
     * @param destPath   新图路径
     * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
     */
    public static void reformat(String src, String destPath, String formatName) throws IOException {
        Thumbnails.of(src).size(ImageUtil.getWidth(src), ImageUtil.getHeight(src)).outputFormat(formatName).toFile(destPath);
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

    private static File getTempFile(String src) throws IOException {
        File f = new File(src);
        File t = new File(f.getParentFile() + File.separator + "temp");
        if (!t.exists()) {
            t.mkdirs();
        }
        File file = new File(t.getCanonicalFile() + File.separator + "temp_" + f.getName());
        return file;
    }

}

