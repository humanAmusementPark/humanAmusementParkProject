package javaproject.jungjin.amuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResDAO {
	static ResDAO dao=null;
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:7777/amuse";
	private Connection conn=null;
	
	public static ResDAO getInstance() {
		if(dao==null)
			dao=new ResDAO();
		return dao;
	}
	
	private ResDAO(){
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
	
	public ResDTO selectres(String id) {
		PreparedStatement ptmt=null;
		ResDTO r=null;
		try {
			if(connect()) {
				String sql = "select * from reservation where mId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					r= new ResDTO();
					r.setMId(rs.getString("mId"));
					r.setTPass(rs.getString("tPass"));
					r.setRId(rs.getString("rId"));
					r.setAtId(rs.getString("atId"));
					r.setRTime(rs.getString("rTime"));
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
		return r;
	}
	
	public int selectatt(String id) {
		PreparedStatement ptmt=null;
		int r=0;
		try {
			if(connect()) {
				String sql = "select count(*) from reservation where atId=? and DATE(rTime) = CURDATE()";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					r=rs.getInt(1);
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
		return r;
		
	}
	
	public int selectvip(String id) {
		PreparedStatement ptmt=null;
		int r=0;
		try {
			if(connect()) {
				String sql = "select count(*) from reservation r inner join ticket t on r.tpass="
						+ "t.tpass where atId=? and DATE(rTime) = CURDATE() and tname='vip'";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					r=rs.getInt(1);
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
		return r;
		
	}
	public void deleteres(String id) {
		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "delete from reservation where mId=?";
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
	
	
	
	public boolean insertres(ResDTO r) {
		PreparedStatement ptmt=null;
		boolean flag=false;
		try {
			if(connect()) {
				String sql = "insert into reservation values(?,?,?,?,sysdate())";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,r.getRId());
				ptmt.setString(2,r.getMId());
				ptmt.setString(3,r.getTPass());
				ptmt.setString(4,r.getAtId());

				
				int rq=ptmt.executeUpdate();
				if(rq>0) {
					flag=true;
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

		return flag;
	}

	public int getcount(String id) {
		int count=0;

		PreparedStatement ptmt=null;
		try {
			if(connect()) {
				String sql = "select count(*) from reservation where mId=?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1,id);
				
				ResultSet rs=ptmt.executeQuery();
				if(rs.next()) {
					count=rs.getInt(1);
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
		
		return count;
	}
}
