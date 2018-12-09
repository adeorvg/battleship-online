package centralServer;

class Game {
	private int gameID;
	private int firstClientID;
	private int secondClientID;
	private boolean started;
	private boolean finished;
	
	Game() {
		gameID = generateID()-5;
		started = true;
	}
	
	public int generateID() {
		int ID = (int) Math.abs(System.currentTimeMillis()+10000*Math.random());
		return ID;
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

	/**
	 * @return the firstClientID
	 */
	public int getFirstClientID() {
		return firstClientID;
	}

	/**
	 * @param firstClientID the firstClientID to set
	 */
	public void setFirstClientID(int firstClientID) {
		this.firstClientID = firstClientID;
	}

	/**
	 * @return the secondClientID
	 */
	public int getSecondClientID() {
		return secondClientID;
	}

	/**
	 * @param secondClientID the secondClientID to set
	 */
	public void setSecondClientID(int secondClientID) {
		this.secondClientID = secondClientID;
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
	public void setFinished(boolean finished) {
		this.finished = finished;
		CentralServer.gamesList.remove(this);
	}
	
	
}
