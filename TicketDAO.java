package javaproject;

import java.sql.*;

public class TicketDAO {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    Connection conn = null;
    PreparedStatement psmt = null;
    private final String ticktN = "LTO";
    private final String seq = "SELECT PRODUCT_seq.NEXTVAL FROM dual";
    private static TicketDAO instance;


    public static TicketDAO getInstance(){
        if( instance ==null){
            instance = new TicketDAO();
        }
        return instance;
    }
    TicketDAO() {
        init();
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

    public boolean checkTicket(String id) throws SQLException {
        conn = connection();
        String sql = "SELECT * FROM TICKET WHERE USERID = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, id);
        ResultSet rs = psmt.executeQuery();

        if (rs.next()) {
            rs.close();
            psmt.close();
            conn.close();
            return true;
        }
        rs.close();
        psmt.close();
        conn.close();
        return false;

    }

    public void normalTicket(String id) throws SQLException {
        conn = connection();
        String sql = "INSERT INTO ticket values ?,?,?,?,?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, ticktN + seq);
        psmt.setString(2, "일반권");
        psmt.setString(3, "일반권입니다.");
        psmt.setString(4, "30000만원");
        psmt.setString(5, id);
        psmt.executeQuery();
        psmt.close();
        conn.close();
    }

    public void magicTicket(String id) throws SQLException {
        conn = connection();
        String sql = "INSERT INTO ticket values ?,?,?,?,?";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, ticktN + seq);
        psmt.setString(2, "매직패스");
        psmt.setString(3, "매직패스권");
        psmt.setString(4, "60000만원");
        psmt.setString(5, id);
        psmt.executeQuery();
        psmt.close();
        conn.close();
    }
}
