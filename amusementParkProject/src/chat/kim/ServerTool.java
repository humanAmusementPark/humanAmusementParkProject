package javaproject.chat.kim;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTool extends Thread {
    private ServerSocket serverSocket;
    private List<ChatHandlerObject> chatHandlerObjectList;
    private ChatStayRoom chatStayRoom;
    private int port;

    public ServerTool(ServerSocket serverSocket, ChatStayRoom chatStayRoom, int port) {
        this.serverSocket = serverSocket;
        this.chatHandlerObjectList = new ArrayList<>();
        this.chatStayRoom = chatStayRoom;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            System.out.println("서버 준비 완료. 포트: " + serverSocket.getLocalPort());
            while (true) {
                if (chatHandlerObjectList.size() >= 2) {
                    chatStayRoom.setFlag(port,false);
                    sleep(500);
                    continue;
                }

                chatStayRoom.setFlag(port,true);

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
