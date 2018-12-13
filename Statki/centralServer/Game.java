package centralServer;

class Game {
	private int gameID;
	private Client firstClient;
	private Client secondClient;
	private boolean started;
	private boolean finished;
	
	Game() {
		gameID = generateID()-5;
		started = true;
		System.out.println("new game started: "+gameID);
	}
	
	private static int generateID() {
		int ID = (int) Math.abs(System.currentTimeMillis()+10000*Math.random());
		return ID;
	}

	public Client getOpponent(Client client) {
		if(client.getID() == firstClient.getID()) {
			return secondClient;
		} else if (client.getID() == secondClient.getID()) {
			return firstClient;
		}
		return null;
	}
	
	/**
	 * @return the gameID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * @param gameID the gameID to set
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public Client getFirstClient() {
		return firstClient;
	}
	
	public void setFirstClient(Client firstClient) {
		this.firstClient = firstClient;
	}
	
	public Client getSecondClient() {
		return secondClient;
	}
	
	public void setSecondClient(Client secondClient) {
		this.secondClient = secondClient;
	}
	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * @param started the started to set
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished the finished to set
	 */
	
	public void EndGame(Client winner) {
		winner.setWin(true);
		this.getOpponent(winner).setLoose(true);
		finished = true;
		CentralServer.gamesList.remove(this);
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
}
