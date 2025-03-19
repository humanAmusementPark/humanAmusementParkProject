package javaproject.chat.kim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ServerTool(ServerSocket serverSocket, boolean[] flagList, int index, boolean[] checkAdmin) {
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
                System.out.println(" checkAdmin = " + checkAdmin[index]);

                //
                System.out.println("socket 이전 ");
                Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                System.out.println(" flag = " + flag[index]);
                if (chatHandlerObjectList.size() >= 1) {
                    if (checkAdmin[index]) {
                        if (chatHandlerObjectList.size() >= 2) {
                            socket.close();
                            continue;
                        }
                    }else{
                        socket.close();
                        continue;
                    }
                }
                ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag, checkAdmin);
                handler.start(); // 각 클라이언트 처리 스레드 시작
                chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
                System.out.println("클라이언트 연결됨: " + socket.getInetAddress());
                sleep(500);


//                if (!checkAdmin[index]) {  //관리자 없는 경우
//                    System.out.println("socket 이전 ");
//                    Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
//                    System.out.println(" flag = " + flag[index]);
//
//                    if (!chatHandlerObjectList.isEmpty()) {
//                        socket.close();
//                        continue;
//                    }
//                    ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag, checkAdmin);
//                    handler.start(); // 각 클라이언트 처리 스레드 시작
//                    chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
//                    System.out.println("클라이언트 연결됨: " + socket.getInetAddress());
//                    sleep(500);
//
//
//                } else {  //관리자 있는경우
//                    System.out.println("관리자가 있는걸로 체크되어 조건문에 들어왔다.");
//
//                    Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
//                    if (chatHandlerObjectList.size() >= 2) {
//                        socket.close();
//                        continue;
//                    }
//                    ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag, checkAdmin);
//                    handler.start(); // 각 클라이언트 처리 스레드 시작
//                    chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
//                    sleep(500);
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
