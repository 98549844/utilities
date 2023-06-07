package com.ace;

import com.ctc.wstx.util.DataUtil;
import com.util.DateTimeUtil;
import com.util.FileUtil;
import com.util.MapUtil;
import com.util.SystemUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public class Ace {

    public static void main(String[] args) throws IOException {

        //  Map m = FileUtil.getPathAndFileMap("C:\\ideaPorject\\utilities\\src\\main\\java\\com\\tools\\ContentTool.java");
        //  System.out.println(m.get(FileUtil.PATH));

        FileUtil fileUtil = new FileUtil();
        ArrayList<String> objects = fileUtil.getFilesLocation("C:\\ideaPorject\\utilities");

        for (int i = 0; i < objects.size(); i++) {
            System.out.println(objects.get(i));
        }


    }
}

