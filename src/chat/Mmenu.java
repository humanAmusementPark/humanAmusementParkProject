package javaproject.chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Mmenu extends JFrame implements ActionListener{
	private String id;
	JButton j1=new JButton("a");
	JButton j2=new JButton("b");
	JButton j3=new JButton("c");
	public static void main(String[] args) {
		new Mmenu("id");

	}

	public Mmenu(String id){
		this.id=id;
		setLayout(new GridBagLayout());
		setTitle("관리자");

		GridBagConstraints c=new GridBagConstraints();
		Dimension d=new Dimension(100,100);
		j1.setPreferredSize(d); // 기본 크기
		j2.setPreferredSize(d);
		j3.setPreferredSize(d);

		c.insets=new Insets(10,10,10,10);
		add(j1,c);
		add(j2,c);
		add(j3,c);
		
		j1.addActionListener(this);
		j2.addActionListener(this);
		j3.addActionListener(this);
		
		this.setVisible(true);
		this.setSize(400, 200);  // 창 크기
		this.setLocationRelativeTo(null); // 화면 중앙 배치
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int port=0;
		if(e.getSource()==j1){
			port=9500;
		}else if(e.getSource()==j2){
			port=9501;
		}else if(e.getSource()==j3){
			port=9502;
		}

		Runnable a=new ProtocolClient2(port,id);
		Thread t=new Thread(a);
		
		t.start();
		
	}
	
	
}
