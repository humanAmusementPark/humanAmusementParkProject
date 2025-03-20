package javaproject.chat.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private final Client client;
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> roleCombo;
    private JTextField nameField;
    private String selectedType = ""; // 선택된 상담 유형 저장

    public ClientGUI(Client client) {
        this.client = client;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("놀이공원 고객센터");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 상단 정보 입력 패널
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        roleCombo = new JComboBox<>(new String[]{"고객", "상담사"});
        nameField = new JTextField();

        infoPanel.add(new JLabel("역할:"));
        infoPanel.add(roleCombo);
        infoPanel.add(new JLabel("이름:"));
        infoPanel.add(nameField);

        // 상담 유형 버튼 패널
        JPanel typePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton rideButton = createTypeButton("놀이기구 문의");
        JButton ticketButton = createTypeButton("티켓 예매");
        JButton lostButton = createTypeButton("분실물 문의");

        typePanel.add(rideButton);
        typePanel.add(ticketButton);
        typePanel.add(lostButton);

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // 메시지 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        JButton sendButton = new JButton("전송");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // 시작 버튼
        JButton startButton = new JButton("상담 시작");
        startButton.addActionListener(e -> startChat());

        // 패널 배치
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(typePanel, BorderLayout.CENTER);
        topPanel.add(startButton, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 이벤트 리스너
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());
    }

    private JButton createTypeButton(String type) {
        JButton button = new JButton(type);
        button.addActionListener(e -> {
            selectedType = type;
            // 선택된 버튼 강조
            for (Component c : button.getParent().getComponents()) {
                if (c instanceof JButton) {
                    c.setBackground(null);
                }
            }
            button.setBackground(Color.LIGHT_GRAY);
        });
        return button;
    }

    private void startChat() {
        if (selectedType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "상담 유형을 선택해주세요.");
            return;
        }
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "이름을 입력해주세요.");
            return;
        }

        try {
            String role = (String) roleCombo.getSelectedItem();
            String name = nameField.getText();

            client.getOutput().writeUTF(role);
            client.getOutput().writeUTF(name);
            client.getOutput().writeUTF(selectedType);

            // 상담 시작 후 버튼들 비활성화
            roleCombo.setEnabled(false);
            nameField.setEnabled(false);
            for (Component c : ((JPanel)getContentPane().getComponent(0)).getComponents()) {
                if (c instanceof JButton) {
                    c.setEnabled(false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "연결 오류: " + e.getMessage());
        }
    }

    private void sendMessage() {
        try {
            String message = messageField.getText();
            if (!message.trim().isEmpty()) {
                client.getOutput().writeUTF(message);
                messageField.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "메시지 전송 실패: " + e.getMessage());
        }
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
}