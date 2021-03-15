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
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	private PrintWriter pw;
	private BufferedReader br;
	private boolean connected;
	private static final int port = 8080;
	private static final int clientID = generateID();

	//localhost
	public ClientSocket(){
		this.serverIP = "localhost";
	}

	public ClientSocket(String serverIP){
		this.serverIP = serverIP;
	}

	public static int generateID() {
		int ID = (int) Math.abs(System.currentTimeMillis());
		return ID;
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
		//TODO info dla serwera
		System.out.println("<Client closed>");
	}

	public boolean sendMessage(Message message) {
		pw.flush();
		pw.println(clientID + "#" + message.toString());
		return true;
	}

	public String receiveMessage() {
		String message;
		try {
			message = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			message = "error";
		}
		return message;
	}


	public int getPort() {
		return port;
	}

	public int getId()
	{
		return clientID;
	}

	public boolean isConnected() {
		return connected;
	}
}
