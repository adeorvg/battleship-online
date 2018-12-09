package centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


class CentralServer {
	private ServerSocket serverSocket = null;
	static List<ConnectionHandler> connectionsList = new ArrayList<ConnectionHandler>();
	static List<Game> gamesList = new ArrayList<Game>();
	
    public void start(int port){
        try {
			serverSocket = new ServerSocket(port);
			while (true) {
				ConnectionHandler ch = new ConnectionHandler(serverSocket.accept());
				ch.start();
				connectionsList.add(ch);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void stop() {
    	try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}

