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
	InetSocketAddress i=null;
	PrintWriter writer=null;
	BufferedReader reader=null;
	int num=0;
	
	public Handler(Socket socket, InetSocketAddress isa) {
		s=socket;
		i=isa;
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
		while(true) {
			try {
				String line = reader.readLine(); 
				if(line.equals("code.exit")) {
					h.prt("상대가 채팅을 종료하였습니다.");
					h=null;
					Server.delete(num);
					
					break;
				}
				h.prt(line);
				prt(line);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
	
	private void prt(String line) {
		writer.println(line);
		writer.flush();
	}

	public void set(Handler h,int num) {
		this.h=h;
		this.num=num;
	}
	
	
}
