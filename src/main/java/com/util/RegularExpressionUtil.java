package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname: RegularExpressionUtil
 * @Date: 2023/6/9 上午 11:19
 * @Author: kalam_au
 * @Description:
 */


public class RegularExpressionUtil {
    private static final Logger log = LogManager.getLogger(RegularExpressionUtil.class.getName());


    public static boolean validate(String regularExpressionPattern, String context) {
        Pattern regex = Pattern.compile(regularExpressionPattern);
        Matcher matcher = regex.matcher(context);
        String validStatus = matcher.matches() ? "valid" : "invalid";
        log.info("{} is {}", context, validStatus);
        return matcher.matches();
    }

    public static String extract(String regularExpressionPattern, String context) {
        Pattern regex = Pattern.compile(regularExpressionPattern);
        Matcher matcher = regex.matcher(context);

        if (matcher.matches()) {
            //get first one
            return matcher.group(1);
        }
        return "false";
    }


    public static void main(String[] args) {
        // String regularExpressionPattern = "^0x([0-9a-fA-F]{40}|[0-9a-fA-F]{38}@1)$";
        String regularExpressionPattern = "^(0x[0-9a-fA-F]{40})(@1)?$";
        //    String regularExpressionPattern1 = "^0x([0-9a-fA-F]{40}|[0-9a-fA-F]{38}@1)$";
        String a = "0x6e52C694a165066DA8a85f80cbCb9cF54c40aef9@137"; //QR code原文
        String b = "0x6e52C694a165066DA8a85f80cbCb9cF54c40aef9"; // 这是正确的,   this is the most classic one
        String d = "0x6e52C694a165066DA8a85f80cbCb9cF54c40ae@1"; // wrong , 38 digits only
        String c = "0x6e52C694a165066DA8a85f80cbCb9cF54c40aef9@1"; // 这也是正确 , 但要把@1这个截走
//        String e = "0x6e52C694a165066DA8a85f80cbCb9cF54c40aef9@1";

        System.out.println(a.length() + " " + extract(regularExpressionPattern, a));
        System.out.println(b.length() + " " + extract(regularExpressionPattern, b));
        System.out.println(d.length() + " " + extract(regularExpressionPattern, d));
        System.out.println(c.length() + " " + extract(regularExpressionPattern, c));
//        System.out.println(e.length() + " " + extract(regularExpressionPattern, e));


    }

    public static void main11(String[] args) {
        String str = "这是一段示例文本，其中包含了一些数字：12345 和 67890。";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String result = matcher.group();
            System.out.println(result);
        }
    }

    public static void main111(String[] args) {
        String a = "@${*||名称:NAME||input}";
        String b = "@${*||备注:REMARK||input}";

        //对整段文本中固定格式间（此处为‘@${’ 与 ‘}’ 之间）的内容进行提取，则可以用如下正则
        String regex = "(?<=\\@\\$\\{)(.+?)(?=\\})";

        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(a);
        int i = 1;
        while (m.find()) {
            System.out.println(m.group(i));
            i++;
        }
    }
}

