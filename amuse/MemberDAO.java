package amuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MemberDAO {
	static MemberDAO dao=null;
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:7777/amuse";
	private Connection conn=null;
	
	public static MemberDAO getInstance() {
		if(dao==null)
			dao=new MemberDAO();
		return dao;
	}
	
	private MemberDAO(){
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
				conn = DriverManager.getConnection(url,"system","1111");
			b=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public MemberDTO selectme(String mid) {
		PreparedStatement ptmt=null;
		MemberDTO m=null;
		try {
			if(connect()) {
				String sql = "select * from member where mId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,mid);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					m= new MemberDTO();
					m.setMId(mid);
					m.setMPass(rs.getString("mPass"));
					m.setMName(rs.getString("mName"));
					m.setMBirth(rs.getDate("mBirth"));
					m.setMGender(rs.getInt("mGender"));
					m.setTPass(rs.getString("tPass"));
					
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
		return m;
		
	}
	
	
	
	public void updateme(MemberDTO m) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "update member set mPass=?, mName=?, mGender=? ,mBirth=? where mId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,m.getMPass());
				ptmt.setString(2,m.getMName());
				ptmt.setInt(3,m.getMGender());
				ptmt.setDate(4,m.getMBirth());
				ptmt.setString(5,m.getMId());
				
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
	
	

}
