package javaproject.chat.kim;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerTool extends Thread {
    private ServerSocket serverSocket;
    private List<ChatHandlerObject> chatHandlerObjectList;
    private boolean[] flag;
    private int index;
    private boolean[] checkAdmin;

    public ServerTool(ServerSocket serverSocket,boolean[] flagList, int index, boolean[] checkAdmin) {
        this.serverSocket = serverSocket;
        this.chatHandlerObjectList = new ArrayList<>();
        this.flag = flagList;
        this.index = index;
        this.checkAdmin = checkAdmin;
    }

    @Override
    public void run() {

       // boolean first = true;

        try {
            System.out.println("서버 준비 완료. 포트: " + serverSocket.getLocalPort());
            while (true) {

                //내가 원하는 조건?
                //1. 회원한명 만 들어와 있는 경우
                //2. 관리자만 들어와 있는 경우
                //3. 관리자 + 회원 이 있는 경우
                // 관리자와 + 회원 조합일경우

                System.out.println(" index = " + index + " flag = " + flag[index]);
                System.out.println(" 리스트 사이즈 = " + chatHandlerObjectList.size());
                //회원을 무조건 한명만
//                if(!checkAdmin[index]) {  // 관리자가 없을경우 회원 무조건 한명만 오도록
//                    if (chatHandlerObjectList.size() >= 1 ) {
//                        if (first){
//                            first = false;
//                        }else {
//                            flag[index] = false;
//                            System.out.println("첫번쨰 조건");
//                            sleep(500);
//                            continue;
//                        }
//                    }
//                }else{ //관리자가 있을 경우
//                    if  (chatHandlerObjectList.size() >= 2) {
//                        flag[index] = false;
//                        System.out.println(" 두번째 조건 ");
//                        sleep(500);
//                        continue;
//                    }
//                }
                //1. 회원이 먼저 들어온경우 2. 상담사가 먼저 들어온경우 3.
                if (!checkAdmin[index]) { //상담사가 없는경우
                    if (chatHandlerObjectList.isEmpty()) {

                        Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                        System.out.println(" flag = " + flag[index]);

                        ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag,checkAdmin);
                        handler.start(); // 각 클라이언트 처리 스레드 시작
                        chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
                        System.out.println("chekckdkdkdkdkdkk");
                        sleep(5000);
                    }else{
                        flag[index] = false;

                    }
                }else{    //상담사가 있는경우
                    if (chatHandlerObjectList.size() < 2){
                        Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                        System.out.println(" flag = " + flag[index]);

                        ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag,checkAdmin);
                        handler.start(); // 각 클라이언트 처리 스레드 시작
                        chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
                        System.out.println("22222222222");
                        sleep(5000);

                    }else{
                        flag[index] = false;

                    }
                }




            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
