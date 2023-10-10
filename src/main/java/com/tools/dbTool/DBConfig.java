package com.tools.dbTool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;

/**
 * @Classname: DatabaseConfig
 * @Date: 2022/7/18 上午 10:35
 * @Author: kalam_au
 * @Description:
 */


public class DBConfig {
    private static final Logger log = LogManager.getLogger(DBConfig.class.getName());

    public static final String MARIADB = "MARIADB";
    public static final String MYSQL = "MYSQL";
    public static final String ORACLE19C = "ORACLE19C";

    private String dbClassName;
    private String connection;
    private String username;
    private String password;


    public DBConfig(@NonNull String dbType) {
        switch (dbType) {
            case MARIADB -> {
                this.dbClassName = "org.mariadb.jdbc.Driver";
                this.connection = "jdbc:mariadb://192.168.31.131:3306/";
                this.username = "root";
                this.password = "garlamau";
            }
            case ORACLE19C -> {
                this.dbClassName = "oracle.jdbc.OracleDriver";
                this.connection = "jdbc:oracle:thin:@192.168.8.66:1521:MPFADB";
                this.username = "USERPROV";
                this.password = "Passw0rd";
            }
            case MYSQL -> {
                this.dbClassName = "com.mysql.cj.jdbc.Driver";
                this.connection = "jdbc:mysql://localhost:3306/";
                // this.connection = "jdbc:mysql://localhost:3306/ace?useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8&useSSL=false";
                this.username = "root";
                this.password = "root";
            }
        }

    }


    public String getDbClassName() {
        return dbClassName;
    }

    public void setDbClassName(String dbClassName) {
        this.dbClassName = dbClassName;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

