package javaproject.chat.gui;

import chat.server.CommandManager;
import chat.server.Session;
import chat.server.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class MainFrame extends JFrame {
    private JButton btnCustomer;
    private JButton btnAdmin;
    private JTextArea textArea;
    private JTextField textField;
    private SessionManager sessionManager;

    public MainFrame() {
        sessionManager = new SessionManager(); // 세션 관리 객체 생성
        setTitle("상담 시스템");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 텍스트 영역과 텍스트 필드 설정
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textField = new JTextField(30);
        textField.setEditable(true);

        // 상담 시작 버튼 클릭 시
        btnCustomer = new JButton("고객으로 로그인");
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSession("고객");
            }
        });

        // 상담사 로그인 버튼 클릭 시
        btnAdmin = new JButton("상담사로 로그인");
        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSession("상담사");
            }
        });

        // 메시지 전송 버튼
        JButton sendButton = new JButton("메시지 보내기");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 레이아웃 구성
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(textField, BorderLayout.SOUTH);
        panel.add(sendButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCustomer);
        buttonPanel.add(btnAdmin);

        add(buttonPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void startSession(String role) {
        // 세션 시작
        Session session = new Session(new Socket(), new CommandManager() {
            @Override
            public boolean execute(String message, Session session) throws IOException {
                // 명령을 실행하는 로직을 구현
                // 예시: 메시지가 "quit"이면 세션을 종료하는 로직
                if ("quit".equalsIgnoreCase(message)) {
                    session.send("세션을 종료합니다.");
                    session.close();
                    return true;  // 성공적으로 처리된 경우
                }

                // 다른 메시지에 대해서는 처리한 후 true를 반환
                System.out.println("실행된 명령: " + message);
                return false;  // 실패 또는 처리되지 않은 명령
            }
        }, sessionManager);

        session.setRole(role);

        // 세션을 스레드로 실행
        new Thread(session).start();
        textArea.append(role + "로 로그인 성공!\n");
    }


    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            textArea.append("보낸 메시지: " + message + "\n");
            // 실제로 Session 객체에 메시지를 전달하는 로직이 들어가야 함
            // 예: session.send(message);
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
