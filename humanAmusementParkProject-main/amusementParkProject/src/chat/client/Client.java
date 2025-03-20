package javaproject.chat.client;

import chat.client.ClientGUI;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static util.MyLogger.log;

@Getter
public class Client {
    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;
    private ClientGUI gui;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.gui = new ClientGUI(this);
    }

    public void start() {
        try {
            log("클라이언트 시작");
            socket = new Socket(host, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            readHandler = new ReadHandler(input, this);
            writeHandler = new WriteHandler(output, this);

            Thread readThread = new Thread(readHandler);
            Thread writeThread = new Thread(writeHandler);
            readThread.start();
            writeThread.start();

            gui.setVisible(true);
        } catch (Exception e) {
            log(e);
        }
    }

    public void displayMessage(String message) {
        gui.appendMessage(message);
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        writeHandler.close();
        readHandler.close();
        try {
            if (socket != null) socket.close();
            if (input != null) input.close();
            if (output != null) output.close();
        } catch (Exception e) {
            log(e);
        }
        closed = true;
        log("연결종료");
    }
}
//public class Client {
//
//    private final String host;
//    private final int port;
//
//    private Socket socket;
//    private DataInputStream input;
//    private DataOutputStream output;
//
//    private ReadHandler readHandler;
//    private WriteHandler writeHandler;
//    private boolean closed = false;
//
//    public Client(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//    @SneakyThrows
//    public void start(){
//        log("클라 시작");
//        socket = new Socket (host,port);
//        input = new DataInputStream(socket.getInputStream());
//        output = new DataOutputStream(socket.getOutputStream());
//
//        readHandler = new ReadHandler(input,this);
//        writeHandler = new WriteHandler(output,this);
//
//        Thread readThread = new Thread(readHandler, "readHandler");
//        Thread writeThread = new Thread(writeHandler, "writeHandler");
//        readThread.start();
//        writeThread.start();
//
//    }
//
//    public synchronized void close() {
//        if(closed){
//            return;
//        }
//        writeHandler.close();
//        readHandler.close();
//        closeAll(socket,input,output);
//        closed = true;
//        log("연결종료");
//    }



