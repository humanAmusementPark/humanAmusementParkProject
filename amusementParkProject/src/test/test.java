package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class test extends JFrame implements ActionListener , KeyListener {

    //필요한 필드 정의 하기
    private JTextField inputName;
    private DefaultListModel<String> model;
    private JList jList;

    //생성자
    public test() {
        setSize(800,500);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // BorderLayout 객체 전달하기
        setLayout(new BorderLayout());

        //패널에 추가할 ui를 미리 만든다.
        JLabel label = new JLabel("Enter your name");
        inputName = new JTextField(10);
        JButton addBtn = new JButton("추가");
        addBtn.addActionListener(this);

        //삭제 버튼
        JButton deleteBtn = new JButton("삭제 ");
        deleteBtn.setActionCommand("delete");
        deleteBtn.addActionListener(this);

        //패널에 UI 를 추가하고
        JPanel topPanel = new JPanel();
        topPanel.add(label);
        topPanel.add(inputName);
        topPanel.add(addBtn);
        topPanel.add(deleteBtn);

        //패널째로 프레임의 상단에 추가하기
        add(topPanel, BorderLayout.NORTH);

        //목록을 출력할 UI
        jList = new JList<>();
        //jList 에 출력할 데이터를 가지고 있는 모델 객체
        model = new DefaultListModel<>();
        model.addElement("하하하하하");
        model.addElement("여보세요나야");
        model.addElement("거기 잘 지내니");

        //모델을 패널에 jlist 넣어주기
        JScrollPane sc = new JScrollPane(jList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //프레임에 스크롤 패널 추가하기
        add(sc, BorderLayout.CENTER);

        //JTextField 에 키 리스너 등록하기
        inputName.addKeyListener(this);
        setVisible(true);




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 이벤트가 발생한 버튼에 설정된 action command 읽어오기
        String command = e.getActionCommand();
        if (command.equals("add")) {
            //JTextField 에 입력한 문자열을 읽어와서 DefaultListModel 객체에 추가해야 한다.
            String name=inputName.getText();
            model.addElement(name);
            inputName.setText("");
        }else if(command.equals("delete")) {
            //JList 의 메소드를 이용해서 선택된 행을 알아낸다.
            int index=jList.getSelectedIndex();
            //만일 선택된 인덱스가 있다면
            if(index >= 0) {
                //모델에서 해당 인덱스를 삭제한다.
                model.remove(index);
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //눌러진 키의 코드값을 읽어와서
        int code=e.getKeyCode();
        //엔터키의 코드값인지 확인한다.
        if(code == KeyEvent.VK_ENTER) {//만일 엔터키를 눌렀다면
            //JTextField 에 입력한 문자열을 읽어와서 DefaultListModel 객체에 추가해야 한다.
            String name=inputName.getText();
            model.addElement(name);
            inputName.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        new test();
    }
}
