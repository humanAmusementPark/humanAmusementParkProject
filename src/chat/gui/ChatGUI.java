package javaproject.chat.gui;

import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Setter
public class ChatGUI {
    private JRadioButton reservationButton;
    private JRadioButton lostButton;
    private JRadioButton otherButton;

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
    private ArrayList<String> badWords = new ArrayList<>();//욕설 단어 저장


    public ChatGUI(String serverAddress, int port, String role) {
        this.role = role;
        initialize(serverAddress, port);
        connectToServer(serverAddress, port);
        loadBadWordsFile("src/badwords.txt");
    }

    private void loadBadWordsFile(String filename) { //욕설 파일 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addBadWord(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addBadWord(String line) {
        if (line != null && !line.trim().isEmpty() && !badWords.contains(line)) {
            badWords.add(line);
        }
    }


    private void initialize(String serverAddress, int port) {
        frame = new JFrame("정규랜드 고객센터");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(255, 255, 255));


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
        reservationButton = new JRadioButton("예약 관련 문의");
        lostButton = new JRadioButton("분실물 문의");
        otherButton = new JRadioButton("기타 문의");
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
        frame.add(chatScroll, BorderLayout.CENTER);
//        chatArea.setLineWrap(true);
//        chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        messageField = new JTextField();
        messageField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        messageField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        messageField.setEditable(false);
        sendButton = new JButton("전송");
        styleButton(sendButton, new Color(255, 199, 0), Color.BLACK, 60, 40);
        sendButton.setEnabled(false);
        exitButton = new JButton("종료");
        styleButton(exitButton, new Color(200, 200, 200), Color.BLACK, 60, 40);
        exitButton.setEnabled(true);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(sendButton);
        buttonPanel.add(exitButton);

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);


        frame.add(inputPanel, BorderLayout.NORTH); // 이름, 문의 유형, 매칭 버튼 영역
        frame.add(chatScroll, BorderLayout.CENTER); // 채팅창
        frame.add(bottomPanel, BorderLayout.SOUTH); // 메시지 입력 및 버튼


        matchButton.addActionListener(e -> startMatching());
        exitButton.addActionListener(e -> {
            closed = true; // 닫힘 플래그 설정
            if (socket != null && !socket.isClosed()) { // 서버 연결 확인
                sendMessage(); // 연결된 경우에만 메시지 전송
            }
            closeResources(); // 리소스 정리
            frame.dispose(); // 창 닫기
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
                sendMessage();
                closeResources();
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
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(MouseEvent evt) {
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
                        System.out.println("시작");
                        while (true) {
                            String message = input.readUTF();
                            if (message.equals("/강퇴")) {

                                chatArea.setCaretPosition(chatArea.getDocument().getLength());
                                closed = true; // closed 플래그 설정
                                Thread.sleep(4000);
                                frame.dispose();
                                closeResources();
                                frame.dispose();
                                break;

                            } else if (message.contains("고객이 매칭되었습니다. 이제 대화가 가능합니다.") ||
                                    message.contains("상담사가 매칭되었습니다. 이제 대화가 가능합니다.")) { // 매칭 완료 메시지 감지

                                SwingUtilities.invokeLater(() -> {
                                    sendButton.setEnabled(true);

                                    messageField.setEditable(true);
                                    System.out.println("버튼 활성화 완료"); // 디버깅용
                                });
                            }
                            chatArea.append(message + "\n");
                            chatArea.setCaretPosition(chatArea.getDocument().getLength());
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

        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "서버연결 불가능");
            frame.dispose();
            //
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

        reservationButton.setEnabled(false); //변경사항 매칭중 다시선택 불가능
        lostButton.setEnabled(false);
        otherButton.setEnabled(false);

        messageField.setEnabled(true);
        sendButton.setEnabled(false);
        exitButton.setEnabled(true);

        try {
            output.writeUTF(role);
            output.flush();

            output.writeUTF(name);
            output.flush();

            output.writeUTF(inquiryType);
            output.flush();


        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "서버 전송 실패");
            e.printStackTrace();
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

        if (closed) {
            message = "/exit";
        } else if (name.isEmpty() || role == null || inquiryType == null) {
            JOptionPane.showMessageDialog(frame, "이름, 역할, 문의 유형, 메시지를 모두 입력하세요.");
            return;
        }

        String badword = message;
        for (String badWord : badWords) {
            if (badword.contains(badWord)) {
                badword = badword.replace(badWord, "**");

            }
        }
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        messageField.setText("");
        try {
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
