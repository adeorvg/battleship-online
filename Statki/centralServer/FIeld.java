//klasy Board, Ship i Field wspoldzialaja ze soba w celu wygenerowania dla klienta planszy po kazdym
//oddaniu strzalu, oraz przy tworzeniu nowej gry

//Stany skopiowano od klienta
//case 4:
//	return typeOfSquare.GREY;	//pole sprawdzone/pudlo
//case 3:
//	return typeOfSquare.BLACK;	//pole statku zniszczonego
//case 2:
//	return typeOfSquare.RED;	//pole statku trafionego
//case 1:	
//	return typeOfSquare.BLUE;	//pole statku zywego
//case 0:
//	return typeOfSquare.WHITE;	//pole puste/niezajete/niesprawdzone

package centralServer;

import java.util.Objects;

class Field {
	private int x;
	private int y;
	private int state = 0;
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(state, x, y);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		return state == other.state && x == other.x && y == other.y;
	}



	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
}