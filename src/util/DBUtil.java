package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static DBUtil instance;

    public DBUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("find db driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static DBUtil getInstance() {
        if (instance == null) instance = new DBUtil();
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:15000/backpjt", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
