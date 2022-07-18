package util.dbUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleDBUtil {
    private static final Logger log = LogManager.getLogger(OracleDBUtil.class.getName());


    // The JDBC Connector Class.
    //  private static final String dbClassName = "oracle.jdbc.OracleDriver";

//    private static final String CONNECTION = "jdbc:oracle:thin:@192.168.8.66:1521:MPFADB";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBConfig oracleConfig = new DBConfig(DBConfig.ORACLE19C);
        String dbClassName = oracleConfig.getDbClassName();
        String connection = oracleConfig.getConnection();
        String username = oracleConfig.getUsername();
        String password = oracleConfig.getPassword();
        System.out.println(dbClassName);
        Connection c = DriverManager.getConnection(connection, username, password);
        System.out.println(c.getSchema());
        System.out.println("It works !");
        c.close();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
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
