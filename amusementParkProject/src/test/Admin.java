package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Admin {
    private String managerId;
    private ManagerAdmin managerAdmin;
    public Admin() throws IOException, SQLException {
        boolean flag = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (flag) {
            System.out.println("1. 회원 로그인 2. 관리자 로그인 3. 종료");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:

                    break;
                case 2:
                    this.managerId = managerLogin();
                    if (managerId != null) {
                        managerAdmin = new ManagerAdmin(managerId);
                        managerAdmin.menu();
                    }
                    break;
                case 3:
                    flag = false;
                    break;
            }
        }
    }

    private String managerLogin() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("아이디 비번을 입력하세요.");
        return br.readLine();
    }
}
