package javaproject;

import javaproject.Service.LoginG;
import javaproject.Service.MemG;
import javaproject.Service.ReservationG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Map extends JFrame implements ActionListener {
    String imagePath = "C:\\Users\\hu-07\\Desktop\\qqqqq\\kkk.jpg";
    private Image image = new ImageIcon(imagePath).getImage();
    private String id;
    private LoginG LoginG;
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };

    public Map(String  id, LoginG loginG) {
//        //정규형꺼 일정표
//        new TimeTable();
        this.LoginG = loginG;
        this.id = id;
        this.setTitle("정규랜드 지도");
        this.setSize(1200, 7000);
        this.setLocation(0, 0);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jpImage);

        reservationBnt();
        logManageBnt();
        ticketBnt();
        logoutBnt();
        this.setVisible(true);
        add();
//        mouseCheck();

    }
    public void add(){
        addButton2("",0,0,1,1);
        addButton2("",2,2,1,1);
        addButton2("",3,3,1,1);
        addButton2("",6,6,1,1);
        addButton2("",8,8,1,1);
        addButton2("",10,10,1,1);



        addButton2("티익스프레스",396,81,130,195);
        addButton2("롤링멕스트레인",707,380,67,120);
        addButton2("콜롬버스",950,366,94,120);
        addButton2("범퍼카",712,284,60,50);
        addButton2("이솝빌리지",828,343,80,70);
        addButton2("썬더홀스",862,138,90,130);
        addButton2("사파리월드",209,514,140,100);
        addButton2("로스트발리",282,641,110,70);
        addButton2("더블락스핀",664,514,45,60);
        addButton2("버드파라다이스",587,695,60,60);
        addButton2("판다월드",599,526,44,52);
        addButton2("애니멀원더월드",527,596,70,80);
        addButton2("씨라이언스타디움",449,675,57,57);
        addButton2("애니멀원더스테이지",504,456,56,100);
        addButton2("카니발광장",479,381,62,57);
        addButton2("food1",625,63,80,49);
        addButton2("food2",657,137,90,57);
        addButton2("food3",605,203,80,34);
        addButton2("food4",547,266,93,42);
    }
    public void reservationBnt(){
        JButton rbnt = new JButton("예약현황");
        rbnt.setBounds(300,6,398-300,39-6);


        rbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new ReservationG();
            }
        });
        jpImage.add(rbnt);
    }
    public void logManageBnt(){
        JButton lbnt = new JButton("회원정보수정");
        lbnt.setBounds(910,45,570-533,36-6);

        lbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new MemG();
            }
        });

        jpImage.add(lbnt);
    }
    public void logoutBnt(){
        JButton lobnt = new JButton("로그아웃");
        lobnt.setBounds(1000,45,620-533,36-6);

        lobnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                dispose();
                LoginG.setVisible(true);
            }
        });

        jpImage.add(lobnt);
    }
    public void ticketBnt(){
        JButton tbnt = new JButton("티켓구매");
        tbnt.setBounds(33,813,234-33,922-813);
        tbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                try {
                    new ticketGUI(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        jpImage.add(tbnt);
    }


//    public void mouseCheck(){
//        jpImage.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int mouseX = e.getX();
//                int mouseY = e.getY();
//                System.out.println("마우스 클릭 좌표: X = " + mouseX + ", Y = " + mouseY);
//            }
//        });
//    }

    public void addButton2(String name , int x , int y , int width , int height){
        JButton jbnt = new JButton();
        jbnt.setText("");
        jbnt.setBounds(x,y,width,height);
        jbnt.setBorder(BorderFactory.createEmptyBorder());
        jbnt.setContentAreaFilled(false);
        jpImage.add(jbnt);
        jbnt.addActionListener(createButtonListener(name));


    }
    private ActionListener createButtonListener(String rideName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 클릭된 버튼을 가져옴
                JButton clickedButton = (JButton) e.getSource();  // 클릭된 버튼 객체
                System.out.println("클릭된 버튼: " + clickedButton);  // 버튼 객체

                System.out.println("놀이기구 이름: " + rideName );


//               임시 sendDataToTeamMember(rideName, rideId, clickedButton);  // 정진형한테 전달
            }
        };
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

//    public static void main(String[] args) {
//        new Map();
//    }
}
