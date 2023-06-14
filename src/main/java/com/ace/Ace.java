package com.ace;

import com.ctc.wstx.util.DataUtil;
import com.util.DateTimeUtil;
import com.util.FileUtil;
import com.util.MapUtil;
import com.util.SystemUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Ace {

    public static void main(String[] args) throws IOException {
        String p = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\input\\staff1.txt";

        Map<String, Object> m = FileUtil.read(p);
        List<StringBuilder> ls = (List<StringBuilder>) m.get(FileUtil.LIST);

        List<StringBuilder> result = new ArrayList<>();
        for (StringBuilder s : ls) {
            System.out.println(s);
            String[] ss = s.toString().split("/");
            StringBuilder a1 = new StringBuilder();
            a1.append(ss[2]).append("-").append(ss[1]).append("-").append(ss[0]).append("\n");
            result.add(a1);
        }

        FileUtil.write("C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\input\\", "staff1.txt", result, false);
    }


}

