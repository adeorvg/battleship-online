package centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


class CentralServer {
	private ServerSocket serverSocket = null;
	public static List<ConnectionHandler> connectionsList = new ArrayList<ConnectionHandler>();
	public static List<Game> gamesList = new ArrayList<Game>();
	
	public static Game findGameByID(int gameID) {
		for(Game game:gamesList) {
			if (game.getGameID() == gameID) {
				return game;
			}
		}
		return null;
	}
	
	public static Game findGameByClient(Client client) {
		for(Game game:gamesList) {
			Client client1 = game.getFirstClient();
			Client client2 = game.getSecondClient();
			if (game.getFirstClient() != null && game.getFirstClient().getID() == client.getID()
					|| game.getSecondClient() != null && game.getSecondClient().getID() == client.getID()) 
				return game;
			}
		return null;
	}
	
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

