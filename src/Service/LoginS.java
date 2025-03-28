package javaproject.Service;

import javaproject.DAO.MemDAO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javaproject.urlTool;

public class LoginS extends JFrame implements ActionListener {
    String[] select = {"관리자", "회원"};
    boolean s_flag = true;

    urlTool urTool = new urlTool();

//    ImageIcon image2 = urTool.getImageIcon("/javaproject/resource/images/로그인.PNG");
//
//    JLabel label = new JLabel(image2);

    JLabel label = new JLabel(new ImageIcon("javaproject/Service/resource/images/메인.png"));
    JComboBox<String> Combo = new JComboBox<String>(select);



    JLabel id = new JLabel("id");
    JLabel pw = new JLabel("pw");
    JTextField idField = new JTextField(22);
    JPasswordField pwField = new JPasswordField(22);

//    ImageIcon submitImage=new ImageIcon("src/resource/images/로그인.png");
    ImageIcon submitImage = urTool.getImageIcon("/javaproject/resource/images/로그인.PNG");

//    ImageIcon signinImage=new ImageIcon("src/resource/images/회원가입.png");
    ImageIcon signinImage = urTool.getImageIcon("/javaproject/resource/images/회원가입.PNG");


    JButton submit = new JButton(submitImage);
    JButton signin = new JButton(signinImage);
    JPanel center = new JPanel();

//    private final Image image = new ImageIcon("resource/images/놀이공원3.jpg").getImage();
    private final ImageIcon image = urTool.getImageIcon("/javaproject/resource/images/놀이공원3.jpg");
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawImage(image.getImage(), 0, 0, 290,300, null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };
    JPanel pp=new JPanel();
    CardLayout cl=new CardLayout(10,10);
    SignInS signInS =new SignInS(pp);

    public LoginS() throws IOException {

        this.setTitle("놀이공원 예약 시스템");
        this.setLayout(new GridLayout(2,1));

        this.add(jpImage);
        this.add(pp);

        pp.setBackground(Color.WHITE);
        pp.setLayout(cl);
        pp.add(center);
        pp.add(signInS);


        center.setBackground(Color.WHITE);
        center.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        idField.setOpaque(false);
        pwField.setOpaque(false);
        //idField.setText("aaa");
        //pwField.setText("aaa");
        Combo.setBackground(Color.WHITE);
        Combo.setBorder(null);
        Combo.setSelectedIndex(1);
        submit.setBorder(null);
        submit.setBackground(null);
        signin.setBackground(null);
        signin.setBorder(null);
        idField.setBorder(null);
        pwField.setBorder(null);
        JPanel underline1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // 밑줄 색상
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // 밑줄 그리기
                if(idField.getText().isEmpty()) {
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setColor(Color.GRAY); // 밑줄 색상
                    g.setFont(new Font("Arial", Font.PLAIN, 12)); // 글자 폰트 설정
                    g.drawString("ID", 5, getHeight() / 2 + 5); // 원하는 위치에 텍스트 그리기
                }


            }
        };
        JPanel underline2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // 밑줄 색상
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // 밑줄 그리기
                if(pwField.getText().isEmpty()) {
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setColor(Color.GRAY); // 밑줄 색상
                    g.setFont(new Font("Arial", Font.PLAIN, 12)); // 글자 폰트 설정
                    g.drawString("PW", 5, getHeight() / 2 + 5); // 원하는 위치에 텍스트 그리기
                }
            }
        };

        underline1.setBounds(idField.getX(), idField.getY() + idField.getHeight(), idField.getWidth(), 1);
        underline1.add(idField);
        underline1.setBackground(Color.WHITE);
        underline2.setBounds(idField.getX(), idField.getY() + idField.getHeight(), idField.getWidth(), 1);
        underline2.add(pwField);
        underline2.setBackground(Color.WHITE);
        c.ipady=5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        center.add(label, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        center.add(Combo, c);


        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 4;


        center.add(underline1, c);

        c.gridx = 0;
        c.gridy = 3;

        center.add(underline2, c);

        c.gridx = 0;
        c.gridy = 5;
        c.fill = 1;

        center.add(submit, c);

        // 회원가입 버튼
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        c.weightx = 1;
        c.gridheight = 2;
        center.add(signin, c);

        this.setSize(300, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치


        Combo.addActionListener(this);
        submit.addActionListener(this);
        signin.addActionListener(this);


        s_flag=Combo.getSelectedIndex() == 0;


        KeyAdapter adapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Enter 키 입력 확인
                    try {
                        submit(); // 공통 동작 호출
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        Combo.addKeyListener(adapter);
        idField.addKeyListener(adapter);
        pwField.addKeyListener(adapter);
        idField.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == Combo) {
            if (Combo.getSelectedIndex() == 0) {
                s_flag = true;
            } else {
                s_flag = false;
            }
        } else if (arg0.getSource() == submit) {
            try {
                submit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (arg0.getSource() == signin) {
            cl.next(pp);

        }
    }

    private void submit() throws IOException {
        String id = idField.getText();
        String pw = pwField.getText();
        idField.setText("");
        pwField.setText("");
        boolean success = false;
        MemDAO memDAO = new MemDAO();
        if (s_flag)
            success = memDAO.idPassDuplicate(1, id, pw);
        else
            success = memDAO.idPassDuplicate(2, id, pw);

        if (success && s_flag) {
            this.setVisible(false);
            new AdMenuS(this,id);
        } else if (success) {
            this.setVisible(false);
            new MemMenuS(id,this);
            new MemTimeTableS();
        } else {
            JOptionPane.showMessageDialog(null, "로그인 실패");
        }
    }

//    // SignInG 창에서 로그인 창을 다시 활성화하도록 수정
//    public void enableLoginWindow() {
//        this.setEnabled(true);  // 로그인 창 활성화
//        this.toFront();  // 로그인 창을 최상위로 가져오기
//    }


}