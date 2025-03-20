package javaproject.chat.client;

import java.io.DataInputStream;

import static util.MyLogger.log;

public class ReadHandler implements Runnable {
    private final DataInputStream input;
    private final Client client;
    private boolean closed = false;

    public ReadHandler(DataInputStream input, Client client) {
        this.input = input;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (!closed) {
                String received = input.readUTF();
                client.displayMessage(received);
            }
        } catch (Exception e) {
            log(e);
        } finally {
            client.close();
        }
    }

    public synchronized void close() {
        if (closed) return;
        closed = true;
        log("ReadHandler 종료");
    }
}

//package chat.client;
//
//
//import java.io.DataInputStream;
//import java.io.IOException;
//
//import static util.MyLogger.log;
//
//public class ReadHandler implements Runnable {
//    private final DataInputStream input;
//    private final Client client;
//    public boolean closed = false;
//
//    public ReadHandler(DataInputStream input, Client client) {
//        this.input = input;
//        this.client = client;
//    }
//
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                String received = input.readUTF();
//                if(received.equals("exit")){
//
//                }
//                System.out.println(received);
//            }
//        } catch (IOException e) {
//            log(e);
//        } finally {
//            client.close();
//        }
//    }
//
//    public synchronized void close() {
//        if (closed) {
//            return;
//        }
//        closed = true;
//        log("readHandelr 종료");
//    }
//}
