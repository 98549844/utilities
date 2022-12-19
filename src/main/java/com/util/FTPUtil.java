package com.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Classname: FTPUtil
 * @Date: 2022/12/19 上午 10:51
 * @Author: kalam_au
 * @Description:
 */


public class FTPUtil {
    private static final Logger log = LogManager.getLogger(FTPUtil.class.getName());


    @Value("${cw.ftp.host}")
    private String host = "10.128.1.1";
    @Value("${cw.ftp.username}")
    private String username = "abc";
    @Value("${cw.ftp.password}")
    private String password = "123456";
    @Value("${cw.ftp.port}")
    private int port = 21;
    @Value("${cw.ftp.path}")
    private String path = "./";

    public String getPath() {
        return path;
    }

    /**
     * 示例
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        log.error("只是get下来, 未做测试");

        final FTPUtil ftpUtils = new FTPUtil();

        FTPClient ftpClient = ftpUtils.getFTPConnection();
        System.out.println(ftpClient.getDataConnectionMode());
        System.out.println(ftpClient.listFiles("zip")[0].getName());

        final List<String> zip = ftpUtils.getFileNameList(ftpClient, "zip");
        System.out.println(zip);

        ftpUtils.close(ftpClient);

    }

    /**
     * 登陆FTP并获取FTPClient对象
     *
     * @return
     */
    public FTPClient getFTPConnection() {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(1000 * 30);//设置连接超时时间
            ftpClient.connect(host, Integer.valueOf(port));// 连接FTP服务器
            ftpClient.login(username, password);// 登陆FTP服务器
            ftpClient.setControlEncoding("UTF-8");// 中文支持
            // 设置文件类型为二进制（如果从FTP下载或上传的文件是压缩文件的时候，不进行该设置可能会导致获取的压缩文件解压失败）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //默认模式0
            //模式2，查询文件失败
            //ftpClient.enterLocalPassiveMode();//开启被动模式，否则文件上传不成功，也不报错
            //模式0，可以查询到文件
            //ftpClient.enterLocalActiveMode();
            //模式3，查询文件失败
            //ftpClient.enterRemotePassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("连接FTP失败，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功!");
            }
        } catch (Exception e) {
            log.info("登陆FTP失败，请检查FTP相关配置信息是否正确！" + e);
            return null;
        }
        return ftpClient;
    }

    /**
     * 关闭FTP连接
     *
     * @param ftpClient
     */
    public void close(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 按前后缀查询文件
     *
     * @param facepic
     * @param prefix
     * @param suffix
     * @return
     */
    public List<String> getPatternFiles(String facepic, String prefix, String suffix) {
        List<String> ret = new ArrayList();
        String fileName = null;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPConnection();
            boolean changeFlag = ftpClient.changeWorkingDirectory(facepic);
            if (!changeFlag) {
                throw new IOException("进入Ftp目录" + facepic + "失败");
            }
            FTPFile[] files = ftpClient.listFiles(getPath());
            for (FTPFile ftpFile : files) {
                fileName = ftpFile.getName();
                if ((!".".equals(fileName)) && (!"..".equals(fileName))) {
                    String regEx = null;
                    if (StringUtils.isNotBlank(prefix)) {
                        regEx = prefix + "*." + suffix;
                    } else {
                        regEx = suffix + "$";
                    }
                    Pattern pattern = Pattern.compile(regEx);
                    if (pattern.matcher(fileName).find()) {
                        ret.add(fileName);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取文件失败", e);
        } /*finally {
            if (ftpClient != null) {
                close(ftpClient);
            }
        }*/
        return ret;
    }


    /**
     * 从FTP下载文件到本地
     */
    public String download(FTPClient ftpClient, String remotePath, String fileName, String downloadPath) {
        log.info("File from FTP {}", remotePath);
        log.info("File to local {}", downloadPath);
        InputStream is = null;
        FileOutputStream fos = null;
        final File targetFile = new File(downloadPath + File.separator + fileName);
        try {
            is = ftpClient.retrieveFileStream(remotePath + "/" + fileName);// 获取ftp上的文件

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(new File(downloadPath + File.separator + fileName));
            // 文件读取方式一
            int i;
            byte[] bytes = new byte[1024];
            while ((i = is.read(bytes)) != -1) {
                fos.write(bytes, 0, i);
            }
            // 文件读取方式二
            //ftpClient.retrieveFile(fileName, new FileOutputStream(new File(downloadPath)));
            ftpClient.completePendingCommand();
            log.info("FTP文件下载成功！");
        } catch (Exception e) {
            log.error("FTP文件下载失败！", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("下载流关闭失败", e);
            }
        }
        return targetFile.getAbsolutePath();
    }


    /**
     * 上传文件
     *
     * @param serviceDec     ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originFileName 待上传文件的名称（绝对地址） *
     * @return
     */
    public boolean upload(FTPClient ftpClient, String serviceDec, String fileName, String originFileName) {
        log.info("开始上传文件");
        try (InputStream input = new FileInputStream(originFileName)) {
            return uploadFile(ftpClient, serviceDec, fileName, input);
        } catch (IOException e) {
            log.error("文件上传失败" + e);
        }
        return false;
    }

    /**
     * 上传文件
     *
     * @param remoteDir  ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    private boolean uploadFile(FTPClient ftpClient, String remoteDir, String fileName, InputStream inputStream) {
        try {
            log.info("开始上传文件");
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            makeDirectories(ftpClient, remoteDir);
            ftpClient.makeDirectory(remoteDir);
            ftpClient.changeWorkingDirectory(remoteDir);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            log.info("上传文件成功");
        } catch (Exception e) {
            log.error("上传文件失败" + e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("上传文件失败" + e);
                return false;
            }
        }
        return true;
    }


    //改变目录路径
    private boolean changeWorkingDirectory(FTPClient ftpClient, String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                log.info("进入文件夹" + directory + " 成功！");

            } else {
                log.info("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    private boolean makeDirectories(FTPClient ftpClient, String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(ftpClient, new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            do {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!exist(ftpClient, path)) {
                    if (makeDirectory(ftpClient, subDirectory)) {
                        changeWorkingDirectory(ftpClient, subDirectory);
                    } else {
                        log.info("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(ftpClient, subDirectory);
                    }
                } else {
                    changeWorkingDirectory(ftpClient, subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
            } while (end > start);
        }
        return success;
    }

    //判断ftp服务器文件是否存在
    private boolean exist(FTPClient ftpClient, String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
    private boolean makeDirectory(FTPClient ftpClient, String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建文件夹" + dir + " 成功！");
            } else {
                log.info("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取FTP某一特定目录下的所有文件名称
     *
     * @param ftpClient  已经登陆成功的FTPClient
     * @param ftpDirPath FTP上的目标文件路径
     */
    public List<String> getFileNameList(FTPClient ftpClient, String ftpDirPath) {
        List<String> list = new ArrayList<>();
        try {
            // 通过提供的文件路径获取FTPFile对象列表
            FTPFile[] files = ftpClient.listFiles(ftpDirPath);
            // 遍历文件列表，打印出文件名称
            for (int i = 0; i < files.length; i++) {
                FTPFile ftpFile = files[i];
                // 此处只打印文件，未遍历子目录（如果需要遍历，加上递归逻辑即可）
                if (ftpFile.isFile()) {
                //log.info(ftpDirPath + ftpFile.getName());
                    list.add(ftpFile.getName());
                }
            }
        } catch (IOException e) {
            log.error("错误" + e);
        }
        return list;
    }

    /**
     * 获取到服务器文件夹里面最新创建的文件名称
     *
     * @param ftpDirPath 文件路径
     * @param ftpClient  ftp的连接
     * @return fileName
     */
    public String getNewFile(FTPClient ftpClient, String ftpDirPath) throws Exception {

        // 通过提供的文件路径获取FTPFile对象列表
        FTPFile[] files = ftpClient.listFiles(ftpDirPath);
        if (files == null) {
            throw new Exception("文件数组为空");
        }
        Arrays.sort(files, new Comparator<>() {
            @Override
            public int compare(FTPFile f1, FTPFile f2) {
                return f1.getTimestamp().compareTo(f2.getTimestamp());
            }

            public boolean equals(Object obj) {
                return true;
            }
        });
        return ftpDirPath + "/" + files[files.length - 1].getName();
    }
}

