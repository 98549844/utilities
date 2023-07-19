package com.ace;

import com.util.StringUtil;
import de.vandermeer.asciitable.AsciiTable;

import java.io.*;
import java.util.Arrays;


public class Ace {

//    public static void main(String[] args) throws IOException {
//
//        Integer[] ints = {21, 1, 23, 5, 3, 29, 6, 24, 8, 0, 20, 9, 30, 25, 7, 26, 4, 27, 2, 28, 22};
//        String[] strings0 = {"7", "2", "22", "30", "3", "26", "5", "28", "8", "24", "9", "1", "0", "21", "23", "6", "25", "4", "27", "29", "20"};
//        String[] strings1 = {"7", "2", "22", "30", "3", "26", "5", "28", "8", "24", "9", "1", "0", "21", "23", "6", "25", "4", "27", "29", "20"};
//
//
//        Arrays.sort(strings0);
//        System.out.println(Arrays.toString(strings0));
//
//        StringUtil.sort(strings1, true, 0, strings1.length - 1);
//        System.out.println(Arrays.toString(strings1));
//        StringUtil.sort(strings1, false, 0, strings1.length - 1);
//        System.out.println(Arrays.toString(strings1));
//
//    }

    public static void main(String[] args) {
        String[] header = {"Name", "Age", "Gender"};
        String[][] data = {
                {"John", "25", "Male"},
                {"Jane", "30", "Female"},
                {"Bob", "40", "Male"}
        };
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("row 1 col 1", "row 1 col 2");
        at.addRule();
        at.addRow("row 2 col 1", "row 2 col 2");
        at.addRule();
        String rend = at.render();
        System.out.println(rend);


    }


}


