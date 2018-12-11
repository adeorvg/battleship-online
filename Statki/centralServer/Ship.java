package centralServer;

import java.util.HashSet;
import java.util.Set;

class Ship {
	private Set<String> fields = new HashSet<>();
	boolean destroyed;
	
	//np 21 51 - B1 E1 lub 21 25 - B1 B5
	//zwraca pola statku
	public Ship(int[] row) { 
		if (row.length == 4){
			char beginLetter = intToChar(row[0]);
			int beginNum = row[1];
			char endLetter = intToChar(row[2]);
			int endNum = row[3];
			if (beginLetter-endLetter == 0) {
				for(int i=beginNum; i<endNum; i++) {
					fields.add(beginLetter+""+i);
				}
			} else if(beginNum-endNum == 0) {
				for(int i=beginLetter; i<=endLetter; i++) {
					fields.add(i+""+beginNum);
				}
			}
			destroyed = false;
		}
	}
	
	public boolean isFieldsValid() {
		for (String field:fields) {
			if (field.length()!=2) return false;
		}
		return fields.size() >1 && fields.size() <= 10;
	}
	
	private char intToChar(int x) {
		return (char)(x+64);
	}
	
	boolean hasAnyFieldsAlive() {
		return !fields.isEmpty();
	}
	public Set<String> getFields(){
		return fields;
	}
	public boolean getDestroyed() {
		return destroyed;
	}
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
}