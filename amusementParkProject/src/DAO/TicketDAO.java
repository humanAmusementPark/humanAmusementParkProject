package javaproject.DAO;

import javaproject.DTO.TicketDTO;
import javaproject.DTO.TimeTableDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO extends SuperDAO {
    private Connection conn;

    public TicketDAO() throws SQLException {
        this.conn = super.getConnection();
    }

    public List<TicketDTO> select(){
        List<TicketDTO> ticketDTOList = new ArrayList<>();

        String query = "SELECT * FROM ticket";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TicketDTO ticketDTO = TicketDTO .builder()
                        .tPass(rs.getString("tPass"))
                        .tName(rs.getString("tName"))
                        .tPrice(rs.getInt("tPrice"))
                        .build();

                ticketDTOList.add(ticketDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return ticketDTOList;
    }

    public void insert(TicketDTO ticketDTO){
        String query = "INSERT INTO ticket  VALUES(?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, ticketDTO.getTPass());
            stmt.setString(2, ticketDTO.getTName());
            stmt.setInt(3,ticketDTO.getTPrice());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String tPass){
        String query = "DELETE FROM ticket WHERE tPass = ?";


        try{
            PreparedStatement cursor = conn.prepareStatement(query);

            cursor.setString(1, tPass);
            cursor.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(TicketDTO ticketDTO,String tPass){
        String query = "UPDATE ticket SET tPass = ?, tName = ? , tPrice = ?  WHERE tPass = ?";

        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            //sql 파라미터 설정
            cursor.setString(1, ticketDTO.getTPass());
            cursor.setString(2, ticketDTO.getTName());
            cursor.setInt(3, ticketDTO.getTPrice());
            cursor.setString(4, tPass);

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
