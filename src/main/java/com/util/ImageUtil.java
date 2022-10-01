package com.util;

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
import java.io.*;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;


/**
 * @Classname: PictureUtil
 * @Date: 26/6/2021 5:14 上午
 * @Author: garlam
 * @Description:
 */


public class ImageUtil {
    private static final Logger log = LogManager.getLogger(ImageUtil.class.getName());

    final static String src = "src/main/resources/file/images";
    final static String output = "src/main/resources/file/images/temp";


    /**
     * 压缩图片并复盖原图片for网页显示和缓存用
     */
    public static void compress(String path)  {
        ImageUtil.compressPicForScale(path, path, 1000, 0.8, 1024, 1024);
    }


    /**
     * 根据指定大小和指定精度压缩图片
     *
     * @param srcPath      源图片地址
     * @param desPath      目标图片地址
     * @param desFileSize  指定图片大小，单位kb
     * @param accuracy     精度，递归压缩的比率，建议小于0.9
     * @param desMaxWidth  目标最大宽度
     * @param desMaxHeight 目标最大高度
     * @return 目标文件路径
     */
    public static String compressPicForScale(String srcPath, String desPath, long desFileSize, double accuracy, int desMaxWidth, int desMaxHeight) {
        if (NullUtil.isEmpty(srcPath) || NullUtil.isEmpty(desPath)) {
            log.error("srcPath/desPath is empty !");
            return null;
        }
        if (!new File(srcPath).exists()) {
            log.error("src image not found !");
            return null;
        }
        try {
            File srcFile = new File(srcPath);
            long srcFileSize = srcFile.length();
            System.out.println("源图片：" + srcPath + "，size：" + srcFileSize / 1024 + "kb");
            //获取图片信息
            BufferedImage bim = ImageIO.read(srcFile);
            int srcWidth = bim.getWidth();
            int srcHeight = bim.getHeight();

            //先转换成jpg
            Thumbnails.Builder builder = Thumbnails.of(srcFile).outputFormat("jpg");

            // 指定大小（宽或高超出会才会被缩放）
            if (srcWidth > desMaxWidth || srcHeight > desMaxHeight) {
                builder.size(desMaxWidth, desMaxHeight);
            } else {
                //宽高均小，指定原大小
                builder.size(srcWidth, srcHeight);
            }

            // 写入到内存
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); //字节输出流（写入到内存）
            builder.toOutputStream(baos);

            // 递归压缩，直到目标文件大小小于
            byte[] bytes = compressPicCycle(baos.toByteArray(), desFileSize, accuracy);

            // 输出到文件
            File desFile = new File(desPath);
            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(bytes);
            fos.close();
            baos.close();

            System.out.println("目标图片：" + desPath + "，size" + desFile.length() / 1024 + "kb");
            System.out.println("图片压缩完成！");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return desPath;
    }

    private static byte[] compressPicCycle(byte[] bytes, long desFileSize, double accuracy) throws IOException {
        // File srcFileJPG = new File(desPath);
        long srcFileSizeJPG = bytes.length;
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
        if (srcFileSizeJPG <= desFileSize * 1024) {
            return bytes;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        int srcWidth = bim.getWidth();
        int srcHeight = bim.getHeight();
        int desWidth = new BigDecimal(srcWidth).multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeight).multiply(new BigDecimal(accuracy)).intValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //字节输出流（写入到内存）
        Thumbnails.of(new ByteArrayInputStream(bytes)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
        return compressPicCycle(baos.toByteArray(), desFileSize, accuracy);
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
    public static boolean square(String src) throws IOException {
        log.info("compressed and squared image store in /temp folder !");
        File outFile = getTempFile(src);
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
        Graphics graphics = bufferedImage.getGraphics();//获取图片画笔
        try {
            int backgroundX = 0;//背景x坐标
            int backgroundY = 0;//背景y坐标
            int backgroundWith = square;//背景宽
            int backgroundHeight = square;//背景高
            graphics.setColor(new Color(255, 255, 255));//设置画笔颜色
            graphics.fillRect(backgroundX, backgroundY, backgroundWith, backgroundHeight);//填充背景，默认白色


            Image image = ImageIO.read(new FileInputStream(src));
            graphics.drawImage(image, x, y, width, height, null);

            String type = "jpg";
            return ImageIO.write(bufferedImage, type, outFile);

        } finally {
            graphics.dispose();//释放画笔
            log.info("Success");
        }
    }

    public static void main(String[] args) throws IOException {
        compress("C:\\ACE\\images\\001.png");
    }

    /**
     * 修改原图的文件格式
     * 要求SRC和descPath要全路径, 包括新旧文件名
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
            log.error("File path incorrect");
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
            log.error("File path incorrect");
            return 0;
        }
        BufferedImage imageBuff = ImageIO.read(new FileInputStream(image));
        log.info("Image.getWidth: {} pixel", imageBuff.getWidth());
        return imageBuff.getWidth();
    }

    public static int getHeight(String image) throws IOException {
        if (!FileUtil.isFile(image)) {
            log.error("File path incorrect");
            return 0;
        }
        BufferedImage imageBuff = ImageIO.read(new FileInputStream(image));
        log.info("Image.getHeight: {} pixel", imageBuff.getHeight());
        return imageBuff.getHeight();
    }


    public static void ImageInfo(String image) throws IOException, JpegProcessingException {
        if (!FileUtil.isFile(image)) {
            log.error("File path incorrect");
            return;
        }

        try {
            Metadata metadata = JpegMetadataReader.readMetadata(new File(image));
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = tags.next();
                    System.out.println(tag);
                }
            }
        } catch (JpegProcessingException | IOException e) {
            e.printStackTrace();
        }
    }

    private static File getTempFile(String src) throws IOException {
        File f = new File(src);
        File t = new File(f.getParentFile() + File.separator + "temp");
        if (!t.exists()) {
            t.mkdirs();
        }
        File file = new File(t.getCanonicalFile() + File.separator + f.getName());
        return file;
    }


}

