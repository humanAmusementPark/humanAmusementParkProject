package DAO;

import DTO.AttractionDTO;
import DTO.TimeTableDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TimeTableDAO extends SuperDAO {
    private Connection conn;

    public TimeTableDAO() throws SQLException {
        this.conn = super.getConnection();
    }


    public List<TimeTableDTO> select(){
        List<TimeTableDTO> timeTableDTOList = new ArrayList<>();
        String query = "SELECT * FROM timeTable";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TimeTableDTO timeTableDTO = TimeTableDTO .builder()
                        .tiDay(rs.getInt("tiDay"))
                        .tiTime(rs.getTime("tiTime").toLocalTime())
                        .tiContent(rs.getString("tiContent"))
                        .build();

                timeTableDTOList.add(timeTableDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return timeTableDTOList;
    }



    @Override
    public void insert() {
        super.insert();
    }

    @Override
    public void update(int choiceNum) {
        super.update(choiceNum);
    }

    @Override
    public void delete(int choiceNum) {
        super.delete(choiceNum);
    }
}
