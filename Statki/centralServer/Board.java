//klasy Board, Ship i Field wspoldzialaja ze soba w celu wygenerowania dla klienta planszy po kazdym
//oddaniu strzalu, oraz przy tworzeniu nowej gry
//Board is a square: size x size

package centralServer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Board {
	private int size;
	private Set<Field> fields = new HashSet<>();
	private Set<Ship> ships = new HashSet<>();
	
	public Board(int[][] shipsCoordinates) {
		this.size = shipsCoordinates.length;
		for (int[] row: shipsCoordinates) ships.add(new Ship(row));
		for(int i=0; i<size;i++) {
			for (int j=0; j<size; j++) {
				fields.add(new Field(i,j));
			}
		}
		for (Iterator<Ship> iterator = ships.iterator(); iterator.hasNext();) {
		    Ship ship = iterator.next();
		    setStateOfFieldsUnderWholeShip( ship, 1);
		}
	}
	
	public boolean hasAliveShip() {
		for(Iterator<Ship> iterator = ships.iterator(); iterator.hasNext();) {
			Ship ship = iterator.next();
			if(!ship.isDestroyed()) return true;
		}
		return false;
	}
	
	public void setStateOfFieldsUnderWholeShip(Ship ship, int state) {
		for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
		    Field field = iterator.next();
		    if (ship.getFields().contains(field)) field.setState(state);
		}
	}
	
	public void destroy(Ship ship) {
		setStateOfFieldsUnderWholeShip(ship, 3);
		ship.setDestroyed(true);
	}
	
	public Field getSpecificField(int x, int y) {
		for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
		    Field field = iterator.next();
		    if(field.getX() == x && field.getY() == y ) return field;
		}
		return null;
	}
	
	public Set<Ship> getShips() {
		return ships;
	}
}
