package javaproject.chat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient {


    String serverAddress = "192.168.0.18"; // 서버 주소
    int port = 4321; // 포트 번호
    String role;

    public ChatClient(String role) {
        // GUI 초기화
        this.role = role;
        SwingUtilities.invokeLater(() -> new ChatGUI(serverAddress, port,role));
    }
}