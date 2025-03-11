package javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ManagerDAO {
Scanner in = new Scanner(System.in);
    private static ManagerDAO instance;
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    Connection conn = null;
    PreparedStatement psmt = null;

    ManagerDAO(){
        init();
    }

    public static ManagerDAO getInstance(){
        if(instance == null){
            instance = new ManagerDAO();
        }
        return instance;
    }
    private Connection connection() {
        try {
            return DriverManager.getConnection(url, "system", "1111");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void newManagerLogin(){
        System.out.println("아이디 : ");
        String mid = in.nextLine();

    }

}
