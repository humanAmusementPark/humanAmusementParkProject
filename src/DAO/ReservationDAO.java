package javaproject.DAO;


import javaproject.DTO.ReservationDTO;

import javax.swing.*;
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
                ReservationDTO reservation = ReservationDTO.builder()
                        .no(rs.getInt("no"))
                        .mId(rs.getString("mId"))
                        .tPass(rs.getString("tPass"))
                        .atId(rs.getString("atId"))
                        .rTime(rs.getTimestamp("rTime"))
                        .build();
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public List<ReservationDTO> selectAll(String id) {
        List<ReservationDTO> reservations = new ArrayList<>();
        String sql = "select * from reservation where mId = ?";
        Connection conn = super.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationDTO reservation = ReservationDTO.builder()
                        .no(rs.getInt("no"))
                        .mId(rs.getString("mId"))
                        .tPass(rs.getString("tPass"))
                        .atId(rs.getString("atId"))
                        .rTime(rs.getTimestamp("rTime"))
                        .build();
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
        Connection conn = super.getConnection();
        PreparedStatement ptmt = null;
        try {
            String sql = "insert into reservation (mId, tPass, atId, rTime) values(?,?,?,sysdate())";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, r.getMId());
            ptmt.setString(2, r.getTPass());
            ptmt.setString(3, r.getAtId());
            int rq = ptmt.executeUpdate();
            if (rq > 0) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 입력 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(ReservationDTO r) {
        Connection conn = super.getConnection();
        String sql = "update reservation set mid = ?, tPass = ?,atId = ?,rTime = ? where no = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, r.getMId());
            ptmt.setString(2, r.getTPass());
            ptmt.setString(3, r.getAtId());
            ptmt.setTimestamp(4, r.getRTime());
            ptmt.setInt(5, r.getNo());
            int rq = ptmt.executeUpdate();
            System.out.println(rq + "건 완료");
            if (rq > 0) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
            if (result > 0) return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 삭제 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public List<ReservationDTO> selectatt(String id) {
        Connection conn = null;
        PreparedStatement ptmt=null;
        List<ReservationDTO> rlist=new ArrayList<>();
        try {
            conn=super.getConnection();
            String sql = "select * from reservation where atId=? and DATE(rTime) = CURDATE()";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,id);

            ResultSet rs=ptmt.executeQuery();
            while(rs.next()) {
                ReservationDTO reservation = ReservationDTO.builder()
                        .no(rs.getInt("no"))
                        .mId(rs.getString("mId"))
                        .tPass(rs.getString("tPass"))
                        .atId(rs.getString("atId"))
                        .rTime(rs.getTimestamp("rTime"))
                        .build();
                rlist.add(reservation);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                ptmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rlist;
    }

    public List<ReservationDTO> selectvip(String id) {
        Connection conn = null;
        PreparedStatement ptmt=null;
        List<ReservationDTO> rlist=new ArrayList<>();
        try {
            conn=super.getConnection();
            String sql = "select * from reservation r inner join ticket t on r.tpass="
                    + "t.tpass where atId=? and DATE(rTime) = CURDATE() and tname='vip'";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,id);
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()) {
                ReservationDTO reservation = ReservationDTO.builder()
                        .no(rs.getInt("no"))
                        .mId(rs.getString("mId"))
                        .tPass(rs.getString("tPass"))
                        .atId(rs.getString("atId"))
                        .rTime(rs.getTimestamp("rTime"))
                        .build();
                rlist.add(reservation);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                ptmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rlist;
    }

    public int getcount(String id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptmt = null;
        try {
            conn = super.getConnection();
            String sql = "select count(*) from reservation where mId=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, id);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 예약 수 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public boolean insertres(ReservationDTO r) {
        Connection conn = null;
        PreparedStatement ptmt=null;
        boolean flag=false;
        try {
            conn=super.getConnection();
            String sql = "insert into reservation (mId, tPass, atId, rTime)values(?,?,?,sysdate())";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,r.getMId());
            ptmt.setString(2,r.getTPass());
            ptmt.setString(3,r.getAtId());
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



}



