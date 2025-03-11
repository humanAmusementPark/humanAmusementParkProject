package javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    Scanner in = new Scanner(System.in);
    MemberDAO memberDAO;
    ManagerDAO managerDAO;
    ReservationDAO reservationDAO;
    RidesDAO ridesDAO;
    TicketDAO ticketDAO;
    LoginService loginService;

    public Login() throws SQLException {

        memberDAO = MemberDAO.getInstance();
        managerDAO = ManagerDAO.getInstance();
        reservationDAO = ReservationDAO.getInstance();
        ridesDAO = RidesDAO.getInstance();
        ticketDAO = TicketDAO.getInstance();
        loginService = LoginService.getInstance();
        System.out.println("1.회원가입  2.로그인");
        int a = in.nextInt();
        in.nextLine();
        switch (a) {
            case 1:
                newLogin();
            case 2:
                login1();
        }
    }

    public void login1() throws SQLException {
        System.out.println("1.관리자 로그인 2.회원로그인");
        int a = in.nextInt();
        in.nextLine();
        login2(a);
    }

    public void login2(int a) throws SQLException {
        System.out.println("아이디 : ");
        String id = in.nextLine();
        System.out.println("비밀번호 : ");
        String pass = in.nextLine();
        if (a == 1) {
            if (loginService.idPassDuplicate("MANAGER", "mid", id, "mPass", pass)) {
                //관리자 메뉴 들어가기
            } else {
                System.out.println("올바르지 않은 아이디 혹은 비밀번호");
            }
        } else if (a == 2) {
            if (loginService.idPassDuplicate("MEMBER", "memberID", id, "passWord", pass)) {
                //회원메뉴 들어가기
            } else {
                System.out.println("올바르지 않은 아이디 혹은 비밀번호");
            }
        }
    }


    public void newLogin() throws SQLException {
        System.out.println("1.관리자가입  2.회원용가입");
        int a = in.nextInt();
        in.nextLine();
        switch (a) {
            case 1:
                newManager();
            case 2:
                newMember();
        }
    }

    public void newManager() throws SQLException {
        System.out.println("아이디 : ");
        String id = in.nextLine();
        if (!loginService.idDuplicate("MANAGER", "mid", id)) {
            loginService.addManager1(id);
        } else {
            System.out.println("중복된 아이디입니다.");
            return;
        }
    }

    public void newMember() throws SQLException {
        System.out.println("아이디 : ");
        String id = in.nextLine();
        if (!loginService.idDuplicate("MEMBER", "memberId", id)) {
            loginService.addMember1(id);
        } else {
            System.out.println("중복된 아이디 입니다.");
        }
    }
}
