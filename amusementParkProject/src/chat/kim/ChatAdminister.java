package javaproject.chat.kim;

import javaproject.DAO.AdminDAO;
import javaproject.DTO.AdminDTO;

public class ChatAdminister {
    private AdminDAO adminDAO;
    public ChatAdminister() {
        //DAO에서 관리자 정보 추출 id 받아와서
        adminDAO = new AdminDAO();
        AdminDTO adminDTO =  adminDAO.select("bbb");

        //네임에다가 임시로 포트 번호 넣어서 확인 원래는 칼럼 만들어서 해결하기
        int port = Integer.parseInt(adminDTO.getAName());

        new ChatClientObject().service(port);
    }




    public static void main(String[] args) {
        new ChatAdminister();
    }
}
