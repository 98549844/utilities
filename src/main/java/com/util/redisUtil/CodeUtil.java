package com.util.redisUtil;

import com.alibaba.excel.util.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: CodeUtil
 * @Date: 23/2/2022 9:25 PM
 * @Author: garlam
 * @Description:
 */


public class CodeUtil {
    private static final Logger log = LogManager.getLogger(CodeUtil.class.getName());

    static String path = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/input/a.txt";
    static String cont = "镓簩缁寸爜浠樻，缁欘?茶姳寮？娣℃颚";

    public static void main(String[] args) throws IOException {

        List ls = getDecodeList();

        for (Object decode : ls) {

            File file = new File(path);
            InputStream input = new FileInputStream(file);
            StringBuffer buffer = new StringBuffer();
            byte[] bytes = new byte[1024];
            for (int n; (n = input.read(bytes)) != -1; ) {
                buffer.append(new String(bytes, 0, n, (String) decode));
            }
            System.out.println(buffer);

        }


    }

    @Deprecated
    public static List getDecodeList() {
        List list = new ArrayList();
        list.add("gbk");
        list.add("utf-8");
        list.add("iso-8859-1");
        list.add("utf-16");
        list.add("big5");
        list.add("big5-hkscs");
        return list;
    }

}


