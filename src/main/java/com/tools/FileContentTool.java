package com.tools;

import com.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * @Classname: FileContentTool
 * @Date: 2023/5/24 下午 04:54
 * @Author: kalam_au
 * @Description:
 */


class FileContentTool {
    private static final Logger log = LogManager.getLogger(FileContentTool.class.getName());

    static final String sourcePath = "C:\\ideaPorject\\ace\\src\\main\\java\\com\\ace\\";
    static final String sourceFile = "AceApplication.java";

    public static void main(String[] args) throws Exception {
        FileContentTool fileContentTool = new FileContentTool();
        fileContentTool.replaceContentSymbol();
    }

    private void replaceContentSymbol() throws IOException {
        log.info("替换全角标点到半角标点 ...");
        String content = (String) Objects.requireNonNull(FileUtil.read(sourcePath + sourceFile)).get(FileUtil.ORIGINAL);
        String result1 = content.replaceAll("，", ", ");
        String result2 = result1.replaceAll("。", ". ");
        String result3 = result2.replaceAll("：", ": ");
        String result4 = result3.replaceAll("！", "! ");
        String result0 = result4.replaceAll("；", "; ");

        FileUtil.write(sourcePath, sourceFile, result0, false);
        log.info("标点替换完成 !!!");
    }

}

