package javaproject.Service;


import javaproject.DAO.MemDAO;
import javaproject.DTO.MemDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.List;

public class AdmemS extends JFrame {
    private JButton update = new JButton("수정");
    private JButton delete = new JButton("삭제");
    private JTable table = null;
    private JScrollPane scrollPane = null;

    public AdmemS(AdMenuS before) {
        setTitle("회원관리");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableLayout();
        butLayout();

        update.addActionListener(e -> update());
        delete.addActionListener(e -> delete());

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

    private void delete() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "선택한 행 없음", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        MemDAO memDAO = new MemDAO();
        if (memDAO.delete(table.getValueAt(row, 0).toString())) {
            JOptionPane.showMessageDialog(null, "삭제 완료");
            table.setModel(getList().getModel());
        } else {
            JOptionPane.showMessageDialog(null, "삭제 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void update() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "선택한 행 없음", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tPass = null;
        if (table.getValueAt(row, 5)!=null){
            tPass = table.getValueAt(row, 5).toString();
        }
        MemDAO memDAO = new MemDAO();
        MemDTO memDTO = MemDTO.builder()
                .mId(table.getValueAt(row, 0).toString())
                .mPass(table.getValueAt(row, 1).toString())
                .mName(table.getValueAt(row, 2).toString())
                .mGender(table.getValueAt(row, 3).toString().equals("남자") ? 0 : 1)
                .mBirth(Date.valueOf(table.getValueAt(row, 4).toString()))
                .tPass(tPass)
                .build();
        if (memDAO.update(memDTO)) {
            JOptionPane.showMessageDialog(null, "수정 완료");
            table.setModel(getList().getModel());
        } else {
            JOptionPane.showMessageDialog(null, "수정 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void butLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        panel.add(update);
        panel.add(delete);
        add("South", panel);
    }

    private void tableLayout() {
        table = getList();
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(getWidth(), 300);
        add(new JScrollPane(table));
    }

    private JTable getList() {
        MemDAO memDAO = new MemDAO();
        List<MemDTO> members = memDAO.selectAll();
        String[] header = {"아이디", "비밀번호", "이름", "성별", "생년월일", "이용권번호"};
        String[][] data = new String[members.size()][header.length];
        for (int i = 0; i < members.size(); i++) {
            MemDTO member = members.get(i);
            data[i][0] = member.getMId();
            data[i][1] = member.getMPass();
            data[i][2] = member.getMName();
            if (member.getMGender() == 0) {
                data[i][3] = "남자";
            } else {
                data[i][3] = "여자";
            }
            data[i][4] = member.BirthToString();
            data[i][5] = member.getTPass();
        }
        DefaultTableModel mod = new DefaultTableModel(data, header) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        return new JTable(mod);
    }

}
