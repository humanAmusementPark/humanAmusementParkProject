package javaproject.Service;


import javaproject.DAO.ReservationDAO;
import javaproject.DTO.ReservationDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

// 회원, 관리자 예약현황 둘다 전체 조회 삭제만 가능
public class MemReservationS extends JFrame implements ActionListener {

    private JButton update = new JButton("수정");
    private JButton delete = new JButton("취소");
    private JTable table = null;
    private String[] info;
    private JScrollPane scrollPane;
    private String id;

    public MemReservationS(String id, MemMenuS before) {
        this.id = id;
        setTitle("예약현황");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableLayout(id);
        butLayout();

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
            table.setModel(getList(id).getModel());
            table.getColumn("예약시간").setPreferredWidth(150);
        } else {
            JOptionPane.showMessageDialog(null, "수정 실패", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void update(int row) {
        String rTime = table.getValueAt(row, 3).toString()+":00";
        ReservationDAO reservationDAO = new ReservationDAO();
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .no(Integer.parseInt(table.getValueAt(row, 0).toString()))
                .mId(id)
                .tPass(table.getValueAt(row, 1).toString())
                .atId(table.getValueAt(row, 2).toString())
                .rTime(Timestamp.valueOf(rTime))
                .build();
        if (reservationDAO.update(reservationDTO)) {
            JOptionPane.showMessageDialog(null, "수정 완료");
            table.setModel(getList(id).getModel());
            table.getColumn("예약시간").setPreferredWidth(150);
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

    private void tableLayout(String id) {
        table = getList(id);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSize(getWidth(), 300);
        table.getColumn("예약시간").setPreferredWidth(150);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public JTable getList(String id) {
        ReservationDAO reservationDAO = new ReservationDAO();
        List<ReservationDTO> reservations = reservationDAO.selectAll(id);
        String[] header = {"예약번호", "이용권 번호", "놀이기구 번호", "예약시간"};
        String[][] data = new String[reservations.size()][header.length];
        for (int i = 0; i < reservations.size(); i++) {
            ReservationDTO reservation = reservations.get(i);
            data[i][0] = String.valueOf(reservation.getNo());
            data[i][1] = reservation.getTPass();
            data[i][2] = reservation.getAtId();
            data[i][3] = reservation.TimetoString();
        }
        DefaultTableModel mod = new DefaultTableModel(data, header) {
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                } else if (column == 1) {
                    return false;
                }
                return true;
            }
        };
        return new JTable(mod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
