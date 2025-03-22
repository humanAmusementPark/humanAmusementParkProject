package javaproject.Service;


import javaproject.AdminMenu1;
import javaproject.DAO.MemDAO;
import javaproject.DAO.ReservationDAO;
import javaproject.DTO.MemDTO;
import javaproject.DTO.ReservationDTO;
import javaproject.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.List;

// 회원, 관리자 예약현황 둘다 전체 조회 삭제만 가능
public class ReservationG extends JFrame implements ActionListener {
    private JButton insert = new JButton("등록");
    private JButton update = new JButton("수정");
    private JButton delete = new JButton("취소");
    private JTable table = null;
    private String[] info;
    private JScrollPane scrollPane;

    public ReservationG(AdminMenu1 before) {
        setTitle("예약현황");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableLayout();
        butLayout();

        insert.addActionListener(this);

        update.addActionListener(this);
        delete.addActionListener(this);

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

    private void delete(int row) {
        ReservationDAO reservationDAO = new ReservationDAO();
        if (reservationDAO.delete(table.getValueAt(row, 0).toString())) {
            JOptionPane.showMessageDialog(null, "수정 완료");
            this.remove(scrollPane);
            tableLayout();
        } else {
            JOptionPane.showMessageDialog(null, "수정 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void update(int row) {
        ReservationDAO reservationDAO = new ReservationDAO();
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .no(Integer.parseInt(table.getValueAt(row, 0).toString()))
                .mId(table.getValueAt(row, 1).toString())
                .tPass(table.getValueAt(row, 2).toString())
                .atId(table.getValueAt(row, 3).toString())
                .rTime(Date.valueOf(table.getValueAt(row, 4).toString()))
                .build();
        if (reservationDAO.update(reservationDTO)) {
            JOptionPane.showMessageDialog(null, "수정 완료");
            this.remove(scrollPane);
            tableLayout();
        } else {
            JOptionPane.showMessageDialog(null, "수정 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void insert() {
        insertFrame(this);
        for (String infoI : info) {
            if (infoI.isEmpty()) {
                JOptionPane.showMessageDialog(null, "빈칸 발생", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        ReservationDAO reservationDAO = new ReservationDAO();
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .mId(info[0])
                .tPass(info[1])
                .atId(info[2])
                .build();
        if (reservationDAO.insert(reservationDTO)) {
            JOptionPane.showMessageDialog(null, "등록 완료");
            this.remove(scrollPane);
            tableLayout();
        } else {
            JOptionPane.showMessageDialog(null, "등록 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void insertFrame(ReservationG before) {
        JFrame inFrame = new JFrame();
        inFrame.setVisible(true);
        setTitle("등록");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel[] labelName = {new JLabel("회원 아이디"), new JLabel("티켓 번호"), new JLabel("놀이기구 번호")};
        JPanel lPanel = new JPanel(new GridLayout(3, 1));
        lPanel.add(labelName[0]);
        lPanel.add(labelName[1]);
        lPanel.add(labelName[2]);
        inFrame.add("WEST", lPanel);
        JPanel tPanel = new JPanel(new GridLayout(3, 1));
        JTextField[] infoField = {new JTextField(10), new JTextField(10), new JTextField(10)};
        tPanel.add(infoField[0]);
        tPanel.add(infoField[1]);
        tPanel.add(infoField[2]);
        inFrame.add("EAST", tPanel);
        JButton inInsert = new JButton("등록");
        inFrame.add("SOUTH", inInsert);
        inInsert.addActionListener(e -> {
            info = new String[]{infoField[0].getText(), infoField[1].getText(), infoField[2].getText()};
            inFrame.dispose();
        });
        inFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                before.setEnabled(true);
                before.toFront();
                before.setFocusable(true);
                before.requestFocusInWindow();
            }
        });

    }

    private void butLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        panel.add(insert);
        panel.add(update);
        panel.add(delete);
        add("South", panel);
    }

    private void tableLayout() {
        table = getList();
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSize(getWidth(), 300);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public JTable getList() {
        ReservationDAO reservationDAO = new ReservationDAO();
        List<ReservationDTO> reservations = reservationDAO.selectAll();
        String[] header = {"예약번호", "회원 아이디", "이용권 번호", "놀이기구 번호", "예약시간"};
        String[][] data = new String[reservations.size()][header.length];
        for (int i = 0; i < reservations.size(); i++) {
            ReservationDTO reservation = reservations.get(i);
            data[i][0] = String.valueOf(reservation.getNo());
            data[i][1] = reservation.getMId();
            data[i][2] = reservation.getTPass();
            data[i][3] = reservation.getAtId();
            data[i][4] = reservation.TimetoString();
        }
        return new JTable(data, header);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insert) {
            insert();
        } else {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "선택한 행 없음", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (e.getSource() == update) {
                update(row);
            } else if (e.getSource() == delete) {
                delete(row);
            }
        }
    }
}