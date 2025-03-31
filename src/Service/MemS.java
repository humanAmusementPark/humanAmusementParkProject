package javaproject.Service;


import javaproject.DAO.MemDAO;
import javaproject.DTO.MemDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

// 회원 회원정보수정
public class MemS extends JFrame {
    private String id;
    private JPanel center = new JPanel();
    private String[] labelName = {"아이디", "비밀번호", "이름", "성별", "생년월일", "티켓번호"};
    private JLabel mId;
    private JTextField mPass;
    private JTextField mName;
    private JRadioButton radioMan = new JRadioButton("남자");
    private JRadioButton radioWoman = new JRadioButton("여자");
    private ButtonGroup group = new ButtonGroup();
    private JComboBox yearCom;
    private JComboBox monthCom;
    private JComboBox dayCom;
    private JLabel tPass;
    private JButton updateBut = new JButton("수정");

    public MemS(String id, MemMenuS before) {
        this.id = id;
        setTitle("회원정보");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        centerLayout();
        updateBut.addActionListener(e -> update());

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

    private void update() {
        MemDAO memDAO = new MemDAO();
        String newPass = mPass.getText();
        String newName = mName.getText();
        if (newPass.isEmpty() || newName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "빈칸 발생.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int newGender = radioMan.isSelected() ? 0 : 1;
        String newBirth = yearCom.getSelectedItem().toString() + "-" + monthCom.getSelectedItem().toString()
                + "-" + dayCom.getSelectedItem().toString();
        MemDTO memDTO = MemDTO.builder()
                .mId(id)
                .mPass(newPass)
                .mName(newName)
                .mGender(newGender)
                .mBirth(Date.valueOf(newBirth))
                .build();
        if (memDAO.update(memDTO)) {
            JOptionPane.showMessageDialog(null, "수정 완료");
        } else {
            JOptionPane.showMessageDialog(null, "수정 실패", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void centerLayout() {
        center.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        //label
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        center.add(new JLabel(labelName[0]), c);
        c.gridx = 0;
        c.gridy = 1;
        center.add(new JLabel(labelName[1]), c);
        c.gridx = 0;
        c.gridy = 2;
        center.add(new JLabel(labelName[2]), c);
        c.gridx = 0;
        c.gridy = 3;
        center.add(new JLabel(labelName[3]), c);
        c.gridx = 0;
        c.gridy = 4;
        center.add(new JLabel(labelName[4]), c);
        c.gridx = 0;
        c.gridy = 5;
        center.add(new JLabel(labelName[5]), c);
        // Text
        MemDAO memDAO = new MemDAO();
        MemDTO memDTO = memDAO.select(id);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        mId = new JLabel(memDTO.getMId());
        center.add(mId, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        mPass = new JPasswordField(memDTO.getMPass(), 10);
        center.add(mPass, c);
        c.gridx = 1;
        c.gridy = 2;
        mName = new JTextField(memDTO.getMName(), 10);
        center.add(mName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        JPanel mGender = makeGenderPanel(memDTO.getMGender());
        center.add(mGender, c);
        c.gridx = 1;
        c.gridy = 4;
        JPanel mBirth = makerBirthPanel(memDTO);
        center.add(mBirth, c);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 5;
        tPass = new JLabel(memDTO.getTPass());
        center.add(tPass, c);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        updateBut.setFont(new Font("맑은고딕", Font.BOLD, 12));
        center.add(updateBut, c);

        this.add(center);
    }

    private JPanel makeGenderPanel(int a) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(radioMan);
        panel.add(radioWoman);
        if (a == 0) {
            radioMan.setSelected(true);
        } else if (a == 1) {
            radioWoman.setSelected(true);
        }
        group.add(radioMan);
        group.add(radioWoman);

        return panel;
    }

    private JPanel makerBirthPanel(MemDTO memDTO) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] year = new String[46];
        String[] month = new String[12];
        String[] day = new String[31];
        for (int i = 0; i < year.length; i++) {
            year[i] = String.valueOf(i + 1980);
        }
        for (int i = 0; i < month.length; i++) {
            month[i] = String.valueOf(i + 1);
        }
        for (int i = 0; i < day.length; i++) {
            day[i] = String.valueOf(i + 1);
        }
        yearCom = new JComboBox(year);
        yearCom.setBackground(Color.WHITE);
        monthCom = new JComboBox(month);
        monthCom.setBackground(Color.WHITE);
        dayCom = new JComboBox(day);
        dayCom.setBackground(Color.WHITE);
        panel.add(yearCom);
        panel.add(new JLabel("년"));
        panel.add(monthCom);
        panel.add(new JLabel("월"));
        panel.add(dayCom);
        panel.add(new JLabel("일"));

        int mBYear = Integer.parseInt(memDTO.getYear());
        int mBMonth = Integer.parseInt(memDTO.getMonth());
        int mBDay = Integer.parseInt(memDTO.getDay());

        yearCom.setSelectedIndex(mBYear - 1980);
        monthCom.setSelectedIndex(mBMonth - 1);
        dayCom.setSelectedIndex(mBDay - 1);

        return panel;
    }


}
