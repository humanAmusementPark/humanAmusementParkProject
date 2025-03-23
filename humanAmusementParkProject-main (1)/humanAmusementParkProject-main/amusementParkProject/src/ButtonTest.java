package javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonTest extends JFrame {
    private OutlineButton outlineButton = new OutlineButton(Color.black, Color.white);
    private final Image image = new ImageIcon(getClass().getResource("/images/kkk.jpg")).getImage();
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };
    ButtonTest m = this;
    public static void main(String[] args) {
        ButtonTest t = new ButtonTest();
    }

    public ButtonTest() {
        this.add(jpImage);
        this.setTitle("Button Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(null);

        this.add(outlineButton);

    }


    class OutlineButton extends JButton {
        private final Color background;
        private final Color foreground;

        int paddingWidth = 15, paddingHeight = 5;

        public OutlineButton(Color background, Color foreground) {
            this.background = background;
            this.foreground = foreground;
            setText("Outline");

            Dimension dimension = getPreferredSize();
            int w = (int) dimension.getWidth() + paddingWidth * 2;
            int h = (int) dimension.getHeight() + paddingHeight * 2;

            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
            setBorder(null);
            setBackground(null);
            setForeground(background);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(background);
                    setForeground(foreground);
                    revalidate();
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(null);
                    setForeground(background);
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
            int h = (int) dimension.getHeight()- 1;

            if(getBackground() != null) {
                g2.setColor(getBackground());
                g2.fillRoundRect(1, 1, (int) (w - 1 / 3.5), (int) (h - 1 / 2.8), 35, 35);
            }

            g2.setColor(getForeground());
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, w, h, 35, 35);

            g2.setColor(getForeground());
            g2.setFont(new Font("맑은 고딕", 1, 18));

            FontMetrics fontMetrics = g2.getFontMetrics();
            Rectangle rectangle = fontMetrics.getStringBounds(getText(), g2).getBounds();

            g2.drawString(getText(), (w - rectangle.width) / 2, (h - rectangle.height) / 2 + fontMetrics.getAscent());
        }
    }
}
