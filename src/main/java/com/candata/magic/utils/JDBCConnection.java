package com.candata.magic.utils;
import java.sql.Connection;
import java.sql.DriverManager;
 
public class JDBCConnection {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://58d657884ee92.gz.cdb.myqcloud.com:13921/candata?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private static final String username = "cdb_outerroot";
    private static final String password = "ci123456";
    
    public static Connection getConnection(){
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }    
        return null;
    }
    
}