package javaproject.jungjin.amuse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class sssss extends JFrame {
	sssss(Login login){
		this.setSize(500,500);
		this.setVisible(true);
		 setLocationRelativeTo(null); // 화면 중앙 배치
		addWindowListener (
		    new WindowAdapter() {
		        public void windowClosing(WindowEvent e) {
		        	login.setVisible(true);
		        }
		    }
		);
	}
	
	
	

}
