package javaproject.chat.kim;

import javaproject.DAO.AdminDAO;
import javaproject.DAO.MemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientObject extends JFrame implements Runnable, ActionListener {
    private JTextArea output;
    private JTextField input;
    private JButton sendBtn;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String nickName;

    private boolean checkAdmin;
    private boolean[] checkAdminList;

    private int portNum;

    private Socket socketTemp;

    public ChatClientObject() {
        //센터 TextArea만들기
        output = new JTextArea();
        output.setEditable(false);
        //스크롤바 부분
        JScrollPane scroll = new JScrollPane(output);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  //항상 스크롤바가 세로로

        //남쪽에 버튼과 TextArea넣기
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        input = new JTextField();

        sendBtn = new JButton("Send");

        bottom.add("Center", input);
        bottom.add("East", sendBtn);

        //Container에 붙이기
        Container container = this.getContentPane();
        container.add("Center", scroll);
        container.add("South", bottom);

        //윈도우 창 설정
        setBounds(300, 300, 300, 300);
        setVisible(true);

        //윈도우 이벤트
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
                try {
                    ChatDTO dto = new ChatDTO();
                    dto.setNickName(nickName);
                    dto.setCommand(Info.EXIT);

                    writer.writeObject(dto);
                    writer.flush();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });


    }


    public void service(Socket socket, ObjectOutputStream writer, ObjectInputStream reader, int portNum, boolean checkAdmin, boolean[] checkAdminList, String id) {
        this.checkAdmin = checkAdmin;
        this.portNum = portNum;
        //플레그 가져와서 사용
        this.checkAdminList = checkAdminList;
        socketTemp = socket;

        //db에서 이름 받아 오기
        if (checkAdmin) {  //chatAdminister 에서 true로 바꿔지면 관리자로 인식
            AdminDAO adminDAO = new AdminDAO();
            nickName = adminDAO.select(id).getAName();
        } else {
            MemDAO memDAO = new MemDAO();
            if (memDAO.select(id) == null) {
                nickName = "guest";
            } else {
                nickName = memDAO.select(id).getMName();
            }
        }

        //에러 발생
        this.writer = writer;
        this.reader = reader;


        System.out.println("전송 준비 완료!");


        System.out.println("서버와 연결이 안되었습니다.");


        try {
            //서버로 닉네임 보내기

            ChatDTO dto = new ChatDTO();
            dto.setCommand(Info.JOIN);

            dto.setNickName(nickName);
            writer.writeObject(dto);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //스레드 생성
        //run 부분에서 서버에서 데이터 받아오는 동안 만약에 내가 action을 해서 서버에 보낸다면 이떄
        //쓰레드를 나누어 처리를 해야한다.
        Thread t = new Thread(this);
        t.start();

        input.addActionListener(this);
        sendBtn.addActionListener(this);  //멕션 이벤트 추가
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            //서버로 보냄
            //JTextField값을 서버로보내기
            //버퍼 비우기
            String msg = input.getText();
            ChatDTO dto = new ChatDTO();
            if (msg.equals("exit")) {
                dto.setCommand(Info.EXIT);
            } else {
                dto.setCommand(Info.SEND);
                dto.setMessage(msg);
                dto.setNickName(nickName);
            }

            writer.writeObject(dto);
            writer.flush();
            input.setText("");

        } catch (IOException io) {
            io.printStackTrace();
        }

    }


    @Override
    public void run() {
        //서버로부터 데이터 받기
        ChatDTO dto = null;
        while (true) {
            try {
                dto = (ChatDTO) reader.readObject();
                if (dto.getCommand() == Info.EXIT) {  //서버로부터 내 자신의 exit를 받으면 종료됨
                    reader.close();
                    writer.close();
                    socketTemp.close();
                    //admin이 종료되었을때 checkAdmin을 false로
                    if (checkAdmin) {
                        switch (portNum) {
                            case 1004:
                                checkAdminList[0] = false;
                                break;
                            case 1005:
                                checkAdminList[1] = false;
                                break;
                            case 1006:
                                checkAdminList[2] = false;
                                break;
                        }
                    }
                    return;
                } else if (dto.getCommand() == Info.SEND) {
                    output.append(dto.getMessage() + "\n");

                    int pos = output.getText().length();
                    output.setCaretPosition(pos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
