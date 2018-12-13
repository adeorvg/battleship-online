package centralServer;

import java.util.Iterator;

class Client {
	private int ID;
	private boolean moved = false;
	private Board board;
	private boolean win = false;
	private boolean loose = false;
	private boolean turn = false;

	Client(ReceivedMessage msg){
		ID = msg.getClientID();
	}
	
	public void shot(String target) {
		Field targetField = convertLetterAndNumToField(target);
		for(Iterator<Ship> iterator = board.getShips().iterator(); iterator.hasNext();) {
		    Ship ship =  iterator.next();
		    int state = ship.getFields().contains(targetField) ? 2 : 4;
		    board.getSpecificField( targetField.getX(), targetField.getY() ).setState(state);
		    if (!ship.hasAliveFields()) board.destroy(ship);
		}
	}
	
	private Field convertLetterAndNumToField(String target) {
		int letter = (int)target.charAt(0)-64;
		int number = target.charAt(1);
		return new Field(letter,number);
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public boolean hasMoved(){
		return moved;
	}
	
	public void setMoved(boolean moved){
		this.moved = moved;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getID() {
		return ID;
	}
	public void setWin(boolean win) {
		this.win=win;
	}
	public boolean getWin() {
		return win;
	}
	public void setLoose(boolean loose) {
		this.loose=loose;
	}
	public boolean getLoose() {
		return loose;
	}
	public boolean getTurn() {
		return turn;
	}
	public void setTurn(boolean turn) {
		this.turn=turn;
	}
	public Board getBoard() {
		return board;
	}
}
