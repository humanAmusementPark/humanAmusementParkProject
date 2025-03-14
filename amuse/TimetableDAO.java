
package amuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TimetableDAO {
	static TimetableDAO dao=null;
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:7777/amuse";
	private Connection conn=null;
	
	
	
	public static TimetableDAO getInstance() {
		if(dao==null)
			dao=new TimetableDAO();
		return dao;
	}
	
	private TimetableDAO(){
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
	
	public ArrayList<TimetableDTO> selectti() {
		Statement stmt=null;
		ArrayList<TimetableDTO> blist=new ArrayList<>();
		try {
			if(connect()) {
				String sql = "select * from timetable";
				stmt = conn.createStatement();

				
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()) {
					TimetableDTO b= new TimetableDTO();
					b.setTiId(rs.getString("tiId"));
					b.setTiDay(rs.getInt("tiDay"));
					b.setTiTime(rs.getString("tiTime"));
					b.setTiContent(rs.getString("tiContent"));
					blist.add(b);
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return blist;
		
	}
	
	
	
	public void updateti(TimetableDTO b) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "update timetable set tiDay=?, tiTime=? ,content=? where tiId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setInt(1,b.getTiDay());
				ptmt.setString(2,b.getTiTime());
				ptmt.setString(3,b.getTiContent());
				ptmt.setString(4,b.getTiId());

				
				int r=ptmt.executeUpdate();
				System.out.println(r+" 업데이트");
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
	

	public void deleteti(String num) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "delete from timetable where tiId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,num);
				
				
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
	
	
	
	public void insertres(TimetableDTO b) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "insert into banner values(?,?,?,?)";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,b.getTiId());
				ptmt.setInt(2,b.getTiDay());
				ptmt.setString(3,b.getTiTime());
				ptmt.setString(4,b.getTiContent());
				
				int rq=ptmt.executeUpdate();
				System.out.println(rq+" ㅅ바입");
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
