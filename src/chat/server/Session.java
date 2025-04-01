package javaproject.chat.server;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private int badWordCnt;
    private boolean closed = false;

    private String username;
    private Session adminSession;
    private String role;//역할
    @Setter
    private Session matchedSession;
    private String name;
    private String type;
    private boolean flag = true;
    private boolean startflag = true;


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
                int command = commandManager.execute(received, this);

                if (command == 1) {
                    exit();
                } else if (command == 2) {
                    kickCustomer();
                } else if (command == 3) {
                    languageWarning(received);
                } else {
                    send(received);
                }
            }
        } catch (IOException e) {
            log(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void languageWarning(String received) throws IOException { //욕설경고
        this.badWordCnt++;
        String formattedMessage = null;
        if (commandManager instanceof CommandManagerV2) {
            ArrayList<String> badWords = ((CommandManagerV2) commandManager).getBadWords();
            for (String badWord : badWords) {
                if (received.contains(badWord)) {
                    received = received.replace(badWord, "**");
                    String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
                    formattedMessage = String.format("%s [%s]: %s", timestamp, name, received);
                }
            }
        }
        matchedSession.getOutput().writeUTF(formattedMessage);
        matchedSession.getOutput().flush();
        output.writeUTF(formattedMessage);
        output.writeUTF("[욕설경고] " + this.badWordCnt + "회\n 욕설 3회 이상시 강제퇴장입니다.");
        output.flush();

        if (this.badWordCnt >= 3) {
            try {
                matchedSession.getOutput().writeUTF(this.getName() + "을 강퇴하였습니다.");
                matchedSession.setMatchedSession(null);
                matchedSession.setFlag(true);
                matchedSession.setStartflag(true);
                sessionManager.matchCustomerToAdmin(matchedSession);
                output.writeUTF("3회 욕설로 강제종료됩니다.");
                output.writeUTF("/강퇴");
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kickCustomer() {         //강제퇴장
        if (matchedSession != null && role.equals("상담사")) {
            try {
                matchedSession.getOutput().writeUTF("====강퇴되었습니다.====");
                matchedSession.getOutput().writeUTF("/강퇴");
                matchedSession.getOutput().flush();
                matchedSession.close();
                sessionManager.remove(matchedSession);
                output.writeUTF(matchedSession.getName() + "을 강퇴하였습니다.");
                output.flush();
                this.setMatchedSession(null);
                this.setFlag(true);
                this.setStartflag(true);
                sessionManager.matchCustomerToAdmin(this);
            } catch (IOException e) {
                log("강퇴중 오류 : " + e);
            }
        }
    }

    private void exit() throws IOException, InterruptedException {      //자기  클라이언트 종료
        if (matchedSession != null) {
            matchedSession.setFlag(true);
            matchedSession.setStartflag(true);
            matchedSession.setMatchedSession(null);
            sessionManager.matchCustomerToAdmin(matchedSession);
            send("님이 퇴장하셨습니다.");

        }
        sessionManager.remove(this);
        close();
        Thread.sleep(5000);
    }


    public void send(String message) throws IOException {
        if (matchedSession != null) {  // 상대방이 매칭되었을 때만 메세지 전송
            if (startflag) {
                matchedSession.getOutput().writeUTF(log1() + "[" + name + "]" + message);
                matchedSession.getOutput().flush();
                startflag = false;
            }else {
                matchedSession.getOutput().writeUTF(log1() + "[" + name + "]" + message);
                matchedSession.getOutput().flush();
                output.writeUTF(log1() + "[" + name + "]" + message);
                output.flush();
            }

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

}
