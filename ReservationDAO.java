package javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ReservationDAO {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static ReservationDAO instance;

    ReservationDAO(){
        init();
    }

    public static ReservationDAO getInstance(){
        if( instance ==null){
            instance = new ReservationDAO();
        }
        return instance;
    }

    private void init(){
        try{
            Class.forName(driver);
        }catch(ClassNotFoundException e){
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
}
