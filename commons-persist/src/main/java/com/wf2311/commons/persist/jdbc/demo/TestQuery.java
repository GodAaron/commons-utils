package com.wf2311.commons.persist.jdbc.demo;

import com.wf2311.commons.persist.jdbc.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wf2311
 * @date 2016/03/13 15:28.
 */
public class TestQuery {
    public static String classString = "com.mysql.jdbc.Driver";
    public static String connectionString = "jdbc:mysql://127.0.0.1:3306/zblog";
    public static String userName = "root";
    public static String passWord = "111111";
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection(classString, connectionString, userName, passWord);
        dbConnection.open();
        String sql = "select tid,subject from sns_theme order by tid limit 1,1000";
        ResultSet rs = dbConnection.executeQuery(sql);
        try {
            while (rs.next()) {
                Long tid = rs.getLong("tid");
                String subject = rs.getString("subject");
                System.out.println(tid+"\t"+subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}
