package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class tempFrame extends JFrame {

    private JLabel lblId;
    private JLabel lblName;
    private JLabel lblPassword;
    private JLabel lblReInfo;
    private JLabel lblGender;
    private JLabel lblSelf;

    private JTextField tfId;
    private JTextField tfName;
    private JPasswordField tfPassword;
    private JTextField tfReInfo;
    private JTextArea tfSelf;

    private JButton btnDup;
    private JButton btnJoin;
    private JButton btnCancel;

    private JRadioButton radiMan;
    private JRadioButton radiWoman;

    //레이블의 크기를 고정시킬 수 있다.
    private Dimension sizeOfLabeel = new Dimension(130,20);
    private int widthOfInput = 10;

    public tempFrame() {
        init();
        setDisplay();
        showFrame();
    }

    public JLabel getLable(String text){
        JLabel lbl = new JLabel(text,JLabel.LEFT);
        lbl.setPreferredSize(sizeOfLabeel);
        return lbl;
    }

    //구성 요소 초기화
    public void init() {
        lblId = getLable("아이디");
        lblName = getLable("이름");
        lblPassword = getLable("비밀번호");
        lblReInfo = getLable("재입력");
        lblGender = new JLabel("성별", JLabel.LEFT);
        lblSelf = getLable("자기소개");

        tfId = new JTextField(widthOfInput);
        tfName = new JTextField(widthOfInput);
        tfPassword = new JPasswordField(widthOfInput);
        tfReInfo = new JTextField(widthOfInput);
        tfSelf = new JTextArea(5,30);

        radiMan = new JRadioButton("Man");
        radiWoman = new JRadioButton("Woman",true);

        btnDup = new JButton("중복검사");
        btnJoin = new JButton("Join");
        btnCancel = new JButton("Cancel");
    }

    //배치
    public void setDisplay() {
        JPanel id = new JPanel(new FlowLayout(FlowLayout.LEFT));
        id.add(lblId);
        id.add(tfId);
        id.add(btnDup);

        JPanel name =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        name.add(lblName);
        name.add(tfName);

        JPanel password = new JPanel(new FlowLayout(FlowLayout.LEFT));
        password.add(lblPassword);
        password.add(tfPassword);

        JPanel reInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reInfo.add(lblReInfo);
        reInfo.add(tfReInfo);

        JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gender.add(lblGender);
        lblGender.setPreferredSize(new Dimension(115,20));
        gender.add(radiMan);
        gender.add(radiWoman);

        JPanel self = new JPanel(new FlowLayout(FlowLayout.LEFT));
        self.add(lblSelf);

        //위에꺼 한꺼번에 묶음
        JPanel pnl1 = new JPanel(new GridLayout(0,1));
        pnl1.add(id);
        pnl1.add(name);
        pnl1.add(password);
        pnl1.add(reInfo);
        pnl1.add(gender);
        pnl1.add(self);

        //north
        JPanel pnlNorth = new JPanel();
        pnlNorth.add(pnl1);

        // CENTER
        JPanel area = new JPanel();
        JScrollPane scroll = new JScrollPane(tfSelf);
        area.add(scroll);

        // SOUTH
        JPanel button = new JPanel();
        button.add(btnJoin);
        button.add(btnCancel);
        button.setBorder(new EmptyBorder(10, 0, 10, 0));

        // 마지막 결과 배치
        add(pnlNorth, BorderLayout.NORTH);
        add(area, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);


    }

    //프레임 설정 및 가시성 확보
    public void showFrame(){
        setTitle("회원가입");
        pack();
        setLocation(100,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new tempFrame();
    }

}
