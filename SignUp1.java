package javaproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignUp1 extends JFrame {

    private JLabel lblId;
    private JLabel lblName;
    private JLabel lblPassword;
    private JLabel lblReInfo;
    private JLabel lblGender;
    private JLabel lblAge;  // 나이 라벨
    private JTextField tfId;
    private JTextField tfName;
    private JPasswordField tfPassword;
    private JTextField tfReInfo;
    private JButton btnDup;
    private JButton btnJoin;
    private JButton btnCancel;
    private JRadioButton radiMan;
    private JRadioButton radiWoman;
    private JComboBox<String> ageComboBox;  // 나이 선택 콤보박스
    private Dimension sizeOfLabeel = new Dimension(130, 20);
    private int widthOfInput = 10;

    public SignUp1() {
        this.init();
        this.setDisplay();
        this.showFrame();
    }

    public JLabel getLable(String text) {
        JLabel lbl = new JLabel(text, 2);
        lbl.setPreferredSize(this.sizeOfLabeel);
        return lbl;
    }

    public void init() {
        this.lblId = this.getLable("아이디");
        this.lblName = this.getLable("이름");
        this.lblPassword = this.getLable("비밀번호");
        this.lblReInfo = this.getLable("재입력");
        this.lblGender = new JLabel("성별", 2);
        this.lblAge = this.getLable("나이");  // 나이 라벨
        this.tfId = new JTextField(this.widthOfInput);
        this.tfName = new JTextField(this.widthOfInput);
        this.tfPassword = new JPasswordField(this.widthOfInput);
        this.tfReInfo = new JTextField(this.widthOfInput);
        this.radiMan = new JRadioButton("Man");
        this.radiWoman = new JRadioButton("Woman", true);
        this.btnDup = new JButton("중복검사");
        this.btnJoin = new JButton("Join");
        this.btnCancel = new JButton("Cancel");


        String[] ageOptions = {"10대", "20대", "30대", "40대", "50대", "60대 이상"};
        this.ageComboBox = new JComboBox<>(ageOptions);
    }

    public void setDisplay() {
        JPanel id = new JPanel(new FlowLayout(0));
        id.add(this.lblId);
        id.add(this.tfId);
        id.add(this.btnDup);

        JPanel name = new JPanel(new FlowLayout(0));
        name.add(this.lblName);
        name.add(this.tfName);

        JPanel password = new JPanel(new FlowLayout(0));
        password.add(this.lblPassword);
        password.add(this.tfPassword);

        JPanel reInfo = new JPanel(new FlowLayout(0));
        reInfo.add(this.lblReInfo);
        reInfo.add(this.tfReInfo);

        JPanel gender = new JPanel(new FlowLayout(0));
        gender.add(this.lblGender);
        this.lblGender.setPreferredSize(new Dimension(115, 20));
        gender.add(this.radiMan);
        gender.add(this.radiWoman);

        JPanel age = new JPanel(new FlowLayout(0));
        age.add(this.lblAge); // 나이 라벨 추가
        age.add(this.ageComboBox); // 나이 콤보박스 추가

        JPanel pnl1 = new JPanel(new GridLayout(0, 1));
        pnl1.add(id);
        pnl1.add(name);
        pnl1.add(password);
        pnl1.add(reInfo);
        pnl1.add(gender);
        pnl1.add(age);  // 나이 패널 추가

        JPanel pnlNorth = new JPanel();
        pnlNorth.add(pnl1);

        JPanel button = new JPanel();
        button.add(this.btnJoin);
        button.add(this.btnCancel);
        button.setBorder(new EmptyBorder(10, 0, 10, 0));

        this.add(pnlNorth, "North");
        this.add(button, "South");
    }

    public void showFrame() {
        this.setTitle("회원가입");
        this.pack();
        this.setLocation(100, 0);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }
}
