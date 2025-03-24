package javaproject.Service;

import javaproject.AdminMenu1;
import javaproject.DAO.MemDAO;
import javaproject.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginG extends JFrame implements ActionListener {
    String[] select = {"관리자", "회원"};
    boolean s_flag = true;

    JLabel label = new JLabel("로그인");
    JComboBox<String> Combo = new JComboBox<String>(select);
    JLabel id = new JLabel("id");
    JLabel pw = new JLabel("pw");
    JTextField idField = new JTextField(10);
    JTextField pwField = new JTextField(10);
    JButton submit = new JButton("로그인");
    JButton signin = new JButton("회원가입");
    JPanel center = new JPanel();


    public LoginG() {
        this.setTitle("놀이공원 예약 시스템");
        this.add("Center", center);

        center.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        center.add(label, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        center.add(Combo, c);

        c.fill = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        center.add(id, c);


        c.gridx = 1;


        center.add(idField, c);

        c.gridx = 0;
        c.gridy = 3;
        center.add(pw, c);

        c.gridx = 1;
        center.add(pwField, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 2;
        c.fill = 1;
        center.add(submit, c);

        // 회원가입 버튼
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        center.add(signin, c);

        this.setSize(300, 233);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치


        Combo.addActionListener(this);
        submit.addActionListener(this);
        signin.addActionListener(this);





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
            submit();
        } else if (arg0.getSource() == signin) {
            this.setEnabled(true);
            new SignInG(this);

        }
    }

    private void submit() {
        String id = idField.getText();
        String pw = pwField.getText();
        boolean success = false;
        MemDAO memDAO = new MemDAO();
        if (s_flag)
            success = memDAO.idPassDuplicate(1, id, pw);
        else
            success = memDAO.idPassDuplicate(2, id, pw);

        if (success && s_flag) {
            this.setVisible(false);
            new AdminMenu1(this,id);
        } else if (success) {
            this.setVisible(false);
            new Map(id,this);
            new TimeTable();
        } else {
            JOptionPane.showMessageDialog(submit, "로그인 실패");
        }
    }

//    // SignInG 창에서 로그인 창을 다시 활성화하도록 수정
//    public void enableLoginWindow() {
//        this.setEnabled(true);  // 로그인 창 활성화
//        this.toFront();  // 로그인 창을 최상위로 가져오기
//    }


}
