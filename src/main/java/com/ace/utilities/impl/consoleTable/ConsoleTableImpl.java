package com.ace.utilities.impl.consoleTable;

import com.ace.utilities.Console;
import com.ace.utilities.impl.consoleTable.enums.Align;
import com.ace.utilities.impl.consoleTable.enums.NullPolicy;
import com.ace.utilities.impl.consoleTable.table.Body;
import com.ace.utilities.impl.consoleTable.table.Cell;
import com.ace.utilities.impl.consoleTable.table.Header;
import com.ace.utilities.impl.consoleTable.util.StringPadUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * console table
 * @author chenlei
 * @dateTime 2018/12/11 9:28
 * @description ConsoleTable
 */
public class ConsoleTableImpl {

    private Header header;
    private Body body;
    String lineSep = "\n";
    String verticalSep = "|";
    String horizontalSep = "-";
    String joinSep = "+";
    int[] columnWidths;
    NullPolicy nullPolicy = NullPolicy.EMPTY_STRING;
    boolean restrict = false;

    private ConsoleTableImpl(){}

    public void print() {
       // System.out.println(getContent());
        Console.println(getContent(), Console.BOLD, Console.FLUORESCENT_GREEN);
    }

    String getContent() {
        return toString();
    }

    List<String> getLines(){
        List<String> lines = new ArrayList<>();
        if((header != null && !header.isEmpty()) || (body != null && !body.isEmpty())){
            lines.addAll(header.print(columnWidths,horizontalSep,verticalSep,joinSep));
            lines.addAll(body.print(columnWidths,horizontalSep,verticalSep,joinSep));
        }
        return lines;
    }

    @Override
    public String toString() {
        return StringUtils.join(getLines(), lineSep);
    }

    public static class ConsoleTableBuilder {

        ConsoleTableImpl consoleTableImpl = new ConsoleTableImpl();

        public ConsoleTableBuilder(){
            consoleTableImpl.header = new Header();
            consoleTableImpl.body = new Body();
        }

        public ConsoleTableBuilder addHead(Cell cell){
            consoleTableImpl.header.addHead(cell);
            return this;
        }

        public ConsoleTableBuilder addRow(List<Cell> row){
            consoleTableImpl.body.addRow(row);
            return this;
        }

        public ConsoleTableBuilder addHeaders(List<Cell> headers){
            consoleTableImpl.header.addHeads(headers);
            return this;
        }

        public ConsoleTableBuilder addRows(List<List<Cell>> rows){
            consoleTableImpl.body.addRows(rows);
            return this;
        }

        public ConsoleTableBuilder lineSep(String lineSep){
            consoleTableImpl.lineSep = lineSep;
            return this;
        }

        public ConsoleTableBuilder verticalSep(String verticalSep){
            consoleTableImpl.verticalSep = verticalSep;
            return this;
        }

        public ConsoleTableBuilder horizontalSep(String horizontalSep){
            consoleTableImpl.horizontalSep = horizontalSep;
            return this;
        }

        public ConsoleTableBuilder joinSep(String joinSep){
            consoleTableImpl.joinSep = joinSep;
            return this;
        }

        public ConsoleTableBuilder nullPolicy(NullPolicy nullPolicy){
            consoleTableImpl.nullPolicy = nullPolicy;
            return this;
        }

        public ConsoleTableBuilder restrict(boolean restrict){
            consoleTableImpl.restrict = restrict;
            return this;
        }

        public ConsoleTableImpl build(){
            //compute max column widths
            if(!consoleTableImpl.header.isEmpty() || !consoleTableImpl.body.isEmpty()){
                List<List<Cell>> allRows = new ArrayList<>();
                allRows.add(consoleTableImpl.header.cells);
                allRows.addAll(consoleTableImpl.body.rows);
                int maxColumn = allRows.stream().map(List::size).mapToInt(size -> size).max().getAsInt();
                int minColumn = allRows.stream().map(List::size).mapToInt(size -> size).min().getAsInt();
                if(maxColumn != minColumn && consoleTableImpl.restrict){
                    throw new IllegalArgumentException("number of columns for each row must be the same when strict mode used.");
                }
                consoleTableImpl.columnWidths = new int[maxColumn];
                for (List<Cell> row : allRows) {
                    for (int i = 0; i < row.size(); i++) {
                        Cell cell = row.get(i);
                        if(cell == null || cell.getValue() == null){
                            cell = consoleTableImpl.nullPolicy.getCell(cell);
                            row.set(i,cell);
                        }
                        int length = StringPadUtil.strLength(cell.getValue());
                        if(consoleTableImpl.columnWidths[i] < length){
                            consoleTableImpl.columnWidths[i] = length;
                        }
                    }
                }
            }
            return consoleTableImpl;
        }
    }

    public static void main(String[] args){
        List<Cell> header = new ArrayList<Cell>(){{
            add(new Cell("name"));
            add(new Cell("email"));
            add(new Cell("tel"));
        }};
        List<List<Cell>> body = new ArrayList<List<Cell>>(){{
            add(new ArrayList<Cell>(){{
                add(new Cell("kat"));
                add(new Cell(Align.CENTER,"kat@gimal.com"));
                add(new Cell(Align.RIGHT,"54321"));
            }});
            add(new ArrayList<Cell>(){{
                add(new Cell("ashe"));
                add(new Cell("ashe_111@hotmail.com"));
                add(new Cell("9876543中文测试210"));
            }});
            add(new ArrayList<Cell>(){{
                add(null);
                add(new Cell(null));
                add(new Cell(Align.LEFT,"11"));
            }});
        }};
        //default
        new ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

        //中文
        header = new ArrayList<Cell>(){{
            add(new Cell("姓名name"));
            add(new Cell("电子邮箱email"));
            add(new Cell("电话号码tel"));
        }};
        body = new ArrayList<List<Cell>>(){{
            add(new ArrayList<Cell>(){{
                add(new Cell("凯特kat"));
                add(new Cell(Align.CENTER,"kat@gimal.com"));
                add(new Cell(Align.RIGHT,"54321"));
            }});
            add(new ArrayList<Cell>(){{
                add(new Cell("艾希ashe"));
                add(new Cell("ashe_111@hotmail.com"));
                add(new Cell("9876543210"));
            }});
            add(new ArrayList<Cell>(){{
                add(new Cell("這是一串很長的繁體中文"));
                add(new Cell("これは長い日本語です"));
                add(new Cell(Align.LEFT,"11这是一串很长的中文"));
            }});
        }};
        new ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

        //no header
        //new ConsoleTable.ConsoleTableBuilder().addRows(body).build().print();

        //restrict
        //header.add(new Cell("not restrict"));
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(false).build().print();
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(true).build().print();

        //"null"
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.NULL_STRING).build().print();
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.THROW).build().print();

        //line sep
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).lineSep("\n\n").build().print();

        //vertical sep & horizontal sep & join sep
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).verticalSep("*").horizontalSep("*").joinSep("*").build().print();
    }
}
