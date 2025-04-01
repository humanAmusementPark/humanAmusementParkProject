package javaproject.Service;

import javaproject.chat.gui.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javaproject.chat.gui.ChatGUI;
import javaproject.urlTool;


public class MemMenuS extends JFrame implements ActionListener {

    urlTool utool = new urlTool();

    private final ImageIcon image = utool.getImageIcon("/javaproject/resource/images/map.jpg");
//    private final Image image = new ImageIcon("resource/images/map.jpg").getImage();
    private final ImageIcon image2 = utool.getImageIcon("/javaproject/resource/images/menubutton2.PNG");
    private String id;
    private LoginS LoginS;
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };
    private JPanel jpImage2 = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image2.getImage(), 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };
    MemMenuS m = this;

    public static void main(String[] args) {
        new MemMenuS(null, null);
    }

    public MemMenuS(String id, LoginS before) {
        this.LoginS = before;
        this.id = id;
        this.setTitle("정규랜드");
        this.setSize(1200, 7000);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(jpImage);

        jpImage.setLayout(null);

        jpImage2.setBounds(380, 0, 500, 40);
        jpImage.add(jpImage2);
        this.setVisible(true);
        add();
        mouseCheck();
        reservationBnt();
        logManageBnt();
        logoutBnt();
        ticketBnt();
        talkBut();

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

    public void add() {
        System.out.println("oooooo");
        addButton2("", 0, 0, 1, 1);
        addButton2("", 2, 2, 1, 1);
        addButton2("", 3, 3, 1, 1);
        addButton2("", 6, 6, 1, 1);
        addButton2("", 8, 8, 1, 1);
        addButton2("", 10, 10, 1, 1);


        addButton2("t익스", 496, 195, 67, 40);
        addButton2("롤링", 715, 404, 72, 40);
        addButton2("콜롬버스", 938, 433, 72, 40);
        addButton2("범퍼", 730, 282, 54, 40);
        addButton2("이솝", 851, 359, 68, 40);
        addButton2("썬더폴스", 845, 249, 55, 40);
        addButton2("사파리", 333, 473, 66, 40);
        addButton2("lost", 364, 685, 64, 38);
        addButton2("더블락", 669, 556, 69, 40);
        addButton2("버드", 589, 720, 74, 40);
        addButton2("판다", 604, 496, 60, 40);
        addButton2("애니멀원더 월드", 525, 614, 78, 40);
        addButton2("씨라이언", 443, 681, 78, 39);
        addButton2("애니멀원더 스테이지", 514, 474, 83, 40);
        addButton2("카니발광장", 488, 385, 67, 39);

    }

    public void logoutBnt() {
        JButton obnt = new JButton("");
        obnt.setBounds(776, 2, 100, 35);
        obnt.setVisible(true);
        obnt.setBorder(BorderFactory.createEmptyBorder());
        obnt.setContentAreaFilled(false);

        obnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                dispose();
                LoginS.setVisible(true);
            }
        });
        jpImage.add(obnt);
    }

    private void talkBut() {
        JButton cbnt = new JButton("");
        cbnt.setBounds(676, 2, 100, 35);
        cbnt.setVisible(true);
        cbnt.setBorder(BorderFactory.createEmptyBorder());
        cbnt.setContentAreaFilled(false);


        cbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new ChatGUI("고객");
            }
        });
        jpImage.add(cbnt);
    }

    public void reservationBnt() {
        JButton rbnt = new JButton("");
        rbnt.setBounds(576, 2, 100, 35);
        rbnt.setVisible(true);
        rbnt.setBorder(BorderFactory.createEmptyBorder());
        rbnt.setContentAreaFilled(false);

        rbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new MemReservationS(id, m);
                m.setEnabled(false);
            }
        });
        jpImage.add(rbnt);
    }

    public void ticketBnt() {
        JButton tbnt = new JButton("티켓구매");
        tbnt.setBounds(476, 2, 100, 35);
        tbnt.setVisible(true);
        tbnt.setBorder(BorderFactory.createEmptyBorder());
        tbnt.setContentAreaFilled(false);

        tbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MemTicketS(id, m);
                    m.setEnabled(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jpImage.add(tbnt);
    }

    public void logManageBnt() {
        JButton lbnt = new JButton("회원정보수정");
        lbnt.setBounds(376, 2, 100, 35);
        lbnt.setVisible(true);
        lbnt.setBorder(BorderFactory.createEmptyBorder());
        lbnt.setContentAreaFilled(false);

        lbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new MemS(id, m);
                m.setEnabled(false);
            }
        });
        jpImage.add(lbnt);
    }

    public void mouseCheck() {
        jpImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                System.out.println("마우스 클릭 좌표: X = " + mouseX + ", Y = " + mouseY);
            }
        });
    }

    public void addButton2(String name, int x, int y, int width, int height) {
        OutlineButton outbnt = new OutlineButton(width, height, name);
        outbnt.setBounds(x, y, width, height);
        jpImage.add(outbnt);
        outbnt.addActionListener(createButtonListener(name));
//        JButton jbnt = new JButton();
//        jbnt.setText("");
//        jbnt.setBounds(x, y, width, height);
//        jbnt.setBorder(BorderFactory.createEmptyBorder());
//        jbnt.setContentAreaFilled(false);
//        jpImage.add(jbnt);
//        jbnt.addActionListener(createButtonListener(name));
    }

    private ActionListener createButtonListener(String rideName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 클릭된 버튼을 가져옴
                JButton clickedButton = (JButton) e.getSource();  // 클릭된 버튼 객체
                System.out.println("클릭된 버튼: " + clickedButton);  // 버튼 객체
                System.out.println("놀이기구 이름: " + rideName);
                try {
                    new ReservationS(m, rideName, id);
                    m.setEnabled(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
//               임시 sendDataToTeamMember(rideName, rideId, clickedButton);  // 정진형한테 전달
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
    // 시설 버튼 커스텀
    class OutlineButton extends JButton {
        //     private final Color background = Color.RED; // 클릭 전 색깔
        private final Color foreground = Color.RED; // 클릭 후 색깔
        private final Color inlineColor = new Color(0, 0, 0, 0); // 투명색

        int paddingWidth, paddingHeight;

        public OutlineButton(int paddingWidth, int paddingHeight, String text) {
            this.paddingWidth = paddingWidth;
            this.paddingHeight = paddingHeight;
            setText(text);

            Dimension dimension = getPreferredSize();
//            System.out.println((int) dimension.getWidth());
//            System.out.println((int) dimension.getHeight());
//            int w = (int) dimension.getWidth() + paddingWidth * 2;
//            int h = (int) dimension.getHeight() + paddingHeight * 2;
            int w = paddingWidth + 6;
            int h = paddingHeight + 5;

            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
            setBorder(null);
            setBackground(inlineColor);
            setForeground(inlineColor);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(inlineColor);
                    setForeground(foreground);
                    revalidate();
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(inlineColor);
                    setForeground(inlineColor);
                    revalidate();
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Dimension dimension = getPreferredSize();
            int w = (int) dimension.getWidth() - 1;
            int h = (int) dimension.getHeight() - 1;

            if (getBackground() != null) {
                g2.setColor(getBackground());
                g2.fillRoundRect(1, 1, (int) (w - 1 / 3.5), (int) (h - 1 / 2.8), 35, 35);
            }
            g2.setColor(getForeground());
            g2.setStroke(new BasicStroke(3)); // 선 두께
            g2.drawRoundRect(3, 3, w - 10, h - 10, 35, 35); // width, height 곡률과 관련있음

//            g2.setColor(getForeground());
//            g2.setFont(new Font("맑은 고딕", 1, 18));

//            FontMetrics fontMetrics = g2.getFontMetrics();
//            Rectangle rectangle = fontMetrics.getStringBounds(getText(), g2).getBounds();

//            g2.drawString(getText(), (w - rectangle.width) / 2, (h - rectangle.height) / 2 + fontMetrics.getAscent());
        }
    }
}
