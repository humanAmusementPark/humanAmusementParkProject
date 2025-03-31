package javaproject.Service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javaproject.DAO.AttractionDAO;
import javaproject.DAO.MemDAO;
import javaproject.DAO.ReservationDAO;
import javaproject.DAO.TicketDAO;
import javaproject.DTO.AttractionDTO;
import javaproject.DTO.ReservationDTO;
import javaproject.urlTool;

public class ReservationS extends JFrame {
    String mId = null;
    AttractionDAO adao = new AttractionDAO();
    ReservationDAO rdao = new ReservationDAO();
    MemDAO mdao = new MemDAO();
    TicketDAO tdao = new TicketDAO();

    JPanel p = new JPanel();
    JLabel name = new JLabel();
    JLabel chaos = new JLabel();
    JButton submit = new JButton("예약하기");

    String[] col = {"남은 예약"};
    DefaultTableModel model = new DefaultTableModel(col, 0) {
        // Jtable 내용 편집 안되게
        public boolean isCellEditable(int i, int c) {
            return false;
        }
    };
    JTable t = new JTable(model);

    public ReservationS(MemMenuS before, String atname, String id) throws SQLException {
        this.setTitle("기구 예약");
        System.out.println("atname =  " + atname);
        AttractionDTO attract = (AttractionDTO) adao.select(atname);
        t.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        t.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

        if (attract == null) {
            before.setEnabled(true);
            this.dispose();
            return;
        }
        String atId = attract.getAtId();
        mId = id; //회원 아이디
        String tpass = mdao.select(mId).getTPass();
        System.out.println("tpass == " + tpass);
        String tname = tdao.select(tpass).getTName();
        if(attract.getAtOnoff() == 0){
            JOptionPane.showMessageDialog(null,"시설 운행 안함");
            before.setEnabled(true);
            this.dispose();
            return;
        }

        int count = 0; //현재 대기인원
        List<ReservationDTO> rlist = null;

        if (tname.equals("vip권")) {
            rlist = rdao.selectvip(atId); //현재 대기인원
            System.out.println("vip");
        } else {
            rlist = rdao.selectatt(atId); //현재 대기인원
            System.out.println("일반");
        }

        count = rlist.size();
        System.out.println(count);

        model.setRowCount(0);
        for (ReservationDTO r : rlist) {
            model.addRow(new Object[]{
                    r.toString(r.getRTime())
            });
        }


        int vcount = 0;

        //ImageIcon icon = new ImageIcon(attract.getAtUrl());
        urlTool utool = new urlTool();
        ImageIcon icon = utool.getImageIcon(attract.getAtUrl());

        this.setSize(icon.getIconWidth() + 150, icon.getIconHeight() + 40); //프레임 크기를 이미지에 맞추기


        JLabel lb1 = new JLabel(icon, JLabel.CENTER); //가운데로 수평정렬
        this.add(lb1);


        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        name.setText("기구명: " + attract.getAtName());
        p.add(name, c);

        c.gridy = 1;
        chaos.setText("대기 인원: " + count);

        p.add(chaos, c);

        c.gridy = 2;
        p.add(submit, c);


        JScrollPane sp = new JScrollPane(t);
        c.gridy = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        p.add(sp, c);

        p.setPreferredSize(new Dimension(150, 300)); // 패널 전체 크기를 제한
        this.add("East", p);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // 화면 중앙 배치

        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ReservationDTO r = ReservationDTO.builder()
                        .no(rdao.getcount(mId))
                        .atId(attract.getAtId())
                        .mId(mId)
                        .tPass(tpass)
                        .build();
                if (rdao.insert(r)) {
                    JOptionPane.showMessageDialog(null, "예약되었습니다.");
                    before.setEnabled(true);
                    dispose();
                }
            }
        });


        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        before.setEnabled(true);
                    }
                }
        );
    }
}