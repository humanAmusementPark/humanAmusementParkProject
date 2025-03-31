package javaproject.DAO;


import javaproject.DTO.AdminDTO;

import javax.swing.*;
import java.sql.*;
import java.util.List;

public class AdminDAO extends SuperDAO implements DAOinf<AdminDTO> {

    @Override
    public List<AdminDTO> selectAll() {
        return null;
    }

    @Override
    public AdminDTO select(String aId) {
        Connection conn = super.getConnection();
        String sql = "select * from administer where aId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                AdminDTO adminDTO = AdminDTO.builder()
                        .aId(rs.getString("aId"))
                        .aPass(rs.getString("aPass"))
                        .aName(rs.getString("aName"))
                        .aGender(rs.getInt("aGender"))
                        .aBirth(rs.getDate("aBirth"))
                        .aPosition(rs.getString("aPosition"))
                        .build();
                return adminDTO;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 관리자 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public boolean insert(AdminDTO adminDTO) {
        Connection conn = super.getConnection();
        String sql = "insert into administer values(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, adminDTO.getAId());
            pstmt.setString(2, adminDTO.getAPass());
            pstmt.setString(3, adminDTO.getAName());
            pstmt.setInt(4, adminDTO.getAGender());
            pstmt.setDate(5, Date.valueOf(adminDTO.BirthToString()));
            pstmt.setString(6, adminDTO.getAPosition());
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 관리자 입력 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean update(AdminDTO AdminDTO) {
        Connection conn = super.getConnection();
        String sql = "update administer set aPass = ?, aName = ?, aGender = ?, aBirth = ?, aPosition = ? where aId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, AdminDTO.getAPass());
            pstmt.setString(2, AdminDTO.getAName());
            pstmt.setInt(3, AdminDTO.getAGender());
            pstmt.setDate(4, Date.valueOf(AdminDTO.BirthToString()));
            pstmt.setString(5, AdminDTO.getAPosition());
            pstmt.setString(6, AdminDTO.getAId());
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
            if (result > 0) return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 관리자 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
        return false;
    }

    public boolean idDuplicate(String id) {
        Connection conn = super.getConnection();
        String sql = "select count(*) from administer where aId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("qqq");
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "id 중복 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }




}