package javaproject.chat.gui;

import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JButton exitButton;
    private JButton matchButton;
    private JLabel statusLabel;
    private DataOutputStream output;
    private DataInputStream input;
    private String role;
    private String inquiryType;
    private Socket socket;
    public boolean closed = false;

    public ChatGUI(String serverAddress, int port, String role) {
        this.role = role;
        initialize(serverAddress, port);
        connectToServer(serverAddress, port);
    }

    private void initialize(String serverAddress, int port) {
        frame = new JFrame("정규랜드 고객센터");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(255, 255, 255));

        // 상단 패널 (헤더)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 229, 0)); // 카카오톡 노란색
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.setPreferredSize(new Dimension(400, 60));

        JLabel titleLabel = new JLabel("정규랜드 고객센터 [" + role + "]", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        statusLabel = new JLabel("연결 대기중입니다", SwingConstants.CENTER);
        statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        headerPanel.add(statusLabel, BorderLayout.SOUTH);

        // 입력 패널 (이름, 문의 유형, 매칭 버튼)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        // 이름 입력
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        namePanel.add(new JLabel("이름: "));
        nameField = new JTextField(15);
        nameField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        namePanel.add(nameField);
        inputPanel.add(namePanel);

        // 문의 유형 선택
        JPanel inquiryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inquiryPanel.setBackground(Color.WHITE);
        inquiryPanel.add(new JLabel("문의 유형: "));
        JRadioButton reservationButton = new JRadioButton("예약 관련 문의");
        JRadioButton lostButton = new JRadioButton("분실물 문의");
        JRadioButton otherButton = new JRadioButton("기타 문의");
        ButtonGroup inquiryGroup = new ButtonGroup();
        inquiryGroup.add(reservationButton);
        inquiryGroup.add(lostButton);
        inquiryGroup.add(otherButton);
        styleRadioButton(reservationButton);
        styleRadioButton(lostButton);
        styleRadioButton(otherButton);
        inquiryPanel.add(reservationButton);
        inquiryPanel.add(lostButton);
        inquiryPanel.add(otherButton);
        inputPanel.add(inquiryPanel);

        // 매칭 대기 버튼
        matchButton = new JButton("매칭 대기");
        matchButton.setEnabled(false);
        styleButton(matchButton, new Color(255, 199, 0), Color.BLACK, 120, 40);
        JPanel matchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        matchPanel.setBackground(Color.WHITE);
        matchPanel.add(matchButton);
        inputPanel.add(matchPanel);

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        chatArea.setBackground(new Color(245, 245, 245));
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(null);
//        chatArea.setLineWrap(true);
//        chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        messageField = new JTextField();
        messageField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        messageField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        sendButton = new JButton("전송");
        styleButton(sendButton, new Color(255, 199, 0), Color.BLACK, 60, 40);

        exitButton = new JButton("종료");
        styleButton(exitButton, new Color(200, 200, 200), Color.BLACK, 60, 40);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(sendButton);
        buttonPanel.add(exitButton);

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // 프레임에 추가
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.NORTH); // 이름, 문의 유형, 매칭 버튼 영역
        frame.add(chatScroll, BorderLayout.CENTER); // 채팅창
        frame.add(bottomPanel, BorderLayout.SOUTH); // 메시지 입력 및 버튼

        // 이벤트 리스너 추가
        matchButton.addActionListener(e -> startMatching());
        exitButton.addActionListener(e -> {
            closed = true;
            sendMessage();
        });
        reservationButton.addActionListener(e -> {
            inquiryType = "예약 관련 문의";
            checkIfReadyToMatch();
        });
        lostButton.addActionListener(e -> {
            inquiryType = "분실물 문의";
            checkIfReadyToMatch();
        });
        otherButton.addActionListener(e -> {
            inquiryType = "기타 문의";
            checkIfReadyToMatch();
        });
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closed = true;
                sendMessage(); // 서버에 종료 메시지 전송
                closeResources(); // 리소스 정리
            }
        });


        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor, int width, int height) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void styleRadioButton(JRadioButton radio) {
        radio.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        radio.setBackground(Color.WHITE);
        radio.setFocusPainted(false);
    }

    private void connectToServer(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());

            statusLabel.setText("연결되었습니다");

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
            JOptionPane.showMessageDialog(frame, "이름,문의 유형을 모두 선택하세요.");
            return;
        }

        statusLabel.setText("매칭 대기중...");
        matchButton.setEnabled(false);

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
            matchButton.setEnabled(true);
        }
    }

    private void sendMessage() {
        String name = nameField.getText().trim();
        String message = messageField.getText().trim();
        if (name.isEmpty() || role == null || inquiryType == null) {
            JOptionPane.showMessageDialog(frame, "이름, 역할, 문의 유형, 메시지를 모두 입력하세요.");
            return;
        }
        if (closed) {
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