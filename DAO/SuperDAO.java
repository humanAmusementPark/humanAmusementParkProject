package javaproject.DAO;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SuperDAO<T> {
    private String driver = "org.mariadb.jdbc.Driver";
    private String url = "jdbc:mariadb://192.168.0.18:3306/mysql";
//    private String url = "jdbc:mariadb://localhost:3306/mysql";
    private String user = "root";
    //private String password = "park1676";
    private String password = "1111";
    private static Connection conn;
    public SuperDAO() {
        init();
    }

    private void init() {
        try {
            Class.forName(driver);
            System.out.println("클래스 load");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "클래스 로드 실패", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {}
                conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 연결 실패", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }
        return conn;
    }
}
