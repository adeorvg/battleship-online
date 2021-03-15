//klasy Board, Ship i Field wspoldzialaja ze soba w celu wygenerowania dla klienta planszy po kazdym
//oddaniu strzalu, oraz przy tworzeniu nowej gry

package centralServer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Ship {
	private Set<Field> fields = new HashSet<>();
	private boolean destroyed;
	
	public Ship(int[] row) { 
		if (row.length == 4) {
			int beginLetter = row[0];
			int beginNum = row[1];
			int endLetter = row[2];
			int endNum = row[3];
			if (beginLetter-endLetter == 0) {
				for(int i=beginNum; i<=endNum; i++) {
					fields.add(new Field(beginLetter,i));
				}
			} else if(beginNum-endNum == 0) {
				for(int i=beginLetter; i<=endLetter; i++) {
					fields.add(new Field(i,beginNum));
				}
			}
			destroyed = false;
		}
	}
	
	public boolean hasAliveFields() {
		for(Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
			Field field = iterator.next();
			if(field.getState() == 1) return true;
		}
		return false;
	}
	
	public Set<Field> getFields() {
		return fields;
	}
	
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
}
