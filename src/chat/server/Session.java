package javaproject.chat.server;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static javaproject.chat.util.MyLogger.log;
import static javaproject.chat.util.MyLogger.log1;
import static javaproject.chat.util.SocketCloseUtil.closeAll;

@Getter
@Setter
public class Session implements Runnable {
    Scanner in = new Scanner(System.in);
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final CommandManager commandManager;
    private SessionManager sessionManager;

    private boolean closed = false;

    private String username;
    private Session adminSession;
    private String role;//역할
    @Setter
    private Session matchedSession;
    private String name;
    private String type;
    private boolean flag = true;

    @SneakyThrows
    public Session(Socket socket, CommandManager commandManager, SessionManager sessionManager) {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.commandManager = commandManager;
        this.sessionManager = sessionManager;

    }

    @Override
    public void run() throws RuntimeException {
        try {


            role = input.readUTF();

            name = input.readUTF();

            type = input.readUTF();

            sessionManager.add(this);

            while (true) {
                String received = input.readUTF();
                log("클라 -> 서버 : " + received);


                if (commandManager.execute(received, this)) {
                   if(matchedSession != null) {
                       matchedSession.setFlag(true);
                       matchedSession.setMatchedSession(null);
                       sessionManager.matchCustomerToAdmin(matchedSession);

                       send("님이 퇴장하셨습니다.");
                   }
                    sessionManager.remove(this);
                    close();
                } else {
                    send(received);
                }
            }

        } catch (IOException e) {
            log(e);
        }
    }

    public void send(String message) throws IOException {
        if (matchedSession != null) {  // 상대방이 매칭되었을 때만 메세지 전송
            log("클라 - > 클라 : " + message);
            matchedSession.getOutput().writeUTF(log1() + "[" + name + "]" + message);
            matchedSession.getOutput().flush();

        } else {
            log("서버 - > 클라 : " + message);
            output.writeUTF(message);
            output.flush();

        }
    }

    public void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);

    }

    public boolean conn() {
        try {
            getOutput().writeUTF("시작");
            getOutput().flush();
            input.readUTF();
            System.out.println("연결");
            return true;
        }catch (Exception e){
            System.out.println("탈출");
        }
        return false;
    }
}
