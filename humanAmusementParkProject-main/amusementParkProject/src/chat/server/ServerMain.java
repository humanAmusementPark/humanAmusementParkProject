package javaproject.chat.server;

import java.io.IOException;

public class ServerMain {

    private  static final int PORT = 4321;

    public static void main(String[] args) throws IOException {
        SessionManager sessionManager = new SessionManager();

        CommandManager commandManager = new CommandManagerV2(sessionManager);

        Server server = new Server(PORT, commandManager, sessionManager);
        server.start();
    }
}
