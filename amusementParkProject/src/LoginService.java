package javaproject;

import java.sql.*;
import java.util.Scanner;

public class LoginService {
    Scanner in = new Scanner(System.in);
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    Connection conn = null;
    PreparedStatement psmt = null;
    private static LoginService instance;

    public LoginService(){
        init();
    }
    public static LoginService getInstance(){
        if(instance == null){
            instance = new LoginService();
        }
        return instance;
    }
    private void init() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Connection connection() {
        try {
            return DriverManager.getConnection(url, "system", "1111");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean idDuplicate(String type,String type2,String id) throws SQLException {
        conn = connection();
        String sql = "SELECT * FROM ? WHERE ? = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1,type);
        psmt.setString(2,type2);
        psmt.setString(3,id);
        ResultSet rs = psmt.executeQuery();
        boolean exists = rs.next();
        conn.close();
        rs.close();
        psmt.close();
        return exists;
    }
    public boolean idPassDuplicate(String type,String type2,String id,String type3,String pass) throws SQLException {
        conn = connection();
        String sql = "SELECT * FROM ? WHERE ? = ? and ? = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1,type);
        psmt.setString(2,id);
        ResultSet rs = psmt.executeQuery();
        boolean exists = rs.next();
        conn.close();
        rs.close();
        psmt.close();
        return exists;
    }

    public void addManager1(String id) throws SQLException {
        System.out.println("비밀번호 : ");
        String pass = in.nextLine();
        System.out.println("이름 : ");
        String name = in.nextLine();
        System.out.println("성별 (남,여)");
        String gender = in.nextLine();
        System.out.println("나이 : ");
        String age = in.nextLine();
        conn = connection();
        String sql = "INSERT INTO MANAGER VALUES(?,?,?,?,?)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1,id);
        psmt.setString(2,pass);
        psmt.setString(3,name);
        psmt.setString(4,gender);
        psmt.setString(5,age);
        psmt.executeQuery();
        conn.close();
        psmt.close();
    }
    public void addMember1(String id) throws SQLException {
        System.out.println("비밀번호 : ");
        String pass = in.nextLine();
        System.out.println("이름 : ");
        String name = in.nextLine();
        System.out.println("성별 (남,여)");
        String gender = in.nextLine();
        System.out.println("나이 : ");
        String age = in.nextLine();
        String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,null)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1,id);
        psmt.setString(2,pass);
        psmt.setString(3,name);
        psmt.setString(4,gender);
        psmt.setString(5,age);
        psmt.executeQuery();
        conn.close();
        psmt.close();
    }

}
