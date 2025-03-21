package javaproject.Service;

import javaproject.DAO.AdminDAO;
import javaproject.DTO.AdminDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdG extends JFrame implements ActionListener {
    private JPanel labelJPanel = new JPanel();
    private JPanel textFJPanel = new JPanel();
    private JButton editPass = new JButton("수정");
    private JButton editName = new JButton("수정");
    private JButton editGender = new JButton("수정");
    private JButton editBirth = new JButton("수정");
    private JButton editPos = new JButton("수정");
    private JPanel buttonJPanel = new JPanel();

    String id;
    private JPanel center = new JPanel();
    private String[] labelName = {"아이디", "비밀번호", "이름", "성별", "생년월일", "직책"};
    private String[] positionName = {"매니저", "부매니저", "놀이공원관리자", "예약관리자", "티켓관리자"};
    private JLabel aId;
    private JTextField aPass;
    private JTextField aName;
    private JRadioButton radioMan = new JRadioButton("남자");
    private JRadioButton radioWoman = new JRadioButton("여자");
    private ButtonGroup group = new ButtonGroup();
    private JComboBox yearCom;
    private JComboBox monthCom;
    private JComboBox dayCom;
    private JComboBox aPos = new JComboBox(positionName);
    private JButton updateBut = new JButton("수정");

    public AdG(String id) {
        this.id = id;
        setTitle("관리자정보");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centerLayout();
        this.add(center);
        this.add("South", updateBut);

    }

    private void centerLayout() {
        center.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);


        // label
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
        AdminDAO adminDAO = new AdminDAO();
        AdminDTO adminDTO = adminDAO.select(id);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 0;
        aId = new JLabel(adminDTO.getAId());
        center.add(aId, c);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 1;
        aPass = new JTextField(adminDTO.getAPass(), 10);
        center.add(aPass, c);
        c.gridx = 1;
        c.gridy = 2;
        aName = new JTextField(adminDTO.getAName(), 10);
        center.add(aName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.insets.top = 5;
        c.insets.bottom = 5;
        JPanel aGender = makeGenderPanel(adminDTO.getAGender());
        center.add(aGender, c);
        c.gridx = 1;
        c.gridy = 4;
        JPanel aBirth = makerBirthPanel(adminDTO);
        center.add(aBirth, c);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        aPos.setBackground(Color.WHITE);
        aPos.setSelectedItem(adminDTO.getAPosition());
        center.add(aPos, c);
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

    private JPanel makerBirthPanel(AdminDTO adminDTO) {
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

        int BYear = Integer.parseInt(adminDTO.getYear());
        int BMonth = Integer.parseInt(adminDTO.getMonth());
        int BDay = Integer.parseInt(adminDTO.getDay());

        yearCom.setSelectedIndex(BYear - 1980);
        monthCom.setSelectedIndex(BMonth - 1);
        dayCom.setSelectedIndex(BDay - 1);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AdminDAO adminDAO = new AdminDAO();
        String newId = aId.getText();
        if (e.getSource() == editPass) {
            String newPass = aPass.getText();
            adminDAO.edit(1, newPass, newId);
        }
        if (e.getSource() == editName) {
            String newName = aName.getText();
            adminDAO.edit(2, newName, newId);
        }
        if (e.getSource() == editGender) {
            if (radioMan.isSelected()) {
                adminDAO.edit(3, "0", newId);
            } else {
                adminDAO.edit(3, "1", newId);
            }
        }
        if (e.getSource() == editBirth) {
            String newBirth = yearCom.getSelectedItem().toString() + "-" + monthCom.getSelectedItem().toString() + "-" + dayCom.getSelectedItem().toString();
            System.out.println(newBirth);
            adminDAO.edit(4, newBirth, newId);
        }
        if (e.getSource() == editPos) {
            String newPos = aPos.getSelectedItem().toString();
            System.out.println(newPos);
            adminDAO.edit(5, newPos, newId);
        } else {
            return;
        }
        dispose();
        new AdG(id);
    }

}
