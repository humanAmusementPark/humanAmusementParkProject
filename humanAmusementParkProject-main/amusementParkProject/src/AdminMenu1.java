package javaproject;

import javaproject.Service.*;
import javaproject.chat.gui.ChatClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class AdminMenu1 extends JFrame {
    JPanel mainPanel = new JPanel();
    JPanel headerPanel = new JPanel();
    JPanel contentPanel = new JPanel();
    JLabel titleLabel = new JLabel("정규랜드 관리모드", SwingConstants.CENTER);

    JButton chating = new JButton("회원상담");
    JButton attraction = new JButton("어트랙션");
    JButton reservation = new JButton("예약관리");
    JButton schedule = new JButton("일정표 관리");
    JButton member = new JButton("회원");
    JButton ticket = new JButton("티켓");
    JButton update = new JButton("로그아웃");

    LoginG LoginG;
    String id;

    public AdminMenu1(LoginG loginG, String id) {
        this.id = id;
        this.LoginG = loginG;
        this.setSize(1200, 800); // 더 큰 창으로 조정
        this.setLocationRelativeTo(null);
        this.setTitle("정규랜드 관리자모드");
        setupUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setupUI() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245)); // 웹 느낌의 연한 배경

        // 헤더 패널
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(26, 35, 126)); // 어두운 파란색 헤더
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 콘텐츠 패널
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // 버튼 간 간격
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 버튼 배치
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        contentPanel.add(chating, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        contentPanel.add(attraction, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        contentPanel.add(reservation, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        contentPanel.add(member, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        contentPanel.add(ticket, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(schedule, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        contentPanel.add(update, gbc);

        // 메인 패널에 추가
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // 버튼 스타일 적용
        styleButton(chating, new Color(33, 150, 243), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(attraction, new Color(33, 150, 243), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(reservation, new Color(76, 175, 80), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 20), 530, 70); // 더 큰 버튼
        styleButton(member, new Color(255, 152, 0), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(ticket, new Color(255, 152, 0), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(schedule, new Color(97, 97, 97), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(update, new Color(244, 67, 54), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);

        // 액션 리스너 추가
        setupActions();

        this.add(mainPanel);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor, Font font, int width, int height) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // 호버 효과
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void setupActions() {
        chating.addActionListener(e -> chatClick());
        attraction.addActionListener(e -> attractClick());
        reservation.addActionListener(e -> resCLick());
        schedule.addActionListener(e -> scheduleClick());
        member.addActionListener(e -> memberClick());
        ticket.addActionListener(e -> ticketClick());
        update.addActionListener(e -> adminUpdate());
    }

    private void chatClick() {
        System.out.println("연결");
        new ChatClient("상담사");
    }

    private void attractClick() {
        System.out.println("연결");
        new AttractionManager(this);
    }

    private void resCLick() {
        System.out.println("연결");
        new ReservationG();
    }

    private void scheduleClick() {
        try {
            new ManagerTimeTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void memberClick() {
        System.out.println("연결");
        new MemAdG();
    }

    private void ticketClick() {
        try {
            new ManagerTicket();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void adminUpdate() {
        System.out.println("연결");
        new AdG(id);
    }
}