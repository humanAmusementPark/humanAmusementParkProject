package javaproject.DAO;

import javaproject.DTO.TicketDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO extends SuperDAO implements DAOinf<TicketDTO> {
    private static TicketDAO tinstance;
    private List<TicketDTO> ticketList = new ArrayList<>(); //여기 추가요12!@!@!@!@!@!@


    public TicketDAO() {
        loadTickets(); //여기추가요!@!@!@
    }

    public static TicketDAO getInstance() { //여기 추가했어요!@!@!@
        try {
            if (tinstance == null) {
                tinstance = new TicketDAO();
            }
            return tinstance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tinstance;
    }

    public List<TicketDTO> getTicketList() {
        return new ArrayList<>(ticketList); // 리스트 복사하여 반환
    }

    private void loadTickets() { //여기 추가요!@!@!@!@!@!@!@!@!@!@
        Connection conn = super.getConnection();
        ticketList.clear();
        String sql = " SELECT * FROM ticket";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tPass = rs.getString("tPass");
                String tName = rs.getString("tName");
                int tPrice = rs.getInt("tPrice");
                ticketList.add(TicketDTO.builder().
                        tPass(tPass)
                        .tName(tName)
                        .tPrice(tPrice)
                        .build());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "db 티켓 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public TicketDTO select(String id) {
        Connection conn = super.getConnection();
        String query = "SELECT * FROM ticket where tPass='" + id + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TicketDTO ticketDTO = TicketDTO.builder()
                        .tPass(rs.getString("tPass"))
                        .tName(rs.getString("tName"))
                        .tPrice(rs.getInt("tPrice"))
                        .build();
                return ticketDTO;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 티켓 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    public List<TicketDTO> selectAll() {
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        String query = "SELECT * FROM ticket";
        Connection conn = super.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TicketDTO ticketDTO = TicketDTO.builder()
                        .tPass(rs.getString("tPass"))
                        .tName(rs.getString("tName"))
                        .tPrice(rs.getInt("tPrice"))
                        .build();
                ticketDTOList.add(ticketDTO);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 티켓 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return ticketDTOList;
    }

    @Override
    public boolean insert(TicketDTO ticketDTO) {
        String query = "INSERT INTO ticket  VALUES(?,?,?)";
        Connection conn = super.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ticketDTO.getTPass());
            stmt.setString(2, ticketDTO.getTName());
            stmt.setInt(3, ticketDTO.getTPrice());
            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "db 티켓 입력 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    @Override
    public boolean delete(String tPass) {
        String query = "DELETE FROM ticket WHERE tPass = ?";
        Connection conn = super.getConnection();
        try {
            PreparedStatement cursor = conn.prepareStatement(query);
            cursor.setString(1, tPass);
            int result = cursor.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "db 티켓 삭제 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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

    @Override
    public boolean update(TicketDTO ticketDTO) {
        String query = "UPDATE ticket SET tName = ? , tPrice = ?  WHERE tPass = ?";
        Connection conn = super.getConnection();
        try {
            PreparedStatement cursor = conn.prepareStatement(query);
            cursor.setString(1, ticketDTO.getTName());
            cursor.setInt(2, ticketDTO.getTPrice());
            cursor.setString(3, ticketDTO.getTPass());
            //sql 실행
            int result = cursor.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "db 티켓 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
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


}