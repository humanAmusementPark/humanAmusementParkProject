package javaproject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationService {
    Scanner in = new Scanner(System.in);

    ReservationDAO rDAO = new ReservationDAO();
    TicketDAO tDAO = new TicketDAO();

    public ReservationService(MemberDTO memberDTO) throws SQLException {
        if (tDAO.checkTicket(memberDTO.getMemberID())) {

        } else {
            normalprt();
            magicprt();
            int a = in.nextInt();
            in.nextLine();
            switch (a){
                case 1: tDAO.normalTicket(memberDTO.getMemberID());
                case 2: tDAO.magicTicket(memberDTO.getMemberID());
            }
        }
    }
    public void normalprt(){
        System.out.println("1.-----일반권-----");
        System.out.println("  놀이기구 자유이용권");
        System.out.println("  30000원  ");
        System.out.println("  ---------------");
    }
    public void magicprt(){
        System.out.println("2.-----매직패스-----");
        System.out.println("  놀이기구 자유이용권(프리패스)");
        System.out.println("  60000원  ");
        System.out.println("  ---------------");
    }


}
