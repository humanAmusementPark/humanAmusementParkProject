package DAO;

import DTO.AttractionDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDAO extends SuperDAO {
    private Connection conn;

    public AttractionDAO() throws SQLException {
        this.conn = super.getConnection();
    }

    @Override
    public List<AttractionDTO> selectAll() {
        List<AttractionDTO> attractionDTOList = new ArrayList<AttractionDTO>();
        String query = "SELECT * FROM attraction";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                AttractionDTO attractionDTO = AttractionDTO.builder()
                        .attractionID(rs.getInt("attractionID"))
                        .AttractionName(rs.getString("attractionName"))
                        .AttractionURL(rs.getString("attrationURL"))
                        .build();

                attractionDTOList.add(attractionDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return attractionDTOList;
    }

    @Override
    public void insert() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = "INSERT INTO attraction(attractionName, attrationURL) VALUES(?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("어트랙션 이름 , 어트랙션URL 을 입력하세요.");
            stmt.setString(1, br.readLine());
            stmt.setString(2, br.readLine());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int choiceNum) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String query;
        switch (choiceNum) {
            case 1:
                query = "UPDATE attraction SET attractionName = ? WHERE attractionID = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query);

                    //sql 파라미터 설정
                    System.out.println("수정하고 싶은 어트랙션ID와 수정할 어트랙션이름을 적으세요.");
                    cursor.setInt(1, Integer.parseInt(br.readLine()));
                    cursor.setString(2, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                query = "update attraction set attrationURL = ? where attractionID = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query);

                    //sql 파라미터 설정
                    System.out.println("수정하고 싶은 어트랙션ID와 수정할 어트랙션URL을 적으세요.");
                    cursor.setInt(1, Integer.parseInt(br.readLine()));
                    cursor.setString(2, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    @Override
    public void delete(int choiceNum) {
       String query = "DELETE FROM attraction WHERE attractionID = ?";
       try{
           PreparedStatement cursor = conn.prepareStatement(query);

           cursor.setInt(1, choiceNum);
           cursor.executeUpdate();

       }catch (Exception e){
           e.printStackTrace();
       }


    }
}
