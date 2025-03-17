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
    private AtomicBoolean checkAdmin;

    public ServerTool(ServerSocket serverSocket,boolean[] flagList, int index, AtomicBoolean checkAdmin) {
        this.serverSocket = serverSocket;
        this.chatHandlerObjectList = new ArrayList<>();
        this.flag = flagList;
        this.index = index;
        this.checkAdmin = checkAdmin;
    }

    @Override
    public void run() {
        try {
            System.out.println("서버 준비 완료. 포트: " + serverSocket.getLocalPort());
            while (true) {
                //어떨떄 제한이 들어갈까
                // 관리자와 + 회원 조합일경우
                if (chatHandlerObjectList.size() >= 1) {
                    if (!checkAdmin.get()) {
                        flag[index] = false;
                        sleep(500);
                        continue;
                    }else if (chatHandlerObjectList.size() >= 2){
                        flag[index] = false;
                        sleep(500);
                        continue;
                    }
                }

                flag[index] = true;

                Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList);
                handler.start(); // 각 클라이언트 처리 스레드 시작
                chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
