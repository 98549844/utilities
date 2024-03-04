package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: VideoConvertor
 * @Date: 2023/5/5 下午 04:27
 * @Author: kalam_au
 * @Description: 影片视频转换成mp4, 只支持转换成mp4
 */


public class VideoConvertor {
    private static final Logger log = LogManager.getLogger(VideoConvertor.class.getName());

    public static void main(String[] args) throws IOException {
        String inputPath = "/tmp/ccc.MOV";
        String outputPath = "/tmp/";
        String newFileName = "c111";
        startDavToMP4Video(inputPath, outputPath, newFileName);
    }

    public static void startDavToMP4Video(String src, String dest, String newName) throws IOException {
        VideoConvertor videoConvertor = new VideoConvertor();
        videoConvertor.davToMP4Video(src, dest, newName);
    }

    /**
     * @param inputPath   视频的地址
     * @param outputPath  视频转完格式存放地址
     * @param newFileName 新生成的视频名称
     */
    public void davToMP4Video(String inputPath, String outputPath, String newFileName) throws IOException {
        //ffmpeg软件地址
        String ffmpegPath = ""; //在没有配置全局参数, 需要指定ffmpeg路径
        if (!checkFile(inputPath)) {
            log.error(inputPath + " is not file");
            return;
        }
        String video;
        String imgUrl;
        String oldVideoUrl;
        if (StringUtil.isNotBlank(inputPath)) {
            imgUrl = inputPath;
            video = imgUrl.replaceAll("\\\\", "/");
            oldVideoUrl = video.substring(0, video.lastIndexOf("/") + 1);
            video = video.substring(video.lastIndexOf("/") + 1);
        } else {
            log.error("inputPath is null ：" + inputPath);
            return;
        }
        String oldFileName = video;
//		int type = checkContentType(inputPath);
        boolean status;
        log.info("直接转成mp4格式");
        status = processMp4(inputPath, inputPath, ffmpegPath, outputPath, newFileName);// 直接转成mp4格式

        if (status) {
            File folder = new File(oldVideoUrl);
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().equals(oldFileName)) {
                    file.delete();
                }
            }
            log.info("视频转MP4成功!");
        } else {
            log.error("视频转换失败，重试！");
            status = processMp4(inputPath, inputPath, ffmpegPath, outputPath, newFileName);// 直接转成mp4格式
            if (status) {
                File folder = new File(oldVideoUrl);
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.getName().equals(oldFileName)) {
                        file.delete();
                    }
                }
                log.info("视频转MP4成功!");
            }
        }
    }


    private static boolean checkFile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            log.error(path + "不是文件夹！");
            return false;
        }
        return true;
    }

    private boolean processMp4(String inputPath, String oldFilePath, String ffmpegPath, String outputPath, String fileName) throws IOException {
        if (!checkFile(inputPath)) {
            log.error(oldFilePath + " is not file");
            return false;
        }

        List<String> command = new ArrayList<>();
        command.add(ffmpegPath + "ffmpeg");
        command.add("-i");
        command.add(oldFilePath);
        command.add("-c:v");
        command.add("libx264");
        command.add("-mbd");
        command.add("0");
        command.add("-c:a");
        command.add("aac");
        command.add("-strict");
        command.add("-2");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-movflags");
        command.add("faststart");
        command.add(outputPath + fileName + ".mp4");

        try {
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            videoProcess.waitFor();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

