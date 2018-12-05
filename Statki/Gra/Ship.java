package Gra;
// klasa definiuj¹ca statek

public class Ship {

	// zmienne wspó³rzêdnych pocz¹tku i koñca statku, myœlê ¿e lepiej bêdzie obie wspó³rzêdne traktowaæ jako liczbê
	// tylko wizualnie bêdzie siê prezentowa³o jedn¹ z nich u¿ytkownikowi jako literê
	private int StartX;
	private int StartY;
	private int EndX;
	private int EndY;
	
	private int id; //statek musi mieæ id ja to czujê
	
	private int length; //d³ugoœæ statku
	
	private boolean ShipAlive; //stan ¿ywego statku (niezatopionego)
	private boolean ShipDead; //stan zatopionego statku
	
	//myœla³em nad tym aby statek mia³ liczbê ¿yæ pocz¹tkowo równ¹ d³ugoœci i gdy osi¹gnie 0 wtedy siê zatopi
	//ka¿de trafienie by zmniejsza³o ¿ycie o 1
	
	//zwyk³y konstruktor
	public Ship() {
		this.ShipAlive = true;
		this.ShipDead = false;
	}
	
	//konstruktor z deklaracj¹ d³ugoœci
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
	
	//funkcja sprawdzaj¹ca czy statek jest poprawny (pod wzglêdem wspó³rzêdnych i ich relacji z d³ugoœci¹)
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
