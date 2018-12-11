package centralServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ConnectionHandler extends Thread{
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectionHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
			initializeWriterAndReader();
			String input;
			while ((input = in.readLine()) != null) {
				ReceivedMessage receivedMessage = new ReceivedMessage(input);
				if (receivedMessage.isClientIdValid()) proceedBasingOnReceivedMessage(receivedMessage);
			    out.println(input);
			    //TODO if every ship destroyed  break
			}

			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void initializeWriterAndReader() throws IOException {
    	out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(
		  new InputStreamReader(clientSocket.getInputStream()));
    }
    
    public synchronized void proceedBasingOnReceivedMessage(ReceivedMessage receivedMessage) {
    	Client client = new Client(receivedMessage);
    	switch (receivedMessage.getContentType()) {
        case "ng":
    		Game game = new Game();
    		game.setFirstClient(client);
    		client.setTurn(true);
    		CentralServer.gamesList.add(game);
    		System.out.println("Started game: "+game.getGameID()+" with client: "+ receivedMessage.getClientID());
            break;
        case "sp":	
        	client.setShipsCoordinatesAndShips(receivedMessage.getShipsCoordinates());
        	System.out.println("Ships placed in game: "+CentralServer.findGameByClient(client).getGameID()+" at client: "+ receivedMessage.getClientID());
            break;
        case "jg":
        	if(receivedMessage.isJoiningGameIdValid()) {
        		Game gameToJoin = CentralServer.findGameByID(receivedMessage.getJoiningGameID());
        		if (gameToJoin != null && gameToJoin.getSecondClient() != null) {
        			gameToJoin.setSecondClient(client);
        		}
	        	System.out.println("Client: "+client.getID()+" joining game: "+gameToJoin.getGameID());
        	}
            break;
        case "sh":
        	Game foundGame = CentralServer.findGameByClient(client);
        	if(client.getTurn() == true && receivedMessage.isTargetValid() && foundGame != null && foundGame.isFinished() == false && client.hasMoved() == false) {
        		Client opponent = foundGame.getOpponent(client);
        		client.shot(receivedMessage.getTarget(), opponent);
        		client.setMoved(true);
        		client.setTurn(false);
        		opponent.setTurn(true);
        		if(!opponent.hasAnyShipAlive()) foundGame.EndGame(client);
        		//TODO send response to client
        	}
            break;
        default:
        	System.out.println("Received it from: " +receivedMessage.getClientID());
            break;
    	}
    }
    
	public void closeConnection() {
		try {
			clientSocket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		System.out.println("Closed connection");
	}
    
}