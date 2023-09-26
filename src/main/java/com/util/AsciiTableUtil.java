package com.util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.u8.U8_Grids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: AsciiTableUtil
 * @Date: 2023/7/19 上午 11:05
 * @Author: kalam_au
 * @Description:
 */


public class AsciiTableUtil {
    private static final Logger log = LogManager.getLogger(AsciiTableUtil.class.getName());

    //http://www.vandermeer.de/projects/skb/java/asciitable/
    //打印console table

    public static void println(List<String[]> contents) {
        AsciiTable asciiTable = new AsciiTable();
        //  asciiTable.getContext().setWidth(width);
        for (Object[] row : contents) {
            asciiTable.addRule();
            asciiTable.addRow(row);
        }
        asciiTable.addRule();
        //asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals()); //+-----+-----+
        asciiTable.getContext().setGrid(U8_Grids.borderDouble()); //╔═════╦═════╗
        String result = asciiTable.render();
        Console.println(result, Console.FLUORESCENT_GREEN, Console.BOLD);
    }

    public static void println(List<String[]> contents, String[]... headers) {
        AsciiTable asciiTable = new AsciiTable();
        //  asciiTable.getContext().setWidth(width);
        for (String[] header : headers) {
            asciiTable.addRule();
            asciiTable.addRow((Object[]) header);
        }

        for (Object[] row : contents) {
            asciiTable.addRule();
            asciiTable.addRow(row);
        }
        asciiTable.addRule();
        //asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals()); //+-----+-----+
        asciiTable.getContext().setGrid(U8_Grids.borderDouble()); //╔═════╦═════╗
        String result = asciiTable.render();
        Console.println(result, Console.FLUORESCENT_GREEN, Console.BOLD);
    }

    public static List<String[]> content() {
        //  String[] header = new String[]{"aaaa","bbbb"};

        List<String[]> contents = new ArrayList<>();
        contents.add(new String[]{"row 2 col 1", "row 1 col 2"});
        contents.add(new String[]{"row 2 col 1", "row 2 col 2"});
        return contents;

    }

    public static void main(String[] args) {
        String[] colName = new String[]{"col 1", "col 2"};
        println(content(), colName);
    }


}

