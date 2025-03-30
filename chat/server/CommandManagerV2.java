package javaproject.chat.server;

import java.io.IOException;

public class CommandManagerV2 implements CommandManager{
    private static final String DELIMITER = "\\|";
    private final SessionManager sessionManager;

    public CommandManagerV2(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public int execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/exit")){
            return  1;
        }else if(totalMessage.startsWith("/강퇴")){
            return 2;
        }

        return 0;
    }
}
