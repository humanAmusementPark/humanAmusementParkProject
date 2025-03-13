package test;

import DAO.AttractionDAO;
import DAO.EatingHouseDAO;
import DTO.AttractionDTO;
import DTO.EatingHouseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerAdmin {
    private String managerId;
    private List<AttractionDTO> attractionDTOList;
    private Map<String, EatingHouseDTO> eatingHouseDTOMap;
    private AttractionDAO attractionDAO = new AttractionDAO();
    private EatingHouseDAO eatingHouseDAO = new EatingHouseDAO();

    public ManagerAdmin(String managerId) throws SQLException {
        this.managerId = managerId;
        this.attractionDTOList = attractionDAO.selectAll();
    }

    public void menu() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean flag = true;
        while (flag) {
            System.out.println("1. 회원 정보 2. 시설 관리 3. 종료 ");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:
                    break;
                case 2:
                    System.out.println("1. 놀이기구 2. 음식점");
                    int choice2 = Integer.parseInt(br.readLine());
                    switch (choice2) {
                        case 1:
                            attracionMenu();
                            break;
                        case 2:
                            eatingHouseMenu();
                            break;
                    }
                    break;
                case 3:
                    flag = false;
                    break;
            }
        }

    }

    public void attracionMenu() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        boolean flag = true;
        while (flag) {
            System.out.println("1.조회 2. 등록 3. 수정 4. 삭제 5.종료 ");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:
                    this.attractionDTOList = attractionDAO.selectAll();
                    attractionDTOList.stream().forEach(attractionDTO -> {
                        System.out.println("놀이기구 이름 = " + attractionDTO.getAttractionName());
                        System.out.println("놀이기구 URL = " + attractionDTO.getAttractionURL());
                    });
                    break;
                case 2:
                    attractionInsert();

                    break;
                case 3:
                    attractionUpdate();
                    break;
                case 4:
                    attrationDelete();

                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }

    public void prt(int choice) throws IOException, SQLException {
        //attration prt
        if (choice == 1) {
            attractionDTOList.stream().forEach(attractionDTO -> {
                System.out.println("getAttractionID() = " + attractionDTO.getAttractionID());
                System.out.println(attractionDTO.getAttractionName());
                System.out.println(attractionDTO.getAttractionURL());
            });
        }else if (choice == 2) {     //facilities prt
            eatingHouseDTOMap.forEach((name, dto) -> {
                System.out.println("식당 이름: " + dto.getEatingHouseName());
                System.out.println("식당 URL: " + dto.getEatingHouseURL());
                System.out.println("메뉴 목록:");

                dto.getMenuDTOList().forEach(menu ->
                        System.out.println(" - 메뉴 이름: " + menu.getMenuName() + ", 가격: " + menu.getMenuPrice())
                );
            });
        }
    }


    public void attrationDelete() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        prt(1);
        System.out.println("삭제하고 싶은 어트랙션ID를 입력하세요.");
        int choice = Integer.parseInt(br.readLine());
        attractionDAO.delete(choice);
    }

    public void attractionInsert() throws IOException, SQLException {
        attractionDAO.insert();
    }

    public void attractionUpdate() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       //우선 한번 출력
        prt(1);

        System.out.println("수정하고 싶은 것을 고르세요.");
        System.out.println(" 1. 어트랙션 이름 2. 어트랙션 URL");
        int choiceNum = Integer.parseInt(br.readLine());

        attractionDAO.update(choiceNum);
    }

    public void eatingHouseMenu() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean flag = true;
        while (flag) {
            System.out.println("1.조회 2. 등록 3. 수정 4. 삭제 5.종료 ");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:
                    this.eatingHouseDTOMap = eatingHouseDAO.select();
                    break;
                case 2:
                    eatingHouseInsert();
                    break;
                case 3:
                    eatingHouseUpdate();
                    break;
                case 4:
                    eatingHouseDelete();
                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }
    public void eatingHouseInsert() throws IOException, SQLException {

        prt(2);
        eatingHouseDAO.insert();
    }

    public void eatingHouseDelete() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        prt(2);
        System.out.println(" 삭제하고 싶은 음식점 id을 입력하세요.");
        eatingHouseDAO.delete(Integer.parseInt(br.readLine()));
    }

    public void eatingHouseUpdate() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        prt(2);
        System.out.println("수정하고 싶은 것을 고르세요.");
        System.out.println(" 1. 음식점 이름 2. 음식점 URL 3. 메뉴 이름 4. 메뉴 URL 5. 메뉴 가격");
        int choice2 = Integer.parseInt(br.readLine());
        eatingHouseDAO.update(choice2);
    }


}
