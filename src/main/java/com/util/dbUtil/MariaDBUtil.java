package com.util.dbUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MariaDBUtil {
    private static final Logger log = LogManager.getLogger(MariaDBUtil.class.getName());


    // The JDBC Connector Class.
    private static final String dbClassName = "org.mariadb.jdbc.Driver";
    // Connection string. emotherearth is the database the program
    // is connecting to. You can include user and password after this
    // by adding (say) ?user=paulr&password=paulr. Not recommended!
    private static final String CONNECTION = "jdbc:mariadb://192.168.31.131:3306/";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println(dbClassName);
        // Class.forName(xxx) loads the jdbc classes and
        // creates a drivermanager class factory
        Class.forName(dbClassName);
        // Properties for user and password. Here the user and password are both 'paulr'
        Properties p = new Properties();
        p.put("user", "root");
        p.put("password", "garlamau");
        // Now try to connect
        Connection c = DriverManager.getConnection(CONNECTION, p);
        System.out.println(c.getSchema());
        System.out.println("It works !");
        c.close();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println(dbClassName);
        // Class.forName(xxx) loads the jdbc classes and
        // creates a drivermanager class factory
        Class.forName(dbClassName);
        // Properties for user and password. Here the user and password are both 'paulr'
        Properties p = new Properties();
        p.put("user", "root");
        p.put("password", "garlamau");
        // Now try to connect
        Connection c = DriverManager.getConnection(CONNECTION, p);
        return c;
    }


    public void close(Connection c) throws SQLException {
        c.close();
    }



}
