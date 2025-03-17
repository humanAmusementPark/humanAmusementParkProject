import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Server {
	static ArrayList<Handler> hlist1=new ArrayList<>();
	static ArrayList<Handler> hlist2=new ArrayList<>();
	static Handler[][] chat= {new Handler[2],new Handler[2],new Handler[2]};


	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9500); // 포트번호를 9500번으로 지정
			Matching m=new Matching(hlist1,hlist2,chat);
			m.start();
			while (true) {
				System.out.println("연결을 기다리는 중...");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("연결 수락됨" + isa.getHostName());
				
				PrintWriter writer=null;
				BufferedReader reader=null;
				
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				String line = reader.readLine(); 
				writer.println(line);
				writer.flush();
				
				Handler h=new Handler(socket,isa);
				
				if(line.equals("manager")) {
					hlist1.add(h);
					System.out.println("관리자 추가");
				}else if(line.equals("customer")) {
					hlist2.add(h);
					System.out.println("고객추가");
				}
				
				
				
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void delete(int num) {
		System.out.println("채팅"+num+"종료");
		chat[num]=new Handler[2];
	}
}


class Matching extends Thread{
	ArrayList<Handler> hlist1=null;
	ArrayList<Handler> hlist2=null;
	Handler[][] chat=null;
	Matching(ArrayList<Handler> hlist1, ArrayList<Handler> hlist2, Handler[][] chat) {
		this.hlist1=hlist1;
		this.hlist2=hlist2;
		this.chat=chat;
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
			
			if(isEmpty(chat)>-1 && hlist1.size()>0 && hlist2.size()>0) {
				System.out.println("채팅시작");
				int num=isEmpty(chat);
				chat[num][0]=hlist1.remove(0);
				chat[num][1]=hlist2.remove(0);
				chat[num][0].set(chat[num][1],num);
				chat[num][1].set(chat[num][0],num);
				chat[num][0].start();
				chat[num][1].start();
			}
			
		}
	}
	
	private int isEmpty(Handler[][] chat) {
		for(int i=0;i<3;i++) {
			if(chat[i][0]==null) {
				return i;
			}
		}
		return -1;
	}

}
