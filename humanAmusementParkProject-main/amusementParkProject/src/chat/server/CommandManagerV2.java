package javaproject.chat.server;

import java.io.IOException;

public class CommandManagerV2 implements CommandManager{
    private static final String DELIMITER = "\\|";
    private final SessionManager sessionManager;

    public CommandManagerV2(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public boolean execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/exit")){
//            throw new IOException("exit");
            return  true;
        }
//        sessionManager.sendAll(totalMessage);
        return false;
    }
}
