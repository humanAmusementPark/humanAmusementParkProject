package javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonTest extends JFrame {
    private OutlineButton outlineButton = new OutlineButton(67, 36, "abcd");
    private final Image image = new ImageIcon(getClass().getResource("/images/kkk.jpg")).getImage();
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };

    public static void main(String[] args) {
        new ButtonTest();

    }

    public ButtonTest() {
        this.setTitle("Button Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        this.setVisible(true);
        this.setSize(1200, 7000);
        this.setResizable(false);
        jpImage.setLayout(null);
        outlineButton.setBounds(497, 163, 67, 36);
        jpImage.add(outlineButton);
        jpImage.setPreferredSize(new Dimension(1200, 7000));
        this.add(jpImage);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/kkk.jpg"));
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("이미지 로드 실패");
        } else {
            System.out.println("이미지 로드 성공");
        }
        mouseCheck();
        System.out.println(outlineButton.getText());

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
            System.out.println((int) dimension.getWidth());
            System.out.println((int) dimension.getHeight());
//            int w = (int) dimension.getWidth() + paddingWidth * 2;
//            int h = (int) dimension.getHeight() + paddingHeight * 2;
            int w = paddingWidth+6;
            int h = paddingHeight+5;

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
