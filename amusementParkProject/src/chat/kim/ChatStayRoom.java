package javaproject.chat.kim;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatStayRoom extends JFrame{
    //gui 부분
    private JButton attractionButton;
    private JButton foodButton;
    private JButton ticketButton;
    private JPanel mainPanel;
    private boolean[] flagList;

    public ChatStayRoom(ChatServerObject chatServerObject){
        flagList = chatServerObject.getFlagList();

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        attractionButton = new JButton("놀이기구 고객센터");
        attractionButton.setBounds(100, 100, 200, 50);
        attractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagList[0]) {
                    new ChatClientObject().service(1004,false,chatServerObject);
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"방풀입니다.","gg",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        foodButton = new JButton("음식점 고객센터");
        foodButton.setBounds(350, 100, 200, 50);
        foodButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagList[1]) {
                    new ChatClientObject().service(1005, false,chatServerObject);
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"방풀입니다.","gg",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        ticketButton = new JButton("티켓 고객센터");
        ticketButton.setBounds(600, 100, 200, 50);
        ticketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagList[2]) {
                    new ChatClientObject().service(1006,false,chatServerObject);
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"방풀입니다.","gg",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        mainPanel.add(attractionButton);
        mainPanel.add(foodButton);
        mainPanel.add(ticketButton);

        add(mainPanel);

        setTitle("고겍센터");
        setBounds(100, 100, 900, 500);
        setVisible(true);

    }

}
