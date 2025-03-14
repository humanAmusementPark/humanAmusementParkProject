package javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.SQLException;

import java.util.Scanner;


public class Login extends JFrame implements ActionListener {
    Scanner in = new Scanner(System.in);
    Map map;
    AdminMenu1 adminMenu;
    JPanel[] jpanel = new JPanel[6];
    private JCheckBox member = new JCheckBox("회원");
    private JCheckBox admin = new JCheckBox("관리자");
    private JLabel id = new JLabel("아이디 : ");
    private JLabel pass = new JLabel("비밀번호 : ");
    private JTextField idText = new JTextField(10);
    private JPasswordField passText = new JPasswordField(10);
    private JButton login = new JButton("로그인");
    private JButton signUp = new JButton("회원가입");
    String imagePath = "C:/Users/hu-07/Desktop/qqqqq/pp.jpg";
    ImageIcon imageIcon = new ImageIcon(imagePath);
    Image image = imageIcon.getImage(); // 이미지 가져오기
    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 크기 조정
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JLabel imageLabel = new JLabel(scaledIcon);
//    String imagePath3 = "C:\\Users\\hu-07\\Desktop\\qqqqq\\kkk.jpg";
    String imagePath2 = "C:\\Users\\hu-07\\Desktop\\qqqqq\\111.jpg";
    LoginService loginService;
    JPanel jp = new JPanel();
    private Image image2 = new ImageIcon(imagePath2).getImage(); //전체사진

    public Login() throws SQLException {

        loginService = LoginService.getInstance();
        this.setSize(1250, 850);
        this.setLocation(0, 0);
        this.setTitle("회원가입 / 로그인");
        jp.setLocation(500,550);
        jp.setSize(200,180);
        jp.setBackground(Color.WHITE);
        jp.setLayout(new GridLayout(6,1));
        addPanel(jp);
        jpanel[1].add(member);
        jpanel[1].add(admin);
        jpanel[3].add(id);
        jpanel[3].add(idText);
        jpanel[4].add(pass);
        jpanel[4].add(passText);
        jpanel[5].add(login);
        jpanel[5].add(signUp);
        this.add(jp);


                this.add(new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image2, 0, 0, getWidth(), getHeight(), null);    // image,x,y,width,height,observer
                this.setOpaque(false);
                super.paintComponents(g);
            }
        });
                this.setVisible(true);
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

         signUp.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
//                   이영씨 연결
             }
         });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = idText.getText();
                String password = new String(passText.getPassword());
                if (!member.isSelected() && !admin.isSelected()) {
                    JOptionPane.showMessageDialog(null, "회원 또는 관리자를 선택하세요.");
                    return;  // 로그인 처리를 진행하지 않음
                }
                try{
                    if(admin.isSelected()){
                        if(loginService.idPassDuplicate("MANAGER1","mid",ID,"mPass",password)){
                            JOptionPane.showMessageDialog(null, "관리자 로그인 성공!");
                            adminMenu = new AdminMenu1();

                            //이부분에 인자값 관리자 아이디 비번 인자로 넘기기
                        }else {
                            JOptionPane.showMessageDialog(null, "올바르지 않은 관리자 아이디 또는 비밀번호");
                        }
                    }else if(member.isSelected()){
                        if(loginService.idPassDuplicate("MEMBER1","mID",ID,"password",password)){
                            JOptionPane.showMessageDialog(null, "회원 로그인 성공!");
                            map = new Map(ID);
                            //이부분에 인자값 회원 아이디 비번 인자로 넘기기
                        }else{
                            JOptionPane.showMessageDialog(null, "올바르지 않은 회원 아이디 또는 비밀번호");
                        }
                    }
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        });

        member.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (member.isSelected()) {
                    admin.setSelected(false);
                }
            }
        });
        admin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (admin.isSelected()) {
                    member.setSelected(false);
                }
            }
        });

    }


    public void addPanel(JPanel jp) {
        for (int i = 0; i < jpanel.length; i++) {
            jpanel[i] = new JPanel();
            jp.add(jpanel[i]);
        }
    }

    public void login() throws SQLException {
        System.out.println("1.관리자 로그인 2.회원로그인");
        int a = in.nextInt();
        in.nextLine();
        login2(a);
    }

    public void login2(int a) throws SQLException {
        System.out.println("아이디 : ");
        String id = in.nextLine();
        System.out.println("비밀번호 : ");
        String pass = in.nextLine();
        if (a == 1) {
            if (loginService.idPassDuplicate("MANAGER", "mid", id, "mPass", pass)) {
                //관리자 메뉴 들어가기
            } else {
                System.out.println("올바르지 않은 아이디 혹은 비밀번호");
            }
        } else if (a == 2) {
            if (loginService.idPassDuplicate("MEMBER", "memberID", id, "passWord", pass)) {
                //회원메뉴 들어가기
            } else {
                System.out.println("올바르지 않은 아이디 혹은 비밀번호");
            }
        }
    }


    public void newLogin() throws SQLException {
        System.out.println("1.관리자가입  2.회원용가입");
        int a = in.nextInt();
        in.nextLine();
        switch (a) {
            case 1:
//                newManager();
            case 2:

        }
    }

//    public void newManager() throws SQLException {
//        System.out.println("아이디 : ");
//        String id = in.nextLine();
//        if (!loginService.idDuplicate("MANAGER", "mid", id)) {
//            loginService.addManager1(id);
//        } else {
//            System.out.println("중복된 아이디입니다.");
//            return;
//        }
//    }

//    public void newMember(String userid) throws SQLException {
//
//        if (!loginService.idDuplicate("MEMBER", "memberId", userid)) {
//            loginService.addMember1(userid);
//
//        } else {
//            System.out.println("중복된 아이디 입니다.");
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}

