package javaproject.chat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static javaproject.chat.util.MyLogger.log;


public class SocketCloseUtil {

    public static void closeAll(Socket socket, InputStream input, OutputStream ouput) {
        close(socket, input);
        close(socket, ouput);
        close(socket != null, socket);
    }

    private static void close(boolean socket, Socket socket1) {
        if (socket) {
            try {
                socket1.close();
            } catch (IOException e) {
                log(e.getMessage());
            }
        }
    }

    private static void close(Socket socket, OutputStream ouput) {
        close(ouput != null, socket);
    }

    private static void close(Socket socket, InputStream input) {
        close(input != null, socket);
    }
}
