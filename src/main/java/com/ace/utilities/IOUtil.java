package com.ace.utilities;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Classname: IOUtil
 * @Date: 2023/10/9 下午 05:26
 * @Author: kalam_au
 * @Description:
 */


public class IOUtil {
    private static final Logger log = LogManager.getLogger(IOUtil.class.getName());

    public static String read(String file) throws IOException {
        return IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
    }

    public static void write(String file, String content) throws IOException {
        IOUtils.write(content, new FileOutputStream(file), StandardCharsets.UTF_8);
    }

    public static void append(String file, String content) throws IOException {
        String c = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
        String result = c + PathUtil.newLine() + content;
        IOUtils.write(content, new FileOutputStream(result), StandardCharsets.UTF_8);
    }

    public static void copy(String source, String target) throws IOException {
        IOUtils.copy(new FileInputStream(source), new FileOutputStream(target));
    }

    public static byte[] toByteArray(String file) throws IOException {
        return IOUtils.toByteArray(new FileInputStream(file));
    }

}

