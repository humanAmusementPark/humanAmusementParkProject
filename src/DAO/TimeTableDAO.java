package javaproject.DAO;


import javaproject.DTO.TimeTableDTO;

import javax.swing.*;
import java.sql.*;

// import junggkim.util.*;


import java.util.ArrayList;
import java.util.List;

public class TimeTableDAO extends SuperDAO implements DAOinf<TimeTableDTO> {

    @Override
    public List<TimeTableDTO> selectAll() {
        List<TimeTableDTO> timeTableDTOList = new ArrayList<>();
        String query = "SELECT * FROM timeTable";
        Connection conn = super.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TimeTableDTO timeTableDTO = TimeTableDTO.builder()
                        .tiId(rs.getString("tiId"))
                        .tiDay(rs.getInt("tiDay"))
                        .tiTime(rs.getTime("tiTime"))
                        .tiContent(rs.getString("tiContent"))
                        .build();
                timeTableDTOList.add(timeTableDTO);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 일정 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return timeTableDTOList;
    }

    @Override
    public boolean insert(TimeTableDTO timeTableDTO) {
        String query = "INSERT INTO timeTable  VALUES(?,?,?,?)";
        Connection conn = super.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, timeTableDTO.getTiId());
            stmt.setInt(2, timeTableDTO.getTiDay());
            stmt.setTime(3, timeTableDTO.getTiTime());
            stmt.setString(4, timeTableDTO.getTiContent());
            int result = stmt.executeUpdate();
            if(result == 1) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "db 일정 추가 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return false;
    }

    public void updateT(TimeTableDTO timeTableDTO, String id) {
        String query = "UPDATE timeTable SET tiId = ?, tiDay = ? , tiTime = ? , tiContent = ? WHERE tiId = ?";

        Connection conn = super.getConnection();

        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            //sql 파라미터 설정
            cursor.setString(1, timeTableDTO.getTiId());

            cursor.setInt(2, timeTableDTO.getTiDay());
            cursor.setTime(3, Time.valueOf(timeTableDTO.getTiTime().toString()));
            cursor.setString(4, timeTableDTO.getTiContent());
            cursor.setString(5, id);

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 일정 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public void deleteT(String tiId) {
        String query = "DELETE FROM timeTable WHERE tiId = ?";
        Connection conn = super.getConnection();
        try {
            PreparedStatement cursor = conn.prepareStatement(query);
            cursor.setString(1, tiId);
            cursor.executeUpdate();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "db 일정 삭제 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public TimeTableDTO select(String id) {
        return null;
    }

    @Override
    public boolean update(TimeTableDTO data) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

}