package DAO;



import DTO.ReservationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO extends SuperDAO {
	Connection conn=null;

    @Override
    public List<ReservationDTO> selectAll() {
        List<ReservationDTO> reservations = new ArrayList<>();
        String sql = "select * from reservation";
        Connection conn = super.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ReservationDTO reservation = new ReservationDTO();
                reservation.setNo(rs.getInt("no"));
                reservation.setMId(rs.getString("mId"));
                reservation.setTPass(rs.getString("tPass"));
                reservation.setAtId(rs.getString("atId"));
                reservation.setRTime(rs.getDate("rTime"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }


    public void delete(Object o) {
        Connection conn = super.getConnection();
        String sql = "delete from reservation where no = ?";
        int intNo = Integer.parseInt(o.toString());
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, intNo);
            int result = stmt.executeUpdate();
            System.out.println(result + "건 완료");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            
        	
        }
    }
    
    
    
    public ReservationDTO selectres(String id) {
		PreparedStatement ptmt=null;
		ReservationDTO r=null;
		try {
			conn=getConnection();
			String sql = "select * from reservation where mId=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,id);
			
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				r= new ReservationDTO();
				r.setMId(rs.getString("mId"));
				r.setTPass(rs.getString("tPass"));
				r.setNo(rs.getInt("no"));
				r.setAtId(rs.getString("atId"));
				r.setRTime(rs.getDate("rTime"));
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();

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
			conn=getConnection();
			String sql = "select count(*) from reservation where atId=? and DATE(rTime) = CURDATE()";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,id);
			
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				r=rs.getInt(1);
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();

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
			conn=getConnection();
			String sql = "select count(*) from reservation r inner join ticket t on r.tpass="
					+ "t.tpass where atId=? and DATE(rTime) = CURDATE() and tname='vip'";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,id);
			
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				r=rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
		
	}
	public void deleteres(String id) {
		PreparedStatement ptmt=null;
		try {
			conn=getConnection();
			String sql = "delete from reservation where mId=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,id);
			
			
			int r=ptmt.executeUpdate();
			System.out.println(r+" 삭제");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public boolean insertres(ReservationDTO r) {
		PreparedStatement ptmt=null;
		boolean flag=false;
		try {
			conn=getConnection();
			String sql = "insert into reservation values(?,?,?,?,sysdate())";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1,r.getNo());
			ptmt.setString(2,r.getMId());
			ptmt.setString(3,r.getTPass());
			ptmt.setString(4,r.getAtId());
			
			
			int rq=ptmt.executeUpdate();
			if(rq>0) {
				flag=true;
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
			conn=getConnection();
			String sql = "select count(*) from reservation where mId=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,id);
			
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
}



