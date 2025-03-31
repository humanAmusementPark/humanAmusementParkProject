package javaproject.Service;

import javaproject.DAO.MemDAO;
import javaproject.DAO.TicketDAO;
import javaproject.DTO.TicketDTO;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javaproject.urlTool;

public class MemTicketS extends JFrame implements ActionListener {

    private String userId; // 로그인한 사용자id
    private TicketDAO ticketDAO = TicketDAO.getInstance();
    private MemDAO memDAO = new MemDAO();

    public MemTicketS(String id, MemMenuS before) throws SQLException {
        this.userId = id;
        this.setSize(800, 400);
        this.setTitle("티켓 구매");

        setLayout(new GridLayout(1, 2));

        // 이미지 경로 확인
        //mac 용
//        String imagePath = "resource/images/티켓.jpg";
        //window용
        String imagePath = "/javaproject/resource/images/티켓.jpg";

        urlTool utool = new urlTool();
        ImageIcon image = utool.getImageIcon(imagePath);

//        Image image = new ImageIcon(imagePath).getImage();


        // 배경 이미지를 그리는 JPanel
        JPanel ImagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // paintComponent를 먼저 호출해야 합니다.
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this); // 이미지를 패널 크기에 맞게 그리기
            }
        };

        ImagePanel.setLayout(new BorderLayout()); // 레이아웃을 BorderLayout으로 설정하여 배경 위에 스크롤 패널 배치
        ImagePanel.setOpaque(true); // 배경을 불투명하게 설정하여 이미지가 제대로 보이도록 함

        add(ImagePanel); // JFrame에 ImagePanel을 추가


        // 티켓 버튼 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        List<TicketDTO> tickets = ticketDAO.getTicketList();
        for (TicketDTO ticket : tickets) {
            JButton ticketButton = new JButton("<html><center><b>" + ticket.getTName() +
                    "</b><br>가격: " + ticket.getTPrice() + "원</center></html>");
            ticketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            ticketButton.setPreferredSize(new Dimension(350, 50));
            ticketButton.addActionListener(e -> purchaseTicket(ticket.getTPass(),ticket.getTName()));
            panel.add(ticketButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panel.setPreferredSize(new Dimension(380, tickets.size() * 70));

        // 스크롤 패널 설정
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);

        setLocationRelativeTo(null);
        //창크기조절막기
        setResizable(false);
        setVisible(true);

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

    public void purchaseTicket(String ticket, String tName) {
        if (memDAO.select(userId).getTPass() != null) {
            JOptionPane.showMessageDialog(this, "이미 티켓을 구매하셨습니다!");
            return;
        }
        memDAO.edit(ticket, userId); // 티켓 구매 처리
        JOptionPane.showMessageDialog(this, tName + "이(가) 성공적으로 구매되었습니다");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}