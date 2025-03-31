package javaproject.Service;

import javaproject.DAO.AdminDAO;
import javaproject.DAO.MemDAO;
import javaproject.DTO.AdminDTO;
import javaproject.DTO.MemDTO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

//import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
//import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
//import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SignInS extends JPanel implements ActionListener {

    JLabel title = new JLabel("회원가입");

    JLabel g_label = new JLabel("gender");
    JLabel b_label = new JLabel("birth");
    HintField i_field = new HintField("ID");
    HintField p_field = new HintField("PW");
    HintField n_field = new HintField("NAME");
    ButtonGroup g_radio = new ButtonGroup();
    JPanel birthPanel;
    JComboBox yearCom;
    JComboBox monthCom;
    JComboBox dayCom;
    JButton submit = new JButton("SUBMIT");
    JButton back = new JButton("BACK");
    boolean idck = false; //중복체크시 true
    JRadioButton m = new JRadioButton("남자");
    JRadioButton f = new JRadioButton("여자");
    JRadioButton ad_button = new JRadioButton("관리자");
    JRadioButton mm_button = new JRadioButton("회원");
    ButtonGroup p_radio = new ButtonGroup();
    JPanel before = null;
    private String[] rank = {"Manager", "SubManager", "AttractionManager", "ReservationManager", "TicketManager"};
    JComboBox<String> Combo = new JComboBox<String>(rank);
    boolean flag = false;
    JPanel rankPanel = new JPanel();

    public SignInS(JPanel before) {
        this.before = before;
        this.setBorder(new LineBorder(Color.black, 1, true));

        setBackground(Color.WHITE);
        g_radio.add(m);
        g_radio.add(f);
        JPanel radio = new JPanel();
        //radio.setLayout(new GridLayout());
        //radio.setBorder(new LineBorder(Color.BLACK, 1, true));
        radio.add(m);
        radio.add(f);
        radio.setBackground(Color.WHITE);

        JPanel radio2 = new JPanel();
        //radio2.setLayout(new GridLayout());
        p_radio.add(ad_button);
        p_radio.add(mm_button);

        m.setSelected(true);
        mm_button.setSelected(true);
        m.setBackground(Color.WHITE);
        f.setBackground(Color.WHITE);
        ad_button.setBackground(Color.WHITE);
        mm_button.setBackground(Color.WHITE);
        radio2.setBackground(Color.WHITE);
        radio2.add(ad_button);
        radio2.add(mm_button);
        //radio2.setBorder(new LineBorder(Color.BLACK, 1, true));
        Color color = new Color(147, 86, 242);
        submit.setBackground(color);
        back.setBackground(Color.WHITE);
        submit.setForeground(Color.WHITE);
        back.setBorder(null);
        submit.setBorder(null);
        CardLayout cl = new CardLayout();
        rankPanel.setLayout(cl);
        rankPanel.setBackground(Color.WHITE);
        JLabel l = new JLabel(" ");
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        rankPanel.add(l, "empty");
        rankPanel.add(Combo, "rank");

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 5, 5);

        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;

        add(i_field, c);


        //c.fill = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        add(p_field, c);


        //c.fill = 0;
        c.gridx = 0;
        c.gridy = 3;

        c.gridwidth = 2;
        add(n_field, c);


        c.insets = new Insets(0, 5, 0, 5);
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        add(radio, c);

        c.gridx = 1;
        c.gridy = 4;
        add(radio2, c);
        c.insets = new Insets(5, 5, 5, 5);

        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        birthPanel = makerBirthPanel();
        add(birthPanel, c);

        c.gridx = 0;
        c.gridy = 6;
        add(rankPanel, c);


        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weighty = 2;


        c.fill = GridBagConstraints.BOTH;
        add(back, c);

        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(submit, c);

        submit.addActionListener(this);
        back.addActionListener(e -> {
            ((CardLayout) before.getLayout()).next(before);
        });

        ad_button.addActionListener(e -> {
            flag = true;
            cl.show(rankPanel, "rank");

        });
        mm_button.addActionListener(e -> {
            flag = false;
            cl.show(rankPanel, "empty");
        });


    }

    private JPanel makerBirthPanel() {
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

        panel.add(new JLabel("BIRTH"));
        panel.add(yearCom);
        panel.add(new JLabel("년"));
        panel.add(monthCom);
        panel.add(new JLabel("월"));
        panel.add(dayCom);
        panel.add(new JLabel("일"));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public boolean ckId() {
        String id = i_field.getText();
        MemDAO memDAO = new MemDAO();
        AdminDAO adminDAO = new AdminDAO();
        if (ad_button.isSelected()) {
            if (adminDAO.idDuplicate(id)) {
                idck = true;
            } else {
                JOptionPane.showMessageDialog(this, "id중복");
                return true;
            }
        } else if (mm_button.isSelected()) {
            if (memDAO.idDuplicate(id)) {
                idck = true;
            } else {
                JOptionPane.showMessageDialog(this, "id중복");
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MemDAO memDAO = new MemDAO();
        AdminDAO adminDAO = new AdminDAO();
        if (i_field.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "id를 입력하세요");
            return;
        }
        if (p_field.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요");
            return;
        }
        if (n_field.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "이름을 입력하세요");
            return;
        }
        if (ckId()) {
            return;
        }
        if (e.getSource() == submit) {
            if (idck) {
                if (signInDB()) {
                    JOptionPane.showMessageDialog(null, "가입 성공");
                    ((CardLayout) before.getLayout()).next(before);
                } else
                    JOptionPane.showMessageDialog(null, "가입 실패");
            } else {
                JOptionPane.showMessageDialog(null, "중복체크필수");
            }
        }
    }

    private boolean signInDB() {
        boolean result = false;
        if (ad_button.isSelected()) {
            String adNum = JOptionPane.showInputDialog(null, "관리자비밀번호");

            String position = null;
            if (adNum.equals("wjdrbfosem")) {
                position = Combo.getSelectedItem().toString();
            } else {
                JOptionPane.showMessageDialog(null, "비밀번호 오류");
            }
            String aBirth = yearCom.getSelectedItem().toString() + "-"
                    + monthCom.getSelectedItem().toString() + "-" + dayCom.getSelectedItem().toString();
            AdminDTO adminDTO = AdminDTO.builder()
                    .aId(i_field.getText())
                    .aPass(p_field.getText())
                    .aName(n_field.getText())
                    .aGender(m.isSelected() ? 0 : 1)
                    .aBirth(Date.valueOf(aBirth))
                    .aPosition(position)
                    .build();

            AdminDAO adminDAO = new AdminDAO();
            if (adminDAO.insert(adminDTO)) {
                result = true;
            }
        } else if (mm_button.isSelected()) {
            String mBirth = yearCom.getSelectedItem().toString() + "-"
                    + monthCom.getSelectedItem().toString() + "-" + dayCom.getSelectedItem().toString();
            MemDTO memDTO = MemDTO.builder()
                    .mId(i_field.getText())
                    .mPass(p_field.getText())
                    .mName(n_field.getText())
                    .mGender(m.isSelected() ? 1 : 0)
                    .mBirth(Date.valueOf(mBirth))
                    .build();

            MemDAO memDAO = new MemDAO();
            if (memDAO.insert(memDTO)) {
                result = true;
            }
        }
        return result;
    }
}

class HintField extends JTextField {
    private final String hint;

    public HintField(String hint) {
        this.hint = hint; // 이미지를 설정
        this.setOpaque(false); // 투명성 설정
        this.setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK); // 밑줄 색상
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // 밑줄 그리기
        if (getText().isEmpty()) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(Color.GRAY); // 밑줄 색상
            g.setFont(new Font("Arial", Font.PLAIN, 12)); // 글자 폰트 설정
            g.drawString(hint, 5, getHeight() / 2 + 5); // 원하는 위치에 텍스트 그리기
        }
    }
}