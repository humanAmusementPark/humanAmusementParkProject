package test;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {
    private JTextField inputMsg;

    public LoginWindow() {

        //flowlayout 객체 전달하기
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //버튼 만들기
        JButton btn1 = new JButton("버튼 1");
        JButton btn2 = new JButton("버튼 2");
        JButton btn3 = new JButton("버튼 3");

        //프레임에 추가하기
        add(btn1);
        add(btn2);
        add(btn3);


        //문자열 입력할 수 있는 ui
        inputMsg = new JTextField(10);

        //전송 버튼
        JButton sendBtn = new JButton("전송");
        sendBtn.setActionCommand("send");
        sendBtn.addActionListener(this);
        add(sendBtn);

        //삭제 버튼
        JButton deleteBtn = new JButton("삭제");
        deleteBtn.setActionCommand("delete");
        deleteBtn.addActionListener(this);

        //패널 객체를 생성해서
        JPanel panel = new JPanel();
        //패널에 UI를 추가
        panel.add(inputMsg);
        panel.add(sendBtn);
        panel.add(deleteBtn);
        //패널 통채로 프레임에 추가하기
        add(panel);



        setSize(800, 500);
        setLocation(100,100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //flowLayout 객체 전달하기
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    public static void main(String[] args) {
        new LoginWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //이벤트가 발생한 버튼에 설정된 action command 읽어오기
        String command = e.getActionCommand();
        if (command.equals("send")) {
            String msg = inputMsg.getText();
            JOptionPane.showMessageDialog(this, msg);
        }else if (command.equals("delete")) {
            inputMsg.setText("");
        }
    }
}
