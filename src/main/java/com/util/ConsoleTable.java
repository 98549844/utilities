package com.util;

import com.util.impl.consoleTable.ConsoleTableImpl;
import com.util.impl.consoleTable.enums.Align;
import com.util.impl.consoleTable.table.Cell;

import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: aaa
 * @Date: 2023/7/19 下午 12:28
 * @Author: kalam_au
 * @Description:
 */


public class ConsoleTable {

    public static void println(List<Cell> header, List<List<Cell>> body) {
        new ConsoleTableImpl.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print(); // default
    }

    public static void println(List<List<Cell>> body) {
        new ConsoleTableImpl.ConsoleTableBuilder().addRows(body).build().print();//no header
    }


    public static void main(String[] args) {
        List<Cell> header = new ArrayList<>() {{
            add(new Cell("name"));
            add(new Cell("email"));
            add(new Cell("tel"));
        }};
        List<List<Cell>> body = new ArrayList<>() {{
            add(new ArrayList<Cell>() {{
                add(new Cell("kat"));
                add(new Cell("kat@gimal.com"));
                add(new Cell("54321"));
            }});
            add(new ArrayList<>() {{
                add(new Cell("ashe"));
                add(new Cell("ashe_111@hotmail.com"));
                add(new Cell("9876543210"));
            }});
            add(new ArrayList<>() {{
                add(null);
                add(new Cell(null));
                add(new Cell("11"));
            }});
        }};

        println(header, body);
     //   println(body);

    }

}

