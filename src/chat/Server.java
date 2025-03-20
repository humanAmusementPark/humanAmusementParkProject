package javaproject.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Server extends Thread{
	ArrayList<Handler> hlist1=new ArrayList<>();
	ArrayList<Handler> hlist2=new ArrayList<>();
	ServerSocket serverSocket=null;
	
	Server(int port) {
		try {
			serverSocket = new ServerSocket(port); // 포트번호 지정
			Matching m=new Matching(hlist1,hlist2);
			m.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String line=null;
		while (true) {
			System.out.println("연결을 기다리는 중...");
			Socket socket;
			try {
				socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("연결 수락됨" + isa.getHostName());
				
				PrintWriter writer=null;
				BufferedReader reader=null;
				try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					line = reader.readLine(); 
					writer.println(line);
					writer.flush();
				} catch (Exception e) {
					System.out.println("연결종료");
				}
				
				Handler h=new Handler(socket);
				
				if(line.equals("manager")) {
					hlist1.add(h);
					System.out.println("관리자 추가");
				}else if(line.equals("customer")) {
					hlist2.add(h);
					System.out.println("고객추가");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}


class Matching extends Thread{
	ArrayList<Handler> hlist1=null;
	ArrayList<Handler> hlist2=null;
	int count=0;
	
	Matching(ArrayList<Handler> hlist1, ArrayList<Handler> hlist2) {
		this.hlist1=hlist1;
		this.hlist2=hlist2;
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if( count==0 && hlist1.size()>0 && hlist2.size()>0) {
				if(check()) {
					System.out.println("채팅시작");
					Handler mgr=hlist1.remove(0);
					Handler ctm=hlist2.remove(0);
					count=2;
					mgr.set(ctm,this);
					ctm.set(mgr,this);
					
					mgr.start();
					ctm.start();
					
				}
			}

			
		}
	}

	private boolean check() {
		for(int i=0;i<hlist1.size();i++) {
			if(hlist1.get(0).Conn()) {
				break;
			}else {
				hlist1.remove(0);
			}
		}
		for(int i=0;i<hlist2.size();i++) {
			if(hlist2.get(0).Conn()) {
				break;
			}else {
				hlist2.remove(0);
			}
		}
		return hlist1.size()>0 && hlist2.size()>0;
	}

	public void delete(Handler handler) {
		count--;
		hlist1.remove(handler);
		hlist2.remove(handler);
	}

}
