package com.util;

import com.util.entity.SmbFileInfo;
import jcifs.smb.SmbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.springframework.util.FileCopyUtils;
import jcifs.smb.NtlmPasswordAuthentication;

import java.net.MalformedURLException;
import java.io.*;

/**
 * @Classname: SmbUtil
 * @Date: 2023/2/1 上午 09:55
 * @Author: kalam_au
 * @Description:
 */

//https://blog.csdn.net/qq_41429510/article/details/105038274
public class SmbUtil {
    private static final Logger log = LogManager.getLogger(SmbUtil.class.getName());

    //无需密码的共享,格式类似：smb://ip/shareFolder/filename(例如：smb://192.168.1.106/test/test.txt)
    //需要用户名和密码,格式类似：smb://username:password@ip/shareFolder/filename(例如：smb://admin:damin@192.168.1.106/test/test.txt)
    //需要用户名密码和域名,格式类似：smb:host;username:password@ip/shareFolder/filename(例如:smb://orcl;admin:admin@192.168.1.106/test/test.txt)

    private static final String SMB_SHARE_FOLDER = "smb://administrator:Kx100@2208@192.168.8.100/ace/";
    // private static final String SMB_SHARE_FOLDER = "smb://kalam_au:R0656540@192.168.2.75/tmp/";
    private static final String SHARE_FOLDER_PATH = "xxx\\yyy";
    private static final String FILE_NAME = "aaa.txt";
    private static final String LOCAL_DIR = "C:\\tamp";

    public static void main(String[] args) {
        System.out.println(checkSmbFile(SMB_SHARE_FOLDER, SHARE_FOLDER_PATH, FILE_NAME));
        getSmbFolderList(SMB_SHARE_FOLDER);
        // downloadSmbFile(SMB_SHARE_FOLDER, SHARE_FOLDER_PATH, FILE_NAME, LOCAL_DIR);
    }


    /**
     * 读取共享文件夹下的所有文件(文件夹)的名称
     *
     * @param smbUrl
     */
    public static void getSmbFolderList(String smbUrl) {
        SmbFile smbFile;
        try {
            // smb://userName:passWord@host/path/
            smbFile = new SmbFile(smbUrl);
            if (!smbFile.exists()) {
                System.out.println("no such folder");
            } else {
                SmbFile[] files = smbFile.listFiles();
                for (SmbFile f : files) {
                    System.out.println(f.getName());
                }
            }
        } catch (MalformedURLException | SmbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从SMB共享文件夹下载文件到本地
     *
     * @param smbUrl      smb请求的url
     * @param shareFolder 共享文件夹中目标文件存放的完整路径
     * @param fileName    要下载/上传的完整文件名
     * @param localDir    要上传/下载的完整文件夹路径
     */
    public static void downloadSmbFile(String smbUrl, String shareFolder, String fileName, String localDir) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String source = smbUrl + shareFolder + File.separator + fileName;
            SmbFile smbfile = new SmbFile(source);
            File localFile = new File(localDir + File.separator + fileName);
            //文件上传到SMB共享文件目录与该写法类似;即使用SmbFileOutputStream(smbFile);
            in = new BufferedInputStream(new SmbFileInputStream(smbfile));
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            FileCopyUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(in, out);
        }
    }

    //关闭文件流
    private static void closeStream(InputStream in, OutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测SMB共享文件夹中的文件是否存在
     *
     * @param smbUrl      smb请求的url
     * @param shareFolder 共享文件夹中目标文件存放的完整路径
     * @param fileName    要检测文件的完整文件名
     */
    public static boolean checkSmbFile(String smbUrl, String shareFolder, String fileName) {
        boolean result = false;
        try {
            SmbFile smbfile = new SmbFile(smbUrl + shareFolder + File.separator + fileName);
            result = smbfile.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static SmbFile newSmbFile(SmbFileInfo smbFileInfo) throws MalformedURLException {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(smbFileInfo.getIp(), smbFileInfo.getUsername(), smbFileInfo.getPassword());
        String smbUrl = String.format("smb://%s/%s", smbFileInfo.getIp(), smbFileInfo.getFilepath());
        return new SmbFile(smbUrl, auth);
    }

}

