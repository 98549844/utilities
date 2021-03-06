package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Classname: PathUtil
 * @Date: 11/7/2021 3:06 上午
 * @Author: garlam
 * @Description:
 */


public class PathUtil {
    private static final Logger log = LogManager.getLogger(PathUtil.class.getName());



    public File getSrcFile(String srcFile) throws IOException {
        log.info("format: src/main/java/com/...");
        File file = new File(srcFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len = null;
        while ((len = br.readLine()) != null) {
            System.out.println(len);
        }
        return file;
    }

    public File getResourceFile(String resource) throws IOException {
        log.info("read file location: ace/src/main/resources");
        log.info("format: /...");
        File file = new File(PathUtil.class.getResource(resource).getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len = null;
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
        String path = directory.getCanonicalPath();
        return path;
    }


}

