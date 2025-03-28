package javaproject.DAO;


import javaproject.DTO.TicketDTO;
import javaproject.DTO.TimeTableDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

// import junggkim.util.*;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeTableDAO extends SuperDAO implements DAOinf<TimeTableDTO> {

    public List<TimeTableDTO> selectT(){
        List<TimeTableDTO> timeTableDTOList = new ArrayList<>();
        String query = "SELECT * FROM timeTable";
        Connection conn = super.getConnection();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TimeTableDTO timeTableDTO = TimeTableDTO .builder()
                        .tiId(rs.getString("tiId"))
                        .tiDay(rs.getInt("tiDay"))
                        .tiTime(rs.getTime("tiTime"))
                        .tiContent(rs.getString("tiContent"))
                        .build();

                timeTableDTOList.add(timeTableDTO);
            }
        } catch (SQLException e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace(System.err);
        }
        return timeTableDTOList;
    }


    public void insertT(TimeTableDTO timeTableDTO) {
        String query = "INSERT INTO timeTable  VALUES(?,?,?,?)";

        Connection conn = super.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, timeTableDTO.getTiId());
            stmt.setInt(2, timeTableDTO.getTiDay());
            stmt.setTime(3,timeTableDTO.getTiTime());
            stmt.setString(4, timeTableDTO.getTiContent());

            stmt.executeUpdate();

        } catch (Exception e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
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
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }


    public void deleteT(String tiId) {
        String query = "DELETE FROM timeTable WHERE tiId = ?";

        Connection conn = super.getConnection();

        try{
            PreparedStatement cursor = conn.prepareStatement(query);

            cursor.setString(1, tiId);
            cursor.executeUpdate();

        }catch (Exception e){
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeTableDTO> selectAll() {
        return null;
    }

    @Override
    public TimeTableDTO select(String id) {
        return null;
    }

    @Override
    public boolean insert(TimeTableDTO data) {
        return false;
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