package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Console {

    private static final Logger log = LogManager.getLogger(Console.class.getName());

    public static final String LINE = "----------------------------------------";
    public static final int WHITE = 30;             // 白色
    public static final int WHITE_BACKGROUND = 40;  // 白色背景
    public static final int RED = 31;               // 红色
    public static final int RED_BACKGROUND = 41;    // 红色背景
    public static final int GREEN = 32;             // 绿色
    public static final int GREEN_BACKGROUND = 42;  // 绿色背景
    public static final int YELLOW = 33;            // 黄色
    public static final int YELLOW_BACKGROUND = 43; // 黄色背景
    public static final int BLUE = 34;              // 蓝色
    public static final int BLUE_BACKGROUND = 44;   // 蓝色背景
    public static final int MAGENTA = 35;           // 品红（洋红）
    public static final int MAGENTA_BACKGROUND = 45;// 品红背景
    public static final int CYAN = 36;              // 蓝绿
    public static final int CYAN_BACKGROUND = 46;   // 蓝绿背景
    public static final int BLACK = 37;             // 黑色
    public static final int BLACK_BACKGROUND = 47;  // 黑色背景

    public static final int FLUORESCENT_ORANGE = 91; // 萤光橙
    public static final int FLUORESCENT_GREEN = 92;  // 萤光绿
    public static final int FLUORESCENT_YELLOW = 94; // 萤光黄
    public static final int FLUORESCENT_PURPLE = 95; // 萤光紫
    public static final int FLUORESCENT_BLUE = 96;   // 萤光蓝

    public static final int BOLD = 1;       // 粗体
    public static final int ITATIC = 3;     // 斜体
    public static final int UNDERLINE = 4;  // 下划线
    public static final int REVERSE = 7;    // 反转

    public static void main(String[] args) {
        printConsoleColor();
    }

    /**
     * 打印Console文字颜色
     */
    public static void printConsoleColor() {
        Console.println("default");
        Console.println("黑色 Console.BLACK", Console.BLACK);
        Console.println("白色 Console.WHITE", Console.WHITE);
        Console.println("红色 Console.RED", Console.RED);
        Console.println("绿色 Console.GREEN", Console.GREEN);
        Console.println("黄色 Console.YELLOW", Console.YELLOW);
        Console.println("蓝色 Console.BLUE", Console.BLUE);
        Console.println("品红 Console.MAGENTA", Console.MAGENTA);
        Console.println("蓝绿 Console.CYAN", Console.CYAN);

        Console.println("萤光橙 Console.FLUORESCENT_ORANGE", Console.FLUORESCENT_ORANGE);
        Console.println("萤光绿 Console.FLUORESCENT_GREEN", Console.FLUORESCENT_GREEN);
        Console.println("萤光黄 Console.FLUORESCENT_YELLOW", Console.FLUORESCENT_YELLOW);
        Console.println("萤光紫 Console.FLUORESCENT_PURPLE", Console.FLUORESCENT_PURPLE);
        Console.println("萤光蓝 Console.FLUORESCENT_BLUE", Console.FLUORESCENT_BLUE);


        Console.println("黑底白字 Console.WHITE, Console.BLACK_BACKGROUND", Console.WHITE, Console.BLACK_BACKGROUND);
        Console.println("白底黑字 Console.BLACK, Console.WHITE_BACKGROUND", Console.BLACK, Console.WHITE_BACKGROUND);
        Console.println("蓝底红字 Console.RED, Console.BLUE_BACKGROUND", Console.RED, Console.BLUE_BACKGROUND);
        Console.println("加粗倾斜 Console.BOLD, Console.ITATIC", Console.BOLD, Console.ITATIC);
        Console.println("黄底白字下划线 Console.WHITE, Console.YELLOW_BACKGROUND, Console.UNDERLINE", Console.WHITE, Console.YELLOW_BACKGROUND, Console.UNDERLINE);
        Console.println("红字颜色反转 Console.RED, Console.REVERSE", Console.RED, Console.REVERSE);
        Console.println("红底白字 Console.WHITE, Console.RED_BACKGROUND", Console.WHITE, Console.RED_BACKGROUND);
        Console.println("绿底红字 Console.RED, Console.GREEN_BACKGROUND", Console.RED, Console.GREEN_BACKGROUND);
        Console.println("红底蓝字 Console.BLUE, Console.MAGENTA_BACKGROUND", Console.BLUE, Console.MAGENTA_BACKGROUND);
        Console.println("蓝绿底白字 Console.WHITE, Console.CYAN_BACKGROUND", Console.WHITE, Console.CYAN_BACKGROUND);
    }


    private static String FMT(String txt, int... codes) {
        StringBuffer sb = new StringBuffer();
        for (int code : codes) {
            sb.append(code + ";");
        }
        String _code = sb.toString();
        if (_code.endsWith(";")) {
            _code = _code.substring(0, _code.length() - 1);
        }
        return (char) 27 + "[" + _code + "m" + txt + (char) 27 + "[0m";
    }

    /**
     * 打印不换行
     */
    public static void print(String txt, int... codes) {
        System.out.print(FMT(txt, codes));
    }

    /**
     * 打印并换行
     */
    public static void println(String txt, int... codes) {
        System.out.println(FMT(txt, codes));
    }

    /**
     * 默认打印红色文字
     */
    public static void println(String txt) {
        System.out.println(FMT(txt, RED));
    }

    /** 执行command
     * @param command
     * @return
     * @throws IOException
     */
    public static String execute(String... command) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append(PathUtil.getNewLine());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            String message = e.getMessage();
            log.error(message);
            return message;
        }
        return result.toString();
    }


}
