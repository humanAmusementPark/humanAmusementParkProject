package javaproject.chat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client {

	public static void main(String[] args) {
		Runnable a=new ProtocolClient(9500,"id");
		Thread t=new Thread(a);
		
		t.start();

	}

}

class ProtocolClient extends JFrame implements Runnable, ActionListener {
    private JTextArea output;
    private JTextField input;
    private JButton sendBtn;
    private Socket socket;
    private BufferedReader reader = null;
    private PrintWriter writer=null;
    private String nickName;
    private String line;
    int port;
	String id=null;
    
	public ProtocolClient(int port, String id) {
		this.port=port;
		this.id=id;
		output=new JTextArea();
		output.setEditable(false);
		JScrollPane sc=new JScrollPane(output);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        input = new JTextField();

        sendBtn = new JButton("Send");

        bottom.add("Center",input);
        bottom.add("East",sendBtn);


        Container container = this.getContentPane();
        container.add("Center", sc);
        container.add("South", bottom);
		
		
		setBounds(300,300,300,300);
	    setVisible(true);
	    
	    sendBtn.addActionListener(this);
	    
	    this.addWindowListener(new WindowAdapter() {
	    	   public void windowClosing(WindowEvent e) {
		    	    System.out.println("사용자 명령으로 종료합니다.");
		    	    
		    	    
		    	    if(socket!=null) {
		    	    	writer.println("code.exit");  
		    			writer.flush(); 
		    			System.out.println("종료");
		    			try {
		    				writer.close();
		    				reader.close();
		    				socket.close();
		    			} catch (IOException e1) {
		    				// TODO Auto-generated catch block
		    				e1.printStackTrace();
		    			}
		    	    }
		    	    
		    	    
	    	   }
	    });
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

			writer.println(nickName+": "+input.getText());  //서버로 데이터를 전송한다. 
			writer.flush();   //버퍼 안에 있는 값들을 전부 비워준다. 
			System.out.println("데이터 전송 완료!");
			
			
			

	}

	@Override
	public void run() {
		try {
			socket = new Socket("192.168.0.62", port);
			System.out.println("연결성공");
			//클라이언트 -> 소켓
			String name= JOptionPane.showInputDialog(this,"이름을 입력하세요",id);
			if(name==null|| name.length()==0) {
				System.exit(0);
			}
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.println("customer");  //서버로 데이터를 전송한다. 
			writer.flush();   //버퍼 안에 있는 값들을 전부 비워준다. 
			System.out.println("데이터 전송 완료!");
			
			//소켓 -> 클라이언트
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = reader.readLine();
			System.out.println("데이터 받기 성공! :"+line); 
			nickName=name;
			setTitle(nickName);
			output.append("대기중...\n");
			
			String aa=reader.readLine();
			

			System.out.println(aa);

			writer.println("채팅시작");
			writer.flush();

			
			
		} catch (Exception e) {
			System.out.println("서버 연결 불량으로 종료합니다");
			dispose();
			return;
		}
		

		while(!socket.isClosed()) {
			
			try {
				
				String line2=reader.readLine();
				if(line2!=null) {
					line=line2;
					System.out.println(line);
				}
				if(line!=null && line.equals("code.exit")) {
					try {
						writer.close();
						reader.close();
						socket.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//소켓 -> 클라이언트
				else if(line!=null) {
					output.append(line+"\n");
				}
				
					

			} catch (IOException e) {
				output.append("통신종료");
				break;
			}
		}
		
		

		System.out.println("종료");
		
	}
	
}