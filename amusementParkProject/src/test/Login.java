package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    public Login()  {

        JFrame frame = new JFrame();
        setTitle("로그인");

        JLabel lId = new JLabel("아이디");

        lId.setBounds(20,20,80,30);
        JLabel lPwd = new JLabel("비밀번호");

        lPwd.setBounds(20,75,80,30);

        JTextField tfId = new JTextField(10);
        tfId.setBounds(100,20,120,30);
        JTextField tfPwd = new JTextField(10);
        tfPwd.setBounds(100,75,120,30);

        //버튼
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(50,120,80,30);
        JButton registBtn = new JButton("회원가입");
        registBtn.setBounds(160,120,100,30);


        add(lId);  add(tfId);
        add(lPwd); add(tfPwd);
        add(loginBtn); add(registBtn);

        setSize(300, 250);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //액션 리스너
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //로그인 버튼 선택했을때
                if (loginBtn.equals(e.getSource())) {
                   //new TimeTable();
                }

                //회원가입 버튼 선택했을 때
            }
        };

        loginBtn.addActionListener(actionListener);
        registBtn.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public static void main(String[] args) {
        new Login();
    }
}

