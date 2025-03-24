package javaproject.chat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // 서버 주소
        int port = 4321; // 포트 번호

        // GUI 초기화
        SwingUtilities.invokeLater(() -> new ChatGUI(serverAddress, port));
    }
}
