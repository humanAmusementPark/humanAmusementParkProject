package javaproject.jungjin.amuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAO {
	static TicketDAO dao=null;
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:7777/amuse";
	private Connection conn=null;
	
	public static TicketDAO getInstance() {
		if(dao==null)
			dao=new TicketDAO();
		return dao;
	}
	
	private TicketDAO(){
		init();
	}

	private void init() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean connect() {
		boolean b=false;
		try {
			if(conn==null || conn.isClosed())
				conn = DriverManager.getConnection(url,"root","1111");
			b=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public TicketDTO selectti(String id) {
		PreparedStatement ptmt=null;
		TicketDTO t=null;
		try {
			if(connect()) {
				String sql = "select * from ticket where tPass =?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					t= new TicketDTO();
					t.setTPass(rs.getString("tPass"));
					t.setTName(rs.getString("tName"));
					t.setTPrice(rs.getInt("tPrice"));
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
		
	}

	public void deleteti(String id) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "delete from ticket where tPass=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				
				int r=ptmt.executeUpdate();
				System.out.println(r+" 삭제");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
