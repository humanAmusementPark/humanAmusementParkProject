package javaproject.chat.kim;

import java.io.IOException;
import java.util.Scanner;

public class chatMain {
    public static void main(String[] args) throws IOException {
        //서버는 처음 맵부분에 들어가기 시작할떄 만들어야 한다.
        ChatServerObject chatServerObject = new  ChatServerObject();

//        String id = "bbb";
//
//        new ChatStayRoom(chatServerObject,id);
//
//        //관리자
////        new ChatAdminister(chatServerObject);
//
//        //ChatAdminister chatAdminister = new ChatAdminister(chatServerObject);
//        // 서버는 백그라운드에서 계속 실행 중입니다.
//        // 관리자를 실행시키는 루프
//        String[] idList = {"aaa","A1001","ccc","d"};
//        int i = 0;
//        while (true) {
//            // 관리자 실행
//            new ChatAdminister(chatServerObject,idList[i++]);  // 채팅 관리자 화면
//
//            // ChatAdminister 종료 후 사용자에게 다시 실행 여부 묻기
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("관리자 프로그램을 다시 실행하시겠습니까? (y/n): ");
//            String response = scanner.nextLine();
//
//            // "y" 입력 시 다시 실행, "n" 입력 시 종료
//            if (!response.equalsIgnoreCase("y")) {
//                System.out.println("프로그램을 종료합니다.");
//                break;  // "n"을 입력하면 프로그램 종료
//            }
//        }


    }
}
