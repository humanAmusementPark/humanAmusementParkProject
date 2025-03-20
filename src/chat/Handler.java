package javaproject.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Handler extends Thread{
	Handler h=null;
	Socket s=null;

	PrintWriter writer=null;
	BufferedReader reader=null;
	Matching m=null;
	
	public Handler(Socket socket) {
		s=socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		
		prt("채팅시작");
		boolean run=true;
		String line=null;
		while(run) {
			System.out.println("활상됨");
			try {
				line = reader.readLine(); 
				System.out.println(line);
				if(line.equals("code.exit")) {
					h.prt("상대가 채팅을 종료하였습니다.");
					h.prt(line);
					prt(line);
					h=null;
					m.delete(this);
					run=false;
					break;
				}
				h.prt(line);
				prt(line);
			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				//e.printStackTrace();
				prt(line);
				h=null;
				m.delete(this);
				run=false;
				break;
			}
		}
		System.out.println("종료됨");
		try {
			writer.close();
			reader.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void prt(String line) {
		writer.println(line);
		writer.flush();
	}

	public void set(Handler h, Matching m) {
		this.h=h;
		this.m=m;
	}

	public boolean Conn() {
		try {

			writer.println("채팅시작");
			writer.flush();
			String line = reader.readLine();
			System.out.println(line);
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}
}
