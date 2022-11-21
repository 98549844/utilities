package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * @Classname: PathUtil
 * @Date: 11/7/2021 3:06 上午
 * @Author: garlam
 * @Description:
 */


public class PathUtil {
    private static final Logger log = LogManager.getLogger(PathUtil.class.getName());


    public File getSrcFile(String srcFile) throws IOException {
        log.info("format: src/main/java/com/.../xx.xx");
        File file = new File(srcFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }

    /**
     * 读取resource文内件容
     *
     * @param resource
     * @return
     * @throws IOException
     */
    public File getResourceContent(String resource) throws IOException {
        log.info("read file location: ace/src/main/resources");
        log.info("format: /../../xxx.xx");
        File file = new File(PathUtil.class.getResource(resource).getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }


    public static String getSystemPath() {
        return System.getProperty("user.dir");
    }

    public static String getSystemPath(String empty) throws IOException {
        if (NullUtil.isNotEmpty(empty)) {
            empty = "";
        }
        File directory = new File(empty);//参数为空
        return directory.getCanonicalPath();
    }

    /** 返回resource下的文件的路径
     * @param resource
     * @return
     */
    public String getResourcePath(String resource) {
        // 直接folder名, 不用resource开头
        log.info("directly access resource/{} ", resource);
        URL url = this.getClass().getResource("/" + resource);
        assert url != null;
        return new File(url.getFile()).getAbsolutePath();
    }


    public static void main(String[] args) throws IOException {
        // System.out.println(getSystemPath());
        // System.out.println(getSystemPath(""));
        PathUtil pathUtil = new PathUtil();
        pathUtil.getResourceContent("/hbm/TestEntity.hbm.xml");
    }


}

