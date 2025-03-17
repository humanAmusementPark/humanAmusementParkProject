package javaproject.jungjin.amuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginService {
    Scanner in = new Scanner(System.in);
    private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:7777/amuse";
	private Connection conn=null;
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
            return DriverManager.getConnection(url, "root", "1111");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean idDuplicate(String type,String type2,String id)  {
        conn = connection();
        String sql = "SELECT * FROM ? WHERE ? = ?";
        boolean exists=false;
        try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,type);
			psmt.setString(2,type2);
			psmt.setString(3,id);
			ResultSet rs = psmt.executeQuery();
			exists = rs.next();
			conn.close();
			rs.close();
			psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return exists;
    }
    public boolean idPassDuplicate(String type,String id,String id2,String pw,String pw2){
        conn = connection();
        String sql = "SELECT * FROM "+type+" WHERE "+id+" = ? and "+pw+" = ?";
        boolean exists=false;
        try {
        	psmt = conn.prepareStatement(sql);
        	

        	psmt.setString(1,id2);
			psmt.setString(2,pw2);

			ResultSet rs = psmt.executeQuery(); // 🔹 executeQuery() 사용

	        if (rs.next()) { // 🔹 결과가 있는지 확인
	            exists = true;
	        }
	        rs.close();  // 🔹 ResultSet 닫기
	        psmt.close(); // 🔹 PreparedStatement 닫기
	        conn.close(); // 🔹 Connection 닫기
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
