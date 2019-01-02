package centralServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class CentralServer {
	private ServerSocket serverSocket = null;
	public static List<ConnectionHandler> connectionsList = new ArrayList<ConnectionHandler>();
	public static List<Game> gamesList = new ArrayList<Game>();
	public static Map<Integer, PrintWriter> outStreams = new HashMap<Integer,PrintWriter>();
	
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
		if (!gamesList.isEmpty()) {
			for(Game game:gamesList) {
				if (game.getFirstClient() != null && game.getFirstClient().getID() == clientID)
					return game.getFirstClient();
				else if (game.getSecondClient() != null && game.getSecondClient().getID() == clientID)
					return game.getSecondClient();		
			}
		}
		return null;
	}
	public static PrintWriter findOutStreamByClient(int clientID) {
		if (!outStreams.isEmpty()) {
			PrintWriter out = outStreams.get((Integer)clientID);
			return out;
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

