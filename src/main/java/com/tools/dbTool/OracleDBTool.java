package com.tools.dbTool;

import com.util.SystemUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OracleDBTool {
    private static final Logger log = LogManager.getLogger(OracleDBTool.class.getName());


    // The JDBC Connector Class.
    //  private static final String dbClassName = "oracle.jdbc.OracleDriver";

//    private static final String CONNECTION = "jdbc:oracle:thin:@192.168.8.66:1521:MPFADB";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBConfig oracleConfig = new DBConfig(DBConfig.ORACLE19C);
        //  String dbClassName = oracleConfig.getDbClassName();
        String connection = oracleConfig.getConnection();
        String username = oracleConfig.getUsername();
        String password = oracleConfig.getPassword();
        //  System.out.println("dbClassName: " + dbClassName);
        System.out.println("connection: " + connection);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println(SystemUtil.LINE);

        Connection c = DriverManager.getConnection(connection, username, password);
        DatabaseMetaData metaData = c.getMetaData();
        System.out.println(c.getSchema());
        System.out.println("It works !");

        getTableFieldInfo("R_UPF_USER_PROFILE", "USERPROV");
        c.close();
    }


    public static void getTableFieldInfo(String Table, String Owner) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        List<HashMap<String, String>> columns = new ArrayList<HashMap<String, String>>();
        try {
            Statement stmt = con.createStatement();

            String sql = "select " + "         comments as \"Name\"," + "         a.column_name \"Code\"," + "         a.DATA_TYPE as \"DataType\"," + "         b.comments as \"Comment\"," + "         decode(c.column_name,null,'FALSE','TRUE') as \"Primary\"," + "         decode(a.NULLABLE,'N','TRUE','Y','FALSE','') as \"Mandatory\"," + "         '' \"sequence\"" + "   from " + "       all_tab_columns a, " + "       all_col_comments b," + "       (" + "        select a.constraint_name, a.column_name" + "          from user_cons_columns a, user_constraints b" + "         where a.constraint_name = b.constraint_name" + "               and b.constraint_type = 'P'" + "               and a.table_name = '" + Table + "'" + "       ) c" + "   where " + "     a.Table_Name=b.table_Name " + "     and a.column_name=b.column_name" + "     and a.Table_Name='" + Table + "'" + "     and a.owner=b.owner " + "     and a.owner='" + Owner + "'" + "     and a.COLUMN_NAME = c.column_name(+)" + "  order by a.COLUMN_ID";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Name", rs.getString("Name"));
                map.put("Code", rs.getString("Code"));
                map.put("DataType", rs.getString("DataType"));
                map.put("Comment", rs.getString("Comment"));
                map.put("Primary", rs.getString("Primary"));
                map.put("Mandatory", rs.getString("Mandatory"));
                columns.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }


    // 得到当前数据库下所有的表名
    public static void getTableNameList(Connection con) {
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                System.out.println("Table Name: " + rs.getString(3));
                System.out.println("Table Owner: " + rs.getString(2));
                System.out.println("------------------------------");
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    public static void getDBInfo() throws SQLException {
        DBConfig oracleConfig = new DBConfig(DBConfig.ORACLE19C);
        //   String dbClassName = oracleConfig.getDbClassName();
        String connection = oracleConfig.getConnection();
        String username = oracleConfig.getUsername();
        String password = oracleConfig.getPassword();
        Connection c = DriverManager.getConnection(connection, username, password);
        DatabaseMetaData metaData = c.getMetaData();
        System.out.println("数据库已知的用户: " + metaData.getUserName());
        System.out.println("数据库的系统函数的逗号分隔列表: " + metaData.getSystemFunctions());
        System.out.println("数据库的时间和日期函数的逗号分隔列表: " + metaData.getTimeDateFunctions());
        System.out.println("数据库的字符串函数的逗号分隔列表: " + metaData.getStringFunctions());
        System.out.println("数据库供应商用于 'schema' 的首选术语: " + metaData.getSchemaTerm());
        System.out.println("数据库URL: " + metaData.getURL());
        System.out.println("是否允许只读:" + metaData.isReadOnly());
        System.out.println("数据库的产品名称:" + metaData.getDatabaseProductName());
        System.out.println("数据库的版本:" + metaData.getDatabaseProductVersion());
        System.out.println("驱动程序的名称:" + metaData.getDriverName());
        System.out.println("驱动程序的版本:" + metaData.getDriverVersion());
        System.out.println(SystemUtil.LINE);
        System.out.println("数据库中使用的表类型");

        ResultSet rs = metaData.getTableTypes();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        DBConfig oracleConfig = new DBConfig(DBConfig.ORACLE19C);
        String dbClassName = oracleConfig.getDbClassName();
        String connection = oracleConfig.getConnection();
        String username = oracleConfig.getUsername();
        String password = oracleConfig.getPassword();

        System.out.println(dbClassName);
        Class.forName(dbClassName);
        Connection c = DriverManager.getConnection(connection, username, password);
        return c;
    }


    public void close(Connection c) throws SQLException {
        c.close();
    }


}
