package Gra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
	private String serverIP;
	private int port;
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	private PrintWriter pw;
	private BufferedReader br;
	private boolean connected;
	
	public ClientSocket(){ //localhost & random Port
		this.serverIP = "localhost";
		this.port = randomPort();
	}
	
	public ClientSocket(String serverIP){ //random Port
		this.serverIP = serverIP;
		this.port = randomPort();
	}
	
	public ClientSocket(String serverIP, int port){
		this.serverIP = serverIP;
		this.port = port;
	}
	
	private static int randomPort() {
		return 1024+(int)Math.random()*64511; //ports from 1024 to 65535
	}
	
	public void openConnection() {
		try {
			socket = new Socket(serverIP, port);
			os = socket.getOutputStream();
			pw = new PrintWriter(os, true);//to server
			is = socket.getInputStream();//from server
			br = new BufferedReader(new InputStreamReader(is));
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
		} 
	}
	
	public void closeConnection() throws IOException {
		pw.close();
		os.close();
		socket.close();
		connected = false;
		System.out.println("<Client closed>");
	}
	
	public boolean sendMessage(Message message) {
		pw.flush();
		pw.println(message);
		return true;
	}
	
	public String receiveMessage() {
		String message = "error";
		try {
			message = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isConnected() {
		return connected;
	}
}