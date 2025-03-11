package DAO;

import DTO.EatingHouseDTO;
import DTO.MenuDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EatingHouseDAO extends SuperDAO {
    private Connection conn;

    public EatingHouseDAO() throws SQLException {
        this.conn = super.getConnection();
    }

    public Map<String, EatingHouseDTO> select() {
        Map<String, EatingHouseDTO> eatingHouseDTOMap = new HashMap<>();

        String query = "SELECT * FROM eatingHouse,menu WHERE eatingHouse.id = menu.id";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String eatingHouseName = rs.getString("eatingHouseName");
                String eatingHouseURL = rs.getString("eatingHouseURL");

                MenuDTO menuDTO = MenuDTO.builder()
                        .menuName(rs.getString("menuName"))
                        .menuPrice(rs.getInt("menuPrice"))
                        .menuURL(rs.getString("menuURL"))
                        .build();

                //기존 DTO가 없다면 새로 생성
                EatingHouseDTO eatingHouseDTO = eatingHouseDTOMap.computeIfAbsent(eatingHouseName, name ->
                        EatingHouseDTO.builder()
                                .eatingHouseName(name)
                                .eatingHouseURL(eatingHouseURL)
                                .menuDTOList(new ArrayList<>())
                                .build()
                );

                //메뉴 추가
                eatingHouseDTO.getMenuDTOList().add(menuDTO);

            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return eatingHouseDTOMap;
    }

    //    private int eatingHouseID;
//    private String eatingHouseName;
//    private String eatingHouseURL;
    @Override
    public void insert() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("1. 음식점 등록 2. 음식점 메뉴 등록");
        try {
            int choiceNum = Integer.parseInt(br.readLine());
            switch (choiceNum) {
                case 1:
                    System.out.println("음식점 이름 과 url을 작성하세요.");
                    String query = "INSERT INTO eatingHouse(eatingHouseName, eatingHouseURL) VALUES(?,?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, br.readLine());
                    stmt.setString(2, br.readLine());

                    stmt.executeUpdate();

                    break;
                case 2:
                    System.out.println("메뉴 이름, 메뉴 URL, 메뉴 가격(숫자) , 음식점ID(숫자) 입력하세요.");
                    String query2 = "INSERT INTO menu(menuName,menuURL,menuPrice) values(?,?,?)";
                    PreparedStatement stmt1 = conn.prepareStatement(query2);
                    stmt1.setString(1, br.readLine());
                    stmt1.setString(2, br.readLine());
                    stmt1.setInt(3, Integer.parseInt(br.readLine()));

                    stmt1.executeUpdate();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int choiceNum) {
//        System.out.println(" 1. 음식점 이름 2. 음식점 URL 3. 메뉴 이름 4. 메뉴 URL 5. 메뉴 가격");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        switch (choiceNum) {
            case 1:
                System.out.println("음식점 ID와 바꿀 음식점 이름을 입력하세요.");
                String query = "UPDATE eatingHouse SET eatingHouseName = ? WHERE eatingHouse.id = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query);

                    //sql 파라미터 설정
                    cursor.setInt(2, Integer.parseInt(br.readLine()));
                    cursor.setString(1, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("음식점 ID와 바꿀 음식점 URL을 입력하세요.");
                String query2 = "UPDATE eatingHouse SET eatingHouseURL = ? WHERE eatingHouse.id = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query2);

                    //sql 파라미터 설정
                    cursor.setInt(2, Integer.parseInt(br.readLine()));
                    cursor.setString(1, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                System.out.println("메뉴 ID와 바꿀 메뉴이름을 입력하세요.");
                String query3 = "UPDATE menu SET menuName = ? WHERE menu.id = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query3);

                    //sql 파라미터 설정
                    cursor.setInt(2, Integer.parseInt(br.readLine()));
                    cursor.setString(1, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                System.out.println("메뉴 ID와 바꿀 메뉴URL을 입력하세요.");
                String query4 = "UPDATE menu SET menuURL = ? WHERE menu.id = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query4);

                    //sql 파라미터 설정
                    cursor.setInt(2, Integer.parseInt(br.readLine()));
                    cursor.setString(1, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                System.out.println("메뉴 ID와 바꿀 메뉴가격을 입력하세요.");
                String query5 = "UPDATE menu SET menuPrice = ? WHERE menu.id = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query5);

                    //sql 파라미터 설정
                    cursor.setInt(2, Integer.parseInt(br.readLine()));
                    cursor.setInt(1, Integer.parseInt(br.readLine()));

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
        //이떄 cascade제약 조건떄문에 메뉴도 같이 사라짐 관련된거

        String query = "DELETE FROM eatingHouse WHERE eatingHouseID = ?";
        try {
            PreparedStatement cursor = conn.prepareStatement(query);

            cursor.setInt(1, choiceNum);
            cursor.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
