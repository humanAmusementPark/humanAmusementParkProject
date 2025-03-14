package javaproject;


import DAO.SuperDAO;

import java.sql.*;

public class TicketDAO extends SuperDAO {
//    private String driver = "oracle.jdbc.driver.OracleDriver";
//    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
//    private String username = "system";
//    private String password = "1111";

    private Connection conn = null;
    private PreparedStatement psmt = null;
    private  TicketDAO instance;

    public TicketDAO() {
    	try {
    		this.conn = super.getConnection();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }


//    public  TicketDAO getInstance() {
//        if (instance == null) {
//            instance = new TicketDAO();
//        }
//
//        return instance;
//    }


    public boolean checkTicket(String id) throws SQLException {

        String sql = "SELECT * FROM TICKET WHERE USERID = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, id);
        ResultSet rs = psmt.executeQuery();

        boolean hasTicket = rs.next(); // 티켓이 있는지 확인

        rs.close();
        psmt.close();
        conn.close();

        return hasTicket;
    }

    public void normalTicket(String id) throws SQLException {


        // 시퀀스 값 가져오기
        String seqSql = "SELECT PRODUCT_seq.NEXTVAL FROM dual";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(seqSql);
        String ticketId = "";

        if (rs.next()) {
            ticketId = "Normal" + rs.getInt(1);
        }
        rs.close();
        stmt.close();

        // 티켓 삽입
        String sql = "INSERT INTO ticket VALUES (?,?,?,?,?)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, ticketId);
        psmt.setString(2, "일반권");
        psmt.setString(3, "일반권입니다.");
        psmt.setString(4, "30,000원");
        psmt.setString(5, id);

        psmt.executeUpdate();
        psmt.close();
        conn.close();
    }

    public void magicTicket(String id) throws SQLException {


        // 시퀀스 값 가져오기
        String seqSql = "SELECT PRODUCT_seq.NEXTVAL FROM dual";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(seqSql);
        String ticketId = "";

        if (rs.next()) {
            ticketId = "Magic" + rs.getInt(1);
        }
        rs.close();
        stmt.close();

        // 티켓 삽입
        String sql = "INSERT INTO ticket VALUES (?,?,?,?,?)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, ticketId);
        psmt.setString(2, "매직패스");
        psmt.setString(3, "매직패스권");
        psmt.setString(4, "60,000원");
        psmt.setString(5, id);

        psmt.executeQuery();
        psmt.close();
        conn.close();
    }
}
