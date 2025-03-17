package javaproject.chat.kim;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;


public class ChatServerObject  {
   private ServerSocket[] serverSocket;
   private boolean[] flagList = {true,true,true};
   private AtomicBoolean checkAdmin = new AtomicBoolean(false);

    public ChatServerObject() throws IOException {
        serverSocket = new ServerSocket[3];
        int port = 1004;
        for (int i = 0; i < 3; i++) {
            serverSocket[i] = new ServerSocket(port);
            ServerTool serverTool = new ServerTool(serverSocket[i],flagList,i,checkAdmin);
            serverTool.start();
            port++;
        }
    }
    public boolean[] getFlagList() {
        return flagList;
    }
    public void setCheckAdmin(boolean checkAdmin) {
        this.checkAdmin.set(checkAdmin);
    }
}
