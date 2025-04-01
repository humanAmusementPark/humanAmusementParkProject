package javaproject.Service;

import javaproject.chat.gui.ChatClient;
import javaproject.chat.gui.ChatGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class AdMenuS extends JFrame {
    JPanel mainPanel = new JPanel();
    JPanel headerPanel = new JPanel();
    JPanel contentPanel = new JPanel();
    JLabel titleLabel = new JLabel("정규랜드 관리모드", SwingConstants.CENTER);

    JButton chating = new JButton("회원상담");
    JButton attraction = new JButton("시설관리");
    JButton reservation = new JButton("예약관리");
    JButton schedule = new JButton("일정표 관리");
    JButton member = new JButton("회원");
    JButton ticket = new JButton("티켓");
    JButton update = new JButton("로그아웃");
    JButton adminManage = new JButton("관리자정보"); // 새로 추가

    LoginS LoginS;
    String id;

    public AdMenuS(LoginS before, String id) {
        this.id = id;
        this.LoginS = before;
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("정규랜드 관리자모드");
        setupUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        before.setEnabled(true);
                        before.toFront();
                        before.setFocusable(true);
                        before.requestFocusInWindow();
                    }
                }
        );
    }

    private void setupUI() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(26, 35, 126));
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        contentPanel.add(chating, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        contentPanel.add(attraction, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; // 예약관리 한 칸으로 줄임
        contentPanel.add(reservation, gbc);
        gbc.gridx = 1; gbc.gridy = 1; // 관리자관리 추가
        contentPanel.add(adminManage, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        contentPanel.add(member, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        contentPanel.add(ticket, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(schedule, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        contentPanel.add(update, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        styleButton(chating, new Color(33, 150, 243), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(attraction, new Color(15, 76, 233), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(reservation, new Color(76, 175, 80), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70); // 크기 조정
        styleButton(adminManage, new Color(156, 39, 176), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70); // 관리자관리 스타일
        styleButton(member, new Color(255, 152, 0), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(ticket, new Color(255, 102, 0), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(schedule, new Color(97, 97, 97), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);
        styleButton(update, new Color(244, 67, 54), Color.WHITE, new Font("맑은 고딕", Font.BOLD, 18), 250, 70);

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
        update.addActionListener(e -> logOut());
        adminManage.addActionListener(e -> adminManageClick()); // 새로 추가
    }

    private void chatClick() {
        System.out.println("연결");
        new ChatGUI("상담사");
    }

    private void attractClick() {
        System.out.println("연결");
        new AttractionS(this);
        this.setEnabled(false);
    }

    private void resCLick() {
        System.out.println("연결");
        new AdReservationS(this);
        this.setEnabled(false);
    }

    private void scheduleClick() {
        try {
            new AdTimeTableS(this);
            this.setEnabled(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void memberClick() {
        System.out.println("연결");
        new AdmemS(this);
        this.setEnabled(false);
    }

    private void ticketClick() {
        try {
            new AdTicketS(this);
            this.setEnabled(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void adminManageClick() {
        System.out.println("연결");
        // 여기에 관리자관리 기능 추가 (예: new AdminManagerG();)
        new AdS(id,this);
        this.setEnabled(false);
    }

    private void logOut() {
        System.out.println("연결");
         dispose();
         LoginS.setVisible(true);
    }
}
