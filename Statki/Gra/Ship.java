package Gra;
// klasa definiuj�ca statek

public class Ship {

	// zmienne wsp�rz�dnych pocz�tku i ko�ca statku, my�l� �e lepiej b�dzie obie wsp�rz�dne traktowa� jako liczb�
	// tylko wizualnie b�dzie si� prezentowa�o jedn� z nich u�ytkownikowi jako liter�
	private int StartX;
	private int StartY;
	private int EndX;
	private int EndY;
	
	private int id; //statek musi mie� id ja to czuj�
	
	private int length; //d�ugo�� statku
	
	private boolean ShipAlive; //stan �ywego statku (niezatopionego)
	private boolean ShipDead; //stan zatopionego statku
	
	//my�la�em nad tym aby statek mia� liczb� �y� pocz�tkowo r�wn� d�ugo�ci i gdy osi�gnie 0 wtedy si� zatopi
	//ka�de trafienie by zmniejsza�o �ycie o 1
	
	//zwyk�y konstruktor
	public Ship() {
		this.ShipAlive = true;
		this.ShipDead = false;
	}
	
	//konstruktor z deklaracj� d�ugo�ci
	public Ship(int length) {
		this.length = length;
		this.ShipAlive = true;
		this.ShipDead = false;
	}
	
	//przypisanie
	public void setCoordinates(int StartX, int StartY, int EndX, int EndY) {
		this.StartX = StartX;
		this.StartY = StartY;
		this.EndX = EndX;
		this.EndY = EndY;
	}
	
	//funkcja sprawdzaj�ca czy statek jest poprawny (pod wzgl�dem wsp�rz�dnych i ich relacji z d�ugo�ci�)
	public boolean checkShip() {
		if(StartX == EndX) {
			if(Math.abs(EndY-StartY)==length-1) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(StartY == EndY){
			if(Math.abs(EndX-StartX)==length-1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
