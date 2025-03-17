package javaproject.chat.kim;

import java.io.IOException;
import java.net.ServerSocket;


public class ChatServerObject  {
   private ServerSocket[] serverSocket;

    public ChatServerObject() throws IOException {
        serverSocket = new ServerSocket[3];
        int port = 1004;
        for (int i = 0; i < 3; i++) {
            serverSocket[i] = new ServerSocket(port);
            ServerTool serverTool = new ServerTool(serverSocket[i], chatStayRoom,port);
            serverTool.start();
            port++;
        }
    }
}
