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


        try {
            System.out.println("서버 준비 완료. 포트: " + serverSocket.getLocalPort());
            while (true) {

                System.out.println(" checkAdmin = " + checkAdmin[index]);

                //
                Socket socket = serverSocket.accept(); // 클라이언트 연결 대기
                ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList, flag, checkAdmin);
                handler.getFlag();


                System.out.println(" checkAdmin ==  " + checkAdmin[index]);

                if (!chatHandlerObjectList.isEmpty()) {
                    if (checkAdmin[index]) {  //관리자 들어올떄
                        if (chatHandlerObjectList.size() >= 2 ) {
                            System.out.println("dddddddddd");
                            socket.close();
                            continue;
                        }
                    }else {  //회원들어올떄
                        System.out.println(" aaaaaaaaaaa" );
                        socket.close();
                        continue;
                    }
                }

                handler.start();
                // 각 클라이언트 처리 스레드 시작
                chatHandlerObjectList.add(handler); // 클라이언트 핸들러를 리스트에 추가
                sleep(500);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
