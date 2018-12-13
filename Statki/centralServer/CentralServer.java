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
			if (game.getFirstClient() != null && game.getFirstClient().getID() == client.getID()
					|| game.getSecondClient() != null && game.getSecondClient().getID() == client.getID()) 
				return game;
			}
		return null;
	}
	
	public static Client findClientByID(int clientID) {
		for(Game game:gamesList) {
			if (game.getFirstClient() != null && game.getFirstClient().getID() == clientID)
				return game.getFirstClient();
			else if (game.getSecondClient() != null && game.getSecondClient().getID() == clientID)
				return game.getSecondClient();		
		}
		return null;
	}
	
    public void start(int port){
        try {
			serverSocket = new ServerSocket(port);
			System.out.println("Started at port: "+port);
			while (true) {
				ConnectionHandler ch = new ConnectionHandler(serverSocket.accept());
				ch.start();
				connectionsList.add(ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void stop() {
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}

