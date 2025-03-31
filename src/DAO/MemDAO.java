package javaproject.DAO;


import javaproject.DTO.MemDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemDAO extends SuperDAO implements DAOinf<MemDTO> {

    @Override
    public List<MemDTO> selectAll() {
        Connection conn = super.getConnection();
        ArrayList<MemDTO> memberList = new ArrayList<>();
        String sql = "select * from member";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MemDTO member = MemDTO.builder()
                        .mId(rs.getString("mId"))
                        .mPass(rs.getString("mPass"))
                        .mName(rs.getString("mName"))
                        .mGender(rs.getInt("mGender"))
                        .mBirth(rs.getDate("mBirth"))
                        .tPass(rs.getString("tPass"))
                        .build();
                memberList.add(member);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return memberList;
    }

    @Override
    public MemDTO select(String mId) {
        Connection conn = super.getConnection();
        String sql = "select * from member where mId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, mId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MemDTO member = MemDTO.builder()
                        .mId(rs.getString("mId"))
                        .mPass(rs.getString("mPass"))
                        .mName(rs.getString("mName"))
                        .mGender(rs.getInt("mGender"))
                        .mBirth(rs.getDate("mBirth"))
                        .tPass(rs.getString("tPass"))
                        .build();
                return member;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
    public boolean insert(MemDTO memDTO) {
        Connection conn = super.getConnection();
        String sql = "insert into member values(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memDTO.getMId());
            pstmt.setString(2, memDTO.getMPass());
            pstmt.setString(3, memDTO.getMName());
            pstmt.setInt(4, memDTO.getMGender());
            pstmt.setDate(5, Date.valueOf(memDTO.BirthToString()));
            pstmt.setString(6, memDTO.getTPass());
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
            if (result > 0) return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 입력 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
    public boolean update(MemDTO member) {
        Connection conn = super.getConnection();
        String sql = "update member set mPass = ?, mName = ?, mGender = ?, mBirth = ?, tPass = ? where mId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getMPass());
            stmt.setString(2, member.getMName());
            stmt.setInt(3, member.getMGender());
            stmt.setDate(4, Date.valueOf(member.BirthToString()));
            if(member.getTPass().isEmpty()) {
                stmt.setNull(5, Types.VARCHAR);
            }else {
                stmt.setString(5, member.getTPass());
            }
            stmt.setString(6, member.getMId());
            int result = stmt.executeUpdate();
            System.out.println(result + "건 완료");
            if (result > 0) return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
    public boolean delete(String mId) {
        Connection conn = super.getConnection();
        String sql = "delete from member where mId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, mId);
            int result = stmt.executeUpdate();
            System.out.println(result + "건 완료");
            if (result > 0) return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 삭제 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public void edit(String text, String mId) {
        Connection conn = super.getConnection();
        String sql = "update member set tPass = ? where mId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, text);
            pstmt.setString(2, mId);
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 회원 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean idPassDuplicate(int num, String id, String pw) {
        Connection conn = super.getConnection();
        String sql = null;
        switch (num) {
            case 1:
                sql = "select count(*) from administer where aId = ? and aPass = ?";
                break;
            case 2:
                sql = "select count(*) from member where mId= ? and mPass = ?";
                break;
            default:
                break;
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "id 중복 검사 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public boolean idDuplicate(String id) {
        Connection conn = super.getConnection();
        String sql = "select count(*) from member where mId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "id 중복 검사 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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
