package javaproject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ticketGUI extends JFrame implements ActionListener {
    JButton normal = new JButton("<html><center><b>노멀 패스</b><br>대기 줄을 서서 이용<br><b>가격: 30,000원</b></center></html>");
    JButton magic = new JButton("<html><center><b>매직 패스</b><br>빠르게 입장 가능<br><b>가격: 60,000원</b></center></html>");
    private String userId; // 로그인한 사용자id
    private TicketDAO ticketDAO = new TicketDAO();
    public ticketGUI(String id) throws SQLException {
        this.userId = id;
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setTitle("티켓 구매");
        this.setLayout(new GridLayout(1,2,10,10));
        this.add(normal);
        this.add(magic);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseTicket("노멀패스");
            }
        });
        magic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseTicket("매직패스");
            }
        });

    }
    public void purchaseTicket(String ticket){
        try {
            if(ticketDAO.checkTicket(userId)){
                JOptionPane.showMessageDialog(this, "이미 티켓을 구매하셨습니다!");
                return;
            }
            if(ticket.equals("노멀패스")){
                ticketDAO.normalTicket(userId);
            }else{
                ticketDAO.magicTicket(userId);
            }
            JOptionPane.showMessageDialog(this, ticket + "가 성공적으로 구매되었습니다");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {
        new ticketGUI("alex1676");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
