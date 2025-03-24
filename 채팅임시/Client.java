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
		Runnable a=new ProtocolClient();
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
    
	public ProtocolClient() {
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
	    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	    
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
		    	    
		    	    System.exit(0);
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
			socket = new Socket("192.168.0.62", 9500);
			System.out.println("연결성공");
			//클라이언트 -> 소켓
			String name= JOptionPane.showInputDialog(this,"이름을 입력하세요","정진");
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
			
			output.append("대기중...\n");
			
			
		} catch (Exception e) {
			System.out.println("서버 연결 불량으로 종료합니다");
			System.exit(0);
		}
		

		while(!socket.isClosed()) {
			
			try {
				
				String line2=reader.readLine();
				if(line2!=null) {
					line=line2;
				}
				//소켓 -> 클라이언트
				if(line!=null) {
					output.append(line+"\n");
				}
				if(line!=null && line.equals("code.exit"))
					break;

			} catch (IOException e) {
			
				output.append("통신종료");
				break;
			}
		}
		
		

		System.out.println("종료");
		try {
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("gsfsdg");
			e.printStackTrace();
		}
	}
	
}