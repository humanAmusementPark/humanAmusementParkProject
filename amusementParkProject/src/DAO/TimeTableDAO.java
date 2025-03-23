package javaproject.DAO;


import javaproject.DTO.TimeTableDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

// import junggkim.util.*;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeTableDAO extends SuperDAO {

    public List<TimeTableDTO> select(){
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


    public void insert(TimeTableDTO timeTableDTO) {
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


    public void update(TimeTableDTO timeTableDTO, String id) {
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


    public void delete(String tiId) {
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
}
