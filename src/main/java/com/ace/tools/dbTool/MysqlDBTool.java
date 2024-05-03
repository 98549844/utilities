package com.ace.tools.dbTool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlDBTool {
    private static final Logger log = LogManager.getLogger(MysqlDBTool.class.getName());


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBConfig dbConfig = new DBConfig(DBConfig.MYSQL);


        System.out.println(dbConfig.getDbClassName());
        Class.forName(dbConfig.getDbClassName());
        Properties p = new Properties();
        p.put("user", dbConfig.getUsername());
        p.put("password", dbConfig.getPassword());
        // Now try to connect
        Connection c = DriverManager.getConnection(dbConfig.getConnection(), p);
        System.out.println("connection closed status: " + c.isClosed());
        System.out.println(c.getSchema());
        System.out.println("It works !");
        c.close();
        System.out.println("connection closed status: " + c.isClosed());
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        DBConfig dbConfig = new DBConfig(DBConfig.MYSQL);

        System.out.println(dbConfig.getDbClassName());
        Class.forName(dbConfig.getDbClassName());
        Properties p = new Properties();
        p.put("user", dbConfig.getUsername());
        p.put("password", dbConfig.getPassword());
        Connection c = DriverManager.getConnection(dbConfig.getConnection(), p);
        return c;
    }


    public void close(Connection c) throws SQLException {
        c.close();
    }


}
