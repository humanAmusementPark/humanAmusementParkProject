package javaproject.chat.kim;

import java.io.IOException;

public class chatMain {
    public static void main(String[] args) throws IOException {

        ChatServerObject chatServerObject = new  ChatServerObject();
        ChatStayRoom chatstayRoom = new ChatStayRoom(chatServerObject);
    }
}
