package javaproject.chat.server;

import java.io.IOException;

public interface CommandManager {
    int execute(String totalMessage, Session session) throws IOException;
}
