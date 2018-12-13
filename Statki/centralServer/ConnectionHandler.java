package centralServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Gra.Message;

class ConnectionHandler extends Thread{
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectionHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
        	System.out.println("New Client tries to connect");
			initializeWriterAndReader();
			String input;
			while ((input = in.readLine()) != null) {
				ReceivedMessage receivedMessage = new ReceivedMessage(input);
				if (receivedMessage.isClientIdValid()) proceedBasingOnReceivedMessage(receivedMessage);
				System.out.println("Served client: "+receivedMessage.getClientID());
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
    	Client check = CentralServer.findClientByID(receivedMessage.getClientID());
    	Client client = check != null ? check : new Client(receivedMessage);
    	switch (receivedMessage.getContentType()) {
        case "ng":
    		Game game = new Game();
    		game.setFirstClient(client);
    		client.setTurn(true);
    		CentralServer.gamesList.add(game);
    		System.out.println("Started game: "+game.getGameID()+" with client: "+ receivedMessage.getClientID());
            break;
        case "sp":	
        	client.setBoard(new Board( receivedMessage.getShipsCoordinates() ));
        	System.out.println("Ships placed in game: "+CentralServer.findGameByClient(client).getGameID()+" at client: "+ receivedMessage.getClientID());
            break;
        case "jg":
        	if(receivedMessage.isJoiningGameIdValid()) {
        		Game gameToJoin = CentralServer.findGameByID(receivedMessage.getJoiningGameID());
        		if (gameToJoin != null && gameToJoin.getSecondClient() == null) {
        			gameToJoin.setSecondClient(client);
        		}
	        	System.out.println("Client: "+client.getID()+" joining game: "+gameToJoin.getGameID());
        	}
            break;
        case "sh":
        	Game foundGame = CentralServer.findGameByClient(client);
        	if(client.getTurn() == true && receivedMessage.isTargetValid() && foundGame != null && foundGame.isFinished() == false && client.hasMoved() == false) {
        		Client opponent = foundGame.getOpponent(client);
        		opponent.shot(receivedMessage.getTarget());
    			client.setMoved(true);
        		client.setTurn(false);
        		opponent.setTurn(true);
	        	if(!opponent.getBoard().hasAliveShip()) foundGame.EndGame(client);
	        		
	        	//sendMessage(message);
	        		
        		}
            break;
        default:
        	System.out.println("Received it from: " +receivedMessage.getClientID());
            break;
    	}
    
	}

	public void sendMessage(SentMessage sentMessage) {
		out.flush();
		out.println(sentMessage);
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