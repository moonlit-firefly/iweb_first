package com.iweb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**JDBC
 * @author 陈郅治
 */
public class JDBCUtil {
    private final static String URL="jdbc:mysql:" +
            "//121.5.180.160:3306/shop?characterEncoding=utf8&useSSL=false";
    private final static String USERNAME="shop";
    private final static String PASSWORD="admin111";
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);

    }

}
