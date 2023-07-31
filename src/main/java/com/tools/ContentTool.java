package com.tools;

import com.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Classname: FileContentTool
 * @Date: 2023/5/24 下午 04:54
 * @Author: kalam_au
 * @Description:
 */


class ContentTool {
    private static final Logger log = LogManager.getLogger(ContentTool.class.getName());

    static final String source = "C:\\ideaPorject\\ace\\doc\\vue\\";
    static final String sourceFile = "package.txt";

    public static void main(String[] args) throws Exception {

        log.info("文本处理工具");
        ContentTool contentTool = new ContentTool();
        contentTool.replaceAllContentSymbol(source);
    }

    private void replaceAllContentSymbol(String path) throws IOException {
        ContentTool contentTool = new ContentTool();
        List<String> ls = FileUtil.getFileNames(path);
        for (String fileName : ls) {
            contentTool.replaceContentSymbol(path, fileName);
        }
    }

    private void replaceContentSymbol(String path, String fileName) throws IOException {
        log.info("替换全角标点到半角标点 ...");
        String content = (String) Objects.requireNonNull(FileUtil.read(path + fileName)).get(FileUtil.ORIGINAL);
        String result = content.replaceAll("，", ", ")
                .replaceAll("。", ". ")
                .replaceAll("：", ": ")
                .replaceAll("！", "! ")
                .replaceAll("；", "; ")
                .replaceAll("、", ", ")
                .replaceAll("（", "(")
                .replaceAll("）", ")");
        if (content.equals(result)) {
            log.info("文本内容完全相同 無需要重写!!!");
            return;
        }

        FileUtil.write(path, fileName, result, false);
        log.info("标点替换完成 !!!");
    }

}

