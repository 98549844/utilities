package com.ace.utilities;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciithemes.a7.A7_Grids;
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
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals()); //+-----+-----+
        // asciiTable.getContext().setGrid(U8_Grids.borderDouble()); //╔═════╦═════╗
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
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals()); //+-----+-----+
        // asciiTable.getContext().setGrid(U8_Grids.borderDouble()); //╔═════╦═════╗

        //asciiTable.setTextAlignment(TextAlignment.RIGHT); //靠右
        //asciiTable.setTextAlignment(TextAlignment.LEFT); //靠左
        //asciiTable.setTextAlignment(TextAlignment.CENTER); //居中
        CWC_LongestLine cwc = new CWC_LongestLine();
        asciiTable.getRenderer().setCWC(cwc);
        String result = asciiTable.render();
        Console.println(result, Console.FLUORESCENT_GREEN, Console.BOLD);
    }

    public static void main(String[] args) {
        String[] header = new String[]{"col 1", "col 2"};

        List<String[]> contents = new ArrayList<>();
        contents.add(new String[]{"row 2 col 1", "row 1 col 2"});
        contents.add(new String[]{"row 2 col 1", "row 2 col 2"});
        println(contents, header);
    }


}

