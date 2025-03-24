package javaproject.chat.client;



import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


import static javaproject.chat.util.MyLogger.log;

public class WriteHandler implements Runnable {

    private static final String DELIMITER = "|";

    private final DataOutputStream output;
    private final Client client;
    private boolean closed = false;
    private String username;

    public WriteHandler(DataOutputStream output, Client client) {
        this.output = output;
        this.client = client;
    }


    @Override
    public void run() {
        Scanner in = new Scanner(System.in);

        try {

            while (true) {
                String toSend = in.nextLine();
                if (toSend.isEmpty()) {
                    continue;
                }
                if (toSend.equals("exit")) {
                    output.writeUTF(toSend);
                    break;
                }
                if (toSend.startsWith("/")) {
                    output.writeUTF(toSend);
                } else {
                    output.writeUTF(toSend);
                    output.flush();
                }

            }
        } catch (IOException | NoSuchElementException e) {
            log(e);
        } finally {
            client.close();
        }
    }


    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            System.in.close();
        } catch (IOException e) {
            log(e);
        }
        closed = true;
        log("writeHandler 종료");
    }

//    private static String inputUsername(Scanner in) {
//        System.out.println("이름을 입력하세요");
//        String username;
//
//        do {
//            username = in.nextLine();
//        } while (username.isEmpty());
//        return username;
//    }
}
