package javaproject.chat.gui;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatGUI {

    private JFrame frame;
    private JTextField nameField;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private  JButton exitButton;
    private JButton matchButton; // 매칭 대기 버튼
    private JLabel statusLabel; // 연결 상태 표시용 레이블
    private DataOutputStream output;
    private DataInputStream input;
    private String role;
    private String inquiryType;
    private Socket socket;
    public boolean closed = false;

    public ChatGUI(String serverAddress, int port) {
        initialize(serverAddress, port);
        // 서버와 연결
        connectToServer(serverAddress, port);
    }

    private void initialize(String serverAddress, int port) {
        frame = new JFrame("정규랜드 고객센터");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(6, 1));

        // 이름 입력 필드
        JPanel namePanel = new JPanel();
        nameField = new JTextField(15);
        namePanel.add(new JLabel("이름: "));
        namePanel.add(nameField);
        topPanel.add(namePanel);

        // 역할 선택 버튼 (고객 / 상담사)
        JPanel rolePanel = new JPanel();
        JRadioButton customerButton = new JRadioButton("고객");
        JRadioButton adminButton = new JRadioButton("상담사");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(customerButton);
        roleGroup.add(adminButton);
        rolePanel.add(new JLabel("역할 선택: "));
        rolePanel.add(customerButton);
        rolePanel.add(adminButton);
        topPanel.add(rolePanel);

        // 상담 종류 선택 버튼 (예약 관련 문의, 분실물 문의, 기타 문의)
        JPanel inquiryPanel = new JPanel();
        JRadioButton reservationButton = new JRadioButton("예약 관련 문의");
        JRadioButton lostButton = new JRadioButton("분실물 문의");
        JRadioButton otherButton = new JRadioButton("기타 문의");
        ButtonGroup inquiryGroup = new ButtonGroup();
        inquiryGroup.add(reservationButton);
        inquiryGroup.add(lostButton);
        inquiryGroup.add(otherButton);
        inquiryPanel.add(new JLabel("문의 유형: "));
        inquiryPanel.add(reservationButton);
        inquiryPanel.add(lostButton);
        inquiryPanel.add(otherButton);
        topPanel.add(inquiryPanel);

        // 매칭 대기 버튼
        matchButton = new JButton("매칭 대기");
        matchButton.setEnabled(false); // 처음에는 비활성화
        matchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatGUI.this.startMatching();
            }
        });
        topPanel.add(matchButton);

        // 상태 표시 (연결 상태)
        statusLabel = new JLabel("연결 대기중입니다", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(statusLabel, BorderLayout.NORTH);

        frame.add(topPanel, BorderLayout.NORTH);

        // 채팅 창
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 메시지 입력 및 전송
        JPanel bottomPanel = new JPanel();
        messageField = new JTextField(27);
        sendButton = new JButton("전송");
        exitButton = new JButton("종료");
        bottomPanel.add(messageField);
        bottomPanel.add(sendButton);
        bottomPanel.add(exitButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closed = true;
                ChatGUI.this.sendMessage();
            }
        });

        // 버튼 이벤트 리스너 설정
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                role = "고객";
                ChatGUI.this.checkIfReadyToMatch();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                role = "상담사";
                ChatGUI.this.checkIfReadyToMatch();
            }
        });

        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inquiryType = "예약 관련 문의";
                ChatGUI.this.checkIfReadyToMatch();
            }
        });

        lostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inquiryType = "분실물 문의";
                ChatGUI.this.checkIfReadyToMatch();
            }
        });

        otherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inquiryType = "기타 문의";
                ChatGUI.this.checkIfReadyToMatch();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatGUI.this.sendMessage();
            }
        });

        // 엔터로 메시지 전송
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatGUI.this.sendMessage();
            }
        });


        frame.setVisible(true);
    }



    private void connectToServer(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());


            statusLabel.setText("연결되었습니다");

            // 스레드 시작
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    try {
                        while (true) {
                            String message = input.readUTF();
                            chatArea.append(message + "\n");
                        }
                    } catch (EOFException e) {
                        System.out.println("예외 : " + e);
                        statusLabel.setText("서버 연결 끊어짐");
                    } catch (IOException e) {
                        System.out.println("예외 발생: " + e);
                        statusLabel.setText("네트워크 오류 발생");
                    } finally {
                        closeResources();
                    }
                }
            }).start();


        } catch (IOException e) {
            statusLabel.setText("서버 연결 실패");
            e.printStackTrace();
        }
    }

    private void closeResources() {
        try {
            if (input != null) input.close();

        } catch (IOException e) {
            System.out.println("input 닫기 실패");
            e.printStackTrace();
        }

        try {
            if (output != null) output.close();
        } catch (IOException e) {
            System.out.println("output 닫기 실패");
            e.printStackTrace();
        }

        try {
            if (socket != null) socket.close();
            System.out.println("성공이요");
        } catch (IOException e) {
            System.out.println("socket 닫기 실패");
            e.printStackTrace();
        }
    }

    private void startMatching() {
        String name = nameField.getText().trim();
        if (name.isEmpty() || role == null || inquiryType == null) {
            JOptionPane.showMessageDialog(frame, "이름, 역할, 문의 유형을 모두 선택하세요.");
            return;
        }

        statusLabel.setText("매칭 대기중...");
        matchButton.setEnabled(false); // 매칭 대기 중에는 버튼 비활성화

        // 역할, 이름, 문의 유형을 서버로 전송하여 매칭 대기 상태를 설정
        try {

            output.writeUTF(role);
            output.flush();
            Thread.sleep(50);
            output.writeUTF(name);
            output.flush();
            Thread.sleep(50);
            output.writeUTF(inquiryType);
            output.flush();


        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "서버 전송 실패");
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIfReadyToMatch() {
        if (role != null && inquiryType != null) {
            matchButton.setEnabled(true); // 역할과 문의 유형이 모두 선택되면 매칭 대기 버튼 활성화
        }

    }


    private void sendMessage() {
        String name = nameField.getText().trim();
        String message = messageField.getText().trim();
        if (name.isEmpty() || role == null || inquiryType == null) {
            JOptionPane.showMessageDialog(frame, "이름, 역할, 문의 유형, 메시지를 모두 입력하세요.");
            return;
        }
        if(closed){
            message = "/exit";
        }

        String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
        String formattedMessage = String.format("%s [%s]: %s", timestamp, name, message);

        chatArea.append(formattedMessage + "\n");
        messageField.setText("");

        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}




