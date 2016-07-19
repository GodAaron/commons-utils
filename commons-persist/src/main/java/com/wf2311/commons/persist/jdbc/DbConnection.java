package com.wf2311.commons.persist.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author wf2311
 * @date 2016/03/12 10:23.
 */
public class DbConnection {
    public String classString = null;
    public String connectionString = null;
    public String userName = null;
    public String passWord = null;

    private Connection conn;
    private Statement stmt;

    public DbConnection(String classString, String connectionString) {
        this.classString = classString;
        this.connectionString = connectionString;
    }

    public DbConnection(String classString, String connectionString, String userName, String passWord) {
        this.classString = classString;
        this.connectionString = connectionString;
        this.userName = userName;
        this.passWord = passWord;
    }

    public DbConnection() {
        //From System.xml
        // classString=XmlUtility.getConfigValue("DB_ClassString");//"oracle.jdbc.driver.OracleDriver";
        // connectionString=XmlUtility.getConfigValue("DB_ConnectionString");//"jdbc:oracle:thin:@192.168.103.171:1521:jstrd";
        //userName=XmlUtility.getConfigValue("DB_UserName");//"htgl";
        //passWord=XmlUtility.getConfigValue("DB_PassWord");//"1";

        //For ODBC
        //classString="sun.jdbc.odbc.JdbcOdbcDriver";
        //connectionString=("jdbc:odbc:DBDemo");
        //userName="dbdemo";
        //passWord="dbdemo";


        //For Access Driver
        //classString="sun.jdbc.odbc.JdbcOdbcDriver";
        //connectionString=("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=C:\\dbdemo.mdb;ImplicitCommitSync=Yes;MaxBufferSize=512;MaxScanRows=128;PageTimeout=5;SafeTransactions=0;Threads=3;UserCommitSync=Yes;").replace('\\','/');

        //For SQLServer Driver
        //      MSSQL 2005：Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //      MSSQL 2000：Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        //classString="net.sourceforge.jtds.jdbc.Driver";
        // connectionString="jdbc:jtds:sqlserver://127.0.0.1:1433;DatabaseName=name;User=sa;Password=pwd";

        //For Oracle Driver
        //classString="oracle.jdbc.driver.OracleDriver";
        //connectionString="jdbc:oracle:thin:@192.168.103.171:1521:jstrd";
        //userName="htgl";
        //passWord="1";

        //For MySQL Driver
        //classString="org.gjt.mm.mysql.Driver";
        //connectionString="jdbc:mysql://localhost:3306/blog?user=root&password=pwd&useUnicode=true&characterEncoding=8859_1";
        //classString="com.mysql.jdbc.Driver";
        //connectionString="jdbc:mysql://localhost:3306/blog?user=root&password=pwd&useUnicode=true&characterEncoding=gb2312";
        classString = "com.mysql.jdbc.Driver";
        connectionString = "jdbc:mysql://127.0.0.1:3306/sns_test47?user=root&password=111111&useUnicode=true&characterEncoding=utf8";

        //For Sybase Driver
        //classString="com.sybase.jdbc.SybDriver";
        //connectionString="jdbc:sybase:Tds:localhost:5007/tsdata"; //tsdata为你的数据库名
        //Properties sysProps = System.getProperties();
        //SysProps.put("user","userid");
        //SysProps.put("password","user_password");
        //If using Sybase then DriverManager.getConnection(connectionString,sysProps);
    }

    /**
     * 打开连接
     *
     * @return
     */
    public boolean open() {
        boolean mResult;
        try {
            Class.forName(classString);
            if ((userName == null) && (passWord == null)) {
                conn = DriverManager.getConnection(connectionString);
            } else {
                conn = DriverManager.getConnection(connectionString, userName, passWord);
            }

            stmt = conn.createStatement();
            mResult = true;
        } catch (Exception e) {
            System.out.println(e.toString());
            mResult = false;
        }
        return (mResult);
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 获取当前时间(JAVA)
     *
     * @return
     */
    public String getDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mDateTime = formatter.format(cal.getTime());
        return (mDateTime);
    }

    /**
     * 获取当前时间(T-SQL)
     *
     * @return
     */
    public java.sql.Date getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String mDateTime = formatter.format(cal.getTime());
        return (java.sql.Date.valueOf(mDateTime));
    }

    /**
     * 生成新的ID
     *
     * @param vTableName
     * @param vFieldName
     * @return
     */
    public int getMaxID(String vTableName, String vFieldName) {
        int mResult = 0;
        boolean mConn = true;
        String mSql = new String();
        mSql = "select max(" + vFieldName + ")+1 as MaxID from " + vTableName;
        try {
            if (conn != null) {
                mConn = conn.isClosed();
            }
            if (mConn) {
                open();
            }

            ResultSet result = executeQuery(mSql);
            if (result.next()) {
                mResult = result.getInt("MaxID");
            }
            result.close();

            if (mConn) {
                close();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return (mResult);
    }

    /**
     * 数据检索
     *
     * @param SqlString
     * @return
     */
    public ResultSet executeQuery(String SqlString) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(SqlString);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return (result);
    }

    /**
     * 数据更新(增、删、改)
     *
     * @param SqlString
     * @return
     */
    public int executeUpdate(String SqlString) {
        int result = 0;
        try {
            result = stmt.executeUpdate(SqlString);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return (result);
    }
}
