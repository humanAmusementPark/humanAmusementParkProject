package javaproject.DAO;



import javaproject.DTO.ReservationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO extends SuperDAO implements DAOinf<ReservationDTO> {


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
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return reservations;
    }

    @Override
    public ReservationDTO select(String id) {
        return null;
    }

    @Override
    public boolean insert(ReservationDTO r) {
        PreparedStatement ptmt=null;
        boolean flag=false;
        try {
            Connection  conn=super.getConnection();
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

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    @Override
    public boolean update(ReservationDTO data) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        Connection conn = super.getConnection();
        String sql = "delete from reservation where no = ?";
        int intNo = Integer.parseInt(id);
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, intNo);
            int result = stmt.executeUpdate();
            System.out.println(result + "건 완료");
            if(result > 0) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public int selectatt(String id) {
        PreparedStatement ptmt=null;
        int r=0;
        try {
            Connection conn=super.getConnection();
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
            Connection conn=super.getConnection();
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

    public int getcount(String id) {
        int count=0;

        PreparedStatement ptmt=null;
        try {
            Connection  conn=super.getConnection();
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



