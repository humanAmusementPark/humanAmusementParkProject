package DAO;

import DTO.AttractionDTO;
import DTO.FacilitiesDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacilitiesDAO extends SuperDAO {
    private Connection conn;

    public FacilitiesDAO() throws SQLException {
        this.conn = super.getConnection();
    }
    @Override
    public List<FacilitiesDTO> selectAll() {
        List<FacilitiesDTO> facilitiesDTOList = new ArrayList<>();

        String query = "SELECT * FROM facilities, menu WHERE facilities_id = menu.facilities_id";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                FacilitiesDTO facilitiesDTO = FacilitiesDTO.builder()
                        .facilityID(rs.getInt("facilitiesID"))
                        .facilityName(rs.getString("facilitiesName"))
                        .facilityURL(rs.getString("facilitiesURL"))
                        .facilitiesMenuDTOList(new ArrayList<>())
                        .build();

                facilitiesDTOList.add(facilitiesDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return facilitiesDTOList;
    }

    @Override
    public void insert() {

    }

    @Override
    public void update(int choiceNum) {

    }

    @Override
    public void delete(int choiceNum) {

    }
}
