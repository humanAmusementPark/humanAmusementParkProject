package javaproject.chat.gui;

import javaproject.Service.AdMenuS;

import javax.swing.*;

public class ChatClient {


//    String serverAddress = "192.168.0.18"; // 서버 주소
    String serverAddress = "localhost";
    int port = 4321; // 포트 번호


    public ChatClient(String role) {
        // GUI 초기화
       // SwingUtilities.invokeLater(() -> new ChatGUI(serverAddress, port, role));
        // Swing GUI 생성, invokeLater => EDT, 이벤트 디스패치 스레드, Swing은 단일 스레드 모델을 사용한다.
    }
}