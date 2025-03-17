package javaproject.chat.kim;

import javaproject.DAO.AdminDAO;
import javaproject.DTO.AdminDTO;

public class ChatAdminister {
    private AdminDAO adminDAO;
    private  ChatServerObject chatServer;

    public ChatAdminister(ChatServerObject chatServerObject) {
        this.chatServer = chatServerObject;

        //DAO에서 관리자 정보 추출 id 받아와서
        adminDAO = new AdminDAO();
        AdminDTO adminDTO =  adminDAO.select("bbb");

        //네임에다가 임시로 포트 번호 넣어서 확인 원래는 칼럼 만들어서 해결하기
        int port = Integer.parseInt(adminDTO.getAName());

        chatServer.setCheckAdmin(true);
        new ChatClientObject().service(port, true,chatServer);
    }



}
