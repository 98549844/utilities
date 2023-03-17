package com.ace;

import com.util.SystemUtil;

import java.io.*;


public class Ace {

    public static void main(String[] args) throws IOException, InterruptedException {
        consoleLoading();
    }

    public static void consoleLoading1() throws InterruptedException {
        int totalProgress = 200; // 总进度为20
        String[] symbols = new String[]{"|", "/", "-", "\\"}; // 转圈所用的字符
        for (int i = 0; i < totalProgress; i++) {
            System.out.print("\r" + symbols[i % symbols.length]); // 输出一个字符表示进度
            Thread.sleep(300); // 等待0.1秒
        }
        System.out.println("\n加载完成");
    }


    public static void consoleLoading() throws InterruptedException {
        String a = "\\";
        String b = "|";
        String c = "/";
        String d = "-";

        Console console = System.console();

        while (true) {
            System.out.print(SystemUtil.backToLineStart() + a);
            Thread.sleep(300);
            System.out.print(SystemUtil.backToLineStart() + b);
            Thread.sleep(300);
            System.out.print(SystemUtil.backToLineStart() + c);
            Thread.sleep(300);
            System.out.print(SystemUtil.backToLineStart() + d);
            Thread.sleep(300);
        }
    }
}

