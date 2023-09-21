package com.tools;

import com.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * @Classname: MybatisUtil
 * @Date: 2023/3/8 下午 03:09
 * @Author: kalam_au
 * @Description:
 */


class MybatisTool {
    private static final Logger log = LogManager.getLogger(MybatisTool.class.getName());

    public static void main(String[] args) throws IOException {
        String countLocation = "C:\\ideaPorject\\eORSO_schedulejob\\src";
        count(countLocation);
    }

    private static void count(String MybatisXmlLocation) throws IOException {
        int select = 0;
        int update = 0;
        int insert = 0;
        int delete = 0;

        FileUtil fileUtil = new FileUtil();
        List xmlList = fileUtil.getFilePaths(MybatisXmlLocation);
        for (Object s : xmlList) {
            String xml = s.toString();
            if ("xml".equalsIgnoreCase(fileUtil.getExtension(FileUtil.getFileName(xml)))) {
                List<StringBuilder> result = (List<StringBuilder>) FileUtil.read(xml).get(FileUtil.LIST);
                log.info("Counting ... {}", xml);
                for (StringBuilder stringBuilder : result) {
                    if (stringBuilder.toString().contains("</select>")) {
                        ++select;
                    }
                    if (stringBuilder.toString().contains("</update>")) {
                        ++update;
                    }
                    if (stringBuilder.toString().contains("</insert>")) {
                        ++insert;
                    }
                    if (stringBuilder.toString().contains("</delete>")) {
                        ++delete;
                    }
                }
            }
        }
        log.info("counted select sql: " + select);
        log.info("counted update sql: " + update);
        log.info("counted insert sql: " + insert);
        log.info("counted delete sql: " + delete);
    }

}

