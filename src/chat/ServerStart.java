package javaproject.chat;

public class ServerStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server s1=new Server(9500);
		Server s2=new Server(9501);
		Server s3=new Server(9502);
		
		s1.start();
		s2.start();
		s3.start();
	}

}
