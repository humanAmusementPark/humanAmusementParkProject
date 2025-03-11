package javaproject;

import java.util.Scanner;

public class MenuAdmin {
   Scanner in = new Scanner(System.in);
    String id;

    public MenuAdmin(String id) {
        this.id = id;
        while (true) {
            System.out.println("1. 회원 조회 2. 관리자 조회 3. 예약 조회");
            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();
            sc.nextLine();
            switch (num) {
                case 1:
//                    menuMemberM();
                    break;
                case 2:
//                    menuAdminM(id);
                    break;
                case 3:
//                    menuReservation();
                    break;
                default:
                    return;
            }
        }
    }
}
