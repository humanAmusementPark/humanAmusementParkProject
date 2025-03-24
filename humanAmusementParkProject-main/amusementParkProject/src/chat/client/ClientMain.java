package javaproject.chat.client;

import java.io.IOException;
public class ClientMain {
    private static final int PORT = 4321;

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost",4321);
        client.start();
    }

}
