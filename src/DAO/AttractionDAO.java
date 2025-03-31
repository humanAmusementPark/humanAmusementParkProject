package javaproject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javaproject.DTO.AttractionDTO;

import javax.swing.*;

public class AttractionDAO extends SuperDAO implements DAOinf<AttractionDTO> {
	private Connection conn = null;

	@Override
	public List<AttractionDTO> selectAll() {
		List<AttractionDTO> attractionDTOList = new ArrayList<AttractionDTO>();
		String query = "SELECT * FROM attraction";
		try {
			conn = super.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				AttractionDTO attractionDTO = AttractionDTO.builder()
						.atId(rs.getString("atId"))
						.atName(rs.getString("atName"))
						.atUrl(rs.getString("atUrl"))
						.atMax(rs.getInt("atMax"))
						.atOnoff(rs.getInt("atOnoff"))
						.build();

				attractionDTOList.add(attractionDTO);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "db 시설 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace(System.err);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		return attractionDTOList;
	}
	@Override
	public AttractionDTO select(String atId) {
		AttractionDTO a = null;
		PreparedStatement ptmt = null;
		try {
			conn = super.getConnection();
			String sql = "select * from attraction where atName=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, atId);
			ResultSet rs = ptmt.executeQuery();
			if (rs.next()) {
				a = AttractionDTO.builder()
						.atId(rs.getString("atId"))
						.atName(rs.getString("atName"))
						.atUrl(parse(rs.getString("atUrl")))
						.atMax(rs.getInt("atMax"))
						.atOnoff(rs.getInt("atOnoff"))
						.build();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@Override
	public boolean insert(AttractionDTO data) {
		return false;
	}

	private String parse(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '\\') {
				StringBuffer buf = new StringBuffer(string);
				buf.insert(i, '\\');
				string = buf.toString();
				i++;
				System.out.println(string);
			}
		}
		return string;
	}
	@Override
	public boolean update(AttractionDTO att) {
		PreparedStatement ptmt = null;
		boolean flag = false;
		try {
			conn = super.getConnection();
			String sql = "update attraction set atUrl=?, atMax=?, atOnoff=? where atId=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, att.getAtUrl());
			ptmt.setInt(2, att.getAtMax());
			ptmt.setInt(3, att.getAtOnoff());
			ptmt.setString(4, att.getAtId());
			int rs = ptmt.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "db 시설 수정 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	public String[] selectAllId() {
		ArrayList<String> list = new ArrayList<>();
		Connection conn = super.getConnection();
		String sql = "select atId from attraction where atOnoff = 1";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "db 시설 조회 쿼리 오류", "Warning", JOptionPane.WARNING_MESSAGE);
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		String[] arr = new String[list.size()];
		arr = list.toArray(arr);
		return arr;
	}


}