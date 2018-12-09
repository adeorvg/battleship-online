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
				//TODO sprawdzenie czy receivedMessage isValid albo przynajmniej type
				proceedBasingOnReceivedMessage(receivedMessage);
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
    	switch (receivedMessage.getContentType()) {
    	//ng - new game; sp - ships placement info; jg - join game; sh - shot
        case "ng":
        	if(receivedMessage.isClientIdValid()) {
        		Game game = new Game();
        		game.setFirstClientID(receivedMessage.getClientID);
        		CentralServer.gamesList.add(game);
        		System.out.println("Started game: "+game.getGameID()+" with client: "+ receivedMessage.getClientID);
        	}
            break;
        case "sp":	
        	//TODO
        	System.out.println("Ships placed in game: "+game.getGameID()+" at client: "+ receivedMessage.getClientID);
            break;
        case "jg":
        	if(receivedMessage.isGameIdValid()) {
	        	for(Game tempGame:CentralServer.gamesList) {
	        		if(receivedMessage.getJoiningGameID == tempGame.getGameID() ) {
	        			tempGame.setSecondClientID(receivedMessage.getClientID());
	        			System.out.println("Joining game: "+tempGame.getGameID());
	        		}
	        	}
        	}
            break;
        case "sh":
        	if(receivedMessage.isTargetValid) {
        		for(Game tempGame:CentralServer.gamesList) {
	        		if(receivedMessage.getClientID == tempGame.getFirstClientID(){
	        			//sprawdz plansze drugiego klienta
	        		} else (receivedMessage.getClientID == tempGame.getSecondClientID() ) {
	        			//sprawdz plansze pierwszego klienta
	        		} 
	        	}
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