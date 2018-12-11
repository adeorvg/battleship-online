package centralServer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Client {
	private int ID;
	private boolean moved = false;
	private int[][] shipsCoordinates;
	private Set<Ship> ships = new HashSet<>();
	private boolean win = false;
	private boolean loose = false;
	private boolean turn = false;
	
	Client(ReceivedMessage msg){
		ID = msg.getClientID();
	}
	
	public void setShipsCoordinatesAndShips(int[][] shipsCoordinates){
		this.shipsCoordinates = shipsCoordinates;
		for(int[] row:shipsCoordinates) ships.add(new Ship(row));
		
	}
	
	public void shot(String target, Client opponent) {
		for (Iterator<Ship> iterator = opponent.getShips().iterator(); iterator.hasNext();) {
		    Ship ship =  iterator.next();
		    if (ship.getFields().contains(target)) {
		        iterator.remove();
		    }       
		}
	}
	
	boolean hasAnyShipAlive() {
		return !ships.isEmpty();
	}
	
	public Set<Ship> getShips(){
		return ships;
	}
	
	public boolean hasMoved(){
		return moved;
	}
	
	public void setMoved(boolean moved){
		this.moved = moved;
	}
	
	public int[][] getShipsCoordinates(){
		return shipsCoordinates;
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
}
