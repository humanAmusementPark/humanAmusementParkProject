package javaproject;

import java.sql.*;

public class LoginService {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String user = "system";
    private String password = "1111";
    private static LoginService instance;

    private LoginService() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean idDuplicate(String table, String column, String id) {
        String sql = String.format("SELECT 1 FROM %s WHERE %s = ?", table, column);
        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean idPassDuplicate(String table, String idColumn, String id, String passColumn, String pass) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", table, idColumn, passColumn);
        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, id);
            psmt.setString(2, pass);
            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addManager(String id, String pass, String name, String gender, int age) {
        String sql = "INSERT INTO MANAGER (id, pass, name, gender, age) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, id);
            psmt.setString(2, pass);
            psmt.setString(3, name);
            psmt.setString(4, gender);
            psmt.setInt(5, age);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember(String id, String pass, String name, String gender, int age) {
        String sql = "INSERT INTO MEMBER (id, pass, name, gender, age) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, id);
            psmt.setString(2, pass);
            psmt.setString(3, name);
            psmt.setString(4, gender);
            psmt.setInt(5, age);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
