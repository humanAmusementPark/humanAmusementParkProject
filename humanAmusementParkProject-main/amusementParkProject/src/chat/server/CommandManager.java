package javaproject.chat.server;

import java.io.IOException;

public interface CommandManager {
boolean execute(String totalMessage, Session session) throws IOException;
}
