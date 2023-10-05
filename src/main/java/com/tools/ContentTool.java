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

    static final String source = "C:\\ideaPorject\\ace\\doc\\javascript\\";
    static final String sourceFile = "springboot-admin-page.sql";

    public static void main(String[] args) throws Exception {

        log.info("文本处理工具");
        ContentTool contentTool = new ContentTool();
        contentTool.replaceContentSymbol(source);
      //  contentTool.replaceContentSymbol(source, sourceFile);
    }

    /**
     * 指定文件夹
     *
     * @param path
     * @throws IOException
     */
    private void replaceContentSymbol(String path) throws IOException {
        ContentTool contentTool = new ContentTool();
        List<String> ls = FileUtil.getFileNames(path);
        for (String fileName : ls) {
            contentTool.replaceContentSymbol(path, fileName);
        }
    }

    /**
     * 指定文件
     *
     * @param path
     * @param fileName
     * @throws IOException
     */
    private void replaceContentSymbol(String path, String fileName) throws IOException {
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
            log.info(fileName + " 文本内容完全相同 無需要重写!");
            return;
        }

        FileUtil.write(path, fileName, result, false);
        log.info(fileName + " 标点替换完成 !");
    }

}

