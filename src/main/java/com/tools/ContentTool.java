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


class ContentTool {
    private static final Logger log = LogManager.getLogger(ContentTool.class.getName());

    static final String sourcePath = "C:\\ideaPorject\\ace\\doc\\regular expression\\";
    static final String sourceFile = "最全常用正则表达式大全.txt";

    public static void main(String[] args) throws Exception {

        log.info("文本处理工具");
        ContentTool contentTool = new ContentTool();
        contentTool.replaceContentSymbol();
    }

    private void replaceContentSymbol() throws IOException {
        log.info("替换全角标点到半角标点 ...");
        String content = (String) Objects.requireNonNull(FileUtil.read(sourcePath + sourceFile)).get(FileUtil.ORIGINAL);
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

        FileUtil.write(sourcePath, sourceFile, result, false);
        log.info("标点替换完成 !!!");
    }

}

