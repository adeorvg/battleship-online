package Gra;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SidePanelPreGame extends JPanel{
	
	private int[] MyShips;                  //tablica przechowuj¹ca d³ugoœci statków - jej d³ugoœæ to iloœæ statków
	private int[][] Coordinates;            //tablica dwuwymiarowa przechowuj¹ca wspó³rzêdne pocz¹tku i koñca statków
	                                        //pierwszy wymiar tablicy to który statek, a drugi to po kolei: StartX, StartY, EndX, EndY
	private boolean[][] FreeField;          //tablica stanów (pocz¹tkowo ca³a TRUE) na której bêdziemy zapamiêtywaæ jakie pola s¹ ju¿ zajête przez statki (TRUE = PUSTE)
	
	private JLabel YourShipsInfoLabel;      //tylko tekst "Twoje Statki:"
	private JTextArea AvailableShipsArea;   //TextArea prezentuj¹ca listê posiadanych statków
	private JTextArea SetShipsInfoArea;     //tylko tekst "Ustaw swoje statki podaj¹c wspó³rzêdne..."
	private JLabel SetTheShipLabel;         //Label prezentuj¹cy, który obecnie statek ustawiamy 
	private JTextArea StartCoordinates;     //TextArea do której wpisujemy pocz¹tkowe wspó³rzêdne
	private JTextArea EndCoordinates;       //TextArea do której wpisujemy koñcowe wspo³rzêdne
	private JButton SetShipButton;          //Guzik zatwierdzaj¹cy wpisane wspó³rzêdne i zapisuj¹cy je do 'Coordinates'
	private JLabel CoordinatesInfo;         //Label ukazuj¹cy czy podane wspó³rzêdne s¹ poprawne
	
	private int SizeX = 300;                //mo¿na sobie ustawiæ jakie chcesz
	private int SizeY = 500;
	
	private int MaxCoordinate = 10;         //maksymalna wartoœæ wspó³rzêdnej - wed³ug uznania, wystarczy tylko tu ustawiæ (max= 26)
	private int CurrentShipIndex;           //pomocniczy index pozwalaj¹cy okreœliæ który statek obecnie ustawiamy
	
	//zwyk³y konstruktor
	public SidePanelPreGame() {
		
		this.setSize(SizeX,SizeY); //ustawia rozmiar Panelu
		setFocusable(true);
		setDoubleBuffered(true);
		setLayout(null);
		
		resetField();
		CurrentShipIndex = 0;
		
		Font myFont = new Font("Arial Black",Font.BOLD,20*SizeX/300);
		YourShipsInfoLabel = new JLabel();
		YourShipsInfoLabel.setBounds(30*SizeX/300, 10*SizeY/700, 270*SizeX/300, 40*SizeY/700);
		YourShipsInfoLabel.setText("Twoje Statki:");
		YourShipsInfoLabel.setFont(myFont);
		this.add(YourShipsInfoLabel);
		
		Font myFont2 = new Font("Arial",Font.PLAIN,16*SizeX/300);
		AvailableShipsArea = new JTextArea();
		AvailableShipsArea.setEditable(false);
		AvailableShipsArea.setBackground(new Color(200,200,200));
		AvailableShipsArea.setBounds(30*SizeX/300, 60*SizeY/700, 270*SizeX/300, 200*SizeY/700);
		AvailableShipsArea.setFont(myFont2);
		this.add(AvailableShipsArea);
		
		Font myFont3 = new Font("Arial",Font.BOLD,13*SizeX/300);
		SetShipsInfoArea = new JTextArea();
		SetShipsInfoArea.setEditable(false);
		SetShipsInfoArea.setText("Ustaw swoje statki podaj¹c w pierwszym\noknie wspó³rzêdne pocz¹tkowe,\na w drugim koñcowe statku");
		SetShipsInfoArea.setFont(myFont3);
		SetShipsInfoArea.setBackground(new Color(200,200,200));
		SetShipsInfoArea.setBounds(10*SizeX/300, 300*SizeY/700, 280*SizeX/300, 90*SizeY/700);
		this.add(SetShipsInfoArea);
		
		SetTheShipLabel = new JLabel();
		SetTheShipLabel.setBounds(30*SizeX/300, 390*SizeY/700, 270*SizeX/300, 25*SizeY/700);
		SetTheShipLabel.setFont(myFont2);
		this.add(SetTheShipLabel);
		
		StartCoordinates = new JTextArea();
		StartCoordinates.setEditable(true);
		StartCoordinates.setFont(myFont);
		StartCoordinates.setBounds(25*SizeX/300, 430*SizeY/700, 50*SizeX/300, 30);
		this.add(StartCoordinates);
		
		EndCoordinates = new JTextArea();
		EndCoordinates.setEditable(true);
		EndCoordinates.setFont(myFont);
		EndCoordinates.setBounds(105*SizeX/300, 430*SizeY/700, 50*SizeX/300, 30);
		this.add(EndCoordinates);
		
		SetShipButton = new JButton();
		HandlerClass handler = new HandlerClass();
		SetShipButton.addActionListener(handler);
		SetShipButton.setText("SET SHIP");
		SetShipButton.setEnabled(false);
		SetShipButton.setFocusable(false);
		SetShipButton.setBounds(180*SizeX/300, 430*SizeY/700, 100*SizeX/300, 30);
		this.add(SetShipButton);
		
		CoordinatesInfo = new JLabel();
		CoordinatesInfo.setFont(myFont2);
		CoordinatesInfo.setBounds(25*SizeX/300, 490*SizeY/700, 275*SizeX/300, 25*SizeY/700);
		this.add(CoordinatesInfo);
	}
	
	//funkcja która zapisuje do Panelu jakie posiadamy statki, podajemy tablicê o d³ugoœci
	//która reprezentuje iloœæ statków, a zawiera ona wartoœci d³ugoœci statków 
	public void setMyShips(int[] ships) {		
		MyShips = new int[ships.length];
		for(int i=0; i<ships.length; i++) {
			MyShips[i] = ships[i];
		}
		String ShipList = "";
		for(int i=0; i<MyShips.length; i++) {
			ShipList = ShipList + "Statek o d³ugoœci " + MyShips[i] + "\n";
		}
		AvailableShipsArea.setText(ShipList);
		
		SetTheShipLabel.setText("Ustaw statek o d³ugoœci: "+MyShips[CurrentShipIndex]);
		
		Coordinates = new int[MyShips.length][4];
		
		SetShipButton.setEnabled(true);
	}
	
	//funkcja zwracaj¹ca tablicê ze wspó³rzêdnymi pocz¹tku i koñca statku
	public int[][] getCoordinates(){
		return Coordinates;
	}
	
	//funkcja uruchamiana po naciœniêciu guzika SetShipButton
	//zapisuje ona podane wspó³rzêdne (jeœli s¹ poprawne) do tablicy 'Coordinates'
	private void setShip() {
		String A = StartCoordinates.getText();
		String B = EndCoordinates.getText();
		if(isProper(A) && isProper(B)) {
			int StartX;
			int StartY;
			int EndX;
			int EndY;
			if(A.length()==2) {
				StartX = (int)(A.charAt(0)) - 64;
				StartY = (int)(A.charAt(1)) - 48;
			}else {
				StartX = (int)(A.charAt(0)) - 64;
				StartY = ((int)A.charAt(1)-48)*10 + ((int)A.charAt(2)-48);
			}
			if(B.length()==2) {
				EndX = (int)(B.charAt(0)) - 64;
				EndY = (int)(B.charAt(1)) - 48;
			}else {
				EndX = (int)(B.charAt(0)) - 64;
				EndY = ((int)B.charAt(1)-48)*10 + ((int)B.charAt(2)-48);
			}
			if(isPossible(StartX,StartY,EndX,EndY,MyShips[CurrentShipIndex])) {
				if(isFree(StartX,StartY,EndX,EndY)) {
					setTaken(StartX,StartY,EndX,EndY);
					CoordinatesInfo.setText("");
					Coordinates[CurrentShipIndex][0]=StartX;
					Coordinates[CurrentShipIndex][1]=StartY;
					Coordinates[CurrentShipIndex][2]=EndX;
					Coordinates[CurrentShipIndex][3]=EndY;
					CurrentShipIndex++;
					StartCoordinates.setText("");
					EndCoordinates.setText("");
					CoordinatesInfo.setText("Poprawne umieszczenie");
					if(CurrentShipIndex < MyShips.length) {
						SetTheShipLabel.setText("Ustaw statek o d³ugoœci: "+MyShips[CurrentShipIndex]);
					}else {
						SetTheShipLabel.setText("Wszystkie statki ustawione");
						SetShipButton.setEnabled(false); //zapobiegamy próbie ustawienia statków po ustawieniu wszystkich statków
					}
				}else {
					CoordinatesInfo.setText("Te pola ju¿ s¹ zajête");
				}
			}else {
				CoordinatesInfo.setText("Niepoprawne wspó³rzêdne");
			}
		}else {
			CoordinatesInfo.setText("Niepoprawne wspó³rzêdne");
		}
	}
	
	//funkcja sprawdzaj¹ca czy podane wspó³rzêdne pola s¹ prawid³owe
	private boolean isProper(String input) {
		if(input.length()==2) {
			char first = input.charAt(0);
			char second = input.charAt(1);
			if((int)first>=65 && (int)first<65+MaxCoordinate) {
				if(MaxCoordinate<10) {
					if((int)second>=49 && (int)second<49+MaxCoordinate) {
						return true;
					}else {
						return false;
					}
				}else {
					if((int)second>=49 && (int)second<57) {
						return true;
					}else {
						return false;
					}
				}
			}else {
				return false;
			}
		}else if(MaxCoordinate>=10 && input.length()==3){
			char first = input.charAt(0);
			char second = input.charAt(1);
			char third = input.charAt(2);
			if((int)first>=65 && (int)first<65+MaxCoordinate) {
				if((int)second>=49 && (int)second<=57) {
					if((int)third>=48 && (int)third<=57) {
						int num = ((int)second-48)*10 + ((int)third-48);
						if(num<=MaxCoordinate) {
							return true;
						}else {
							return false;
						}
					}else {
						return false;
					}
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	//funkcja sprawdzaj¹ca czy pola na które chcemy umieœciæ statek nie s¹ zajête
	private boolean isFree(int StartX,int StartY,int EndX,int EndY) {
		if(StartX == EndX) {
			if(EndY>StartY) {
				for(int i=StartY;i<=EndY;i++) {
					if(FreeField[StartX-1][i-1]==false) {
						return false;
					}
				}
				return true;
			}else {
				for(int i=EndY;i<=StartY;i++) {
					if(FreeField[StartX-1][i-1]==false) {
						return false;
					}
				}
				return true;
			}
		}else {
			if(EndX>StartX) {
				for(int i=StartX;i<=EndX;i++) {
					if(FreeField[i-1][StartY-1]==false) {
						return false;
					}
				}
				return true;
			}else {
				for(int i=EndX;i<=StartX;i++) {
					if(FreeField[i-1][StartY-1]==false) {
						return false;
					}
				}
				return true;
			}
		}
	}
	
	//funkcja która sprawi ¿e zapamiêtamy pola które s¹ zajête przez ten statek
	private void setTaken(int StartX,int StartY,int EndX,int EndY) {
		if(StartX == EndX) {
			if(EndY>StartY) {
				for(int i=StartY;i<=EndY;i++) {
					FreeField[StartX-1][i-1]=false; 
				}
			}else {
				for(int i=EndY;i<=StartY;i++) {
					FreeField[StartX-1][i-1]=false;
				}
			}
		}else {
			if(EndX>StartX) {
				for(int i=StartX;i<=EndX;i++) {
					FreeField[i-1][StartY-1]=false;
				}
			}else {
				for(int i=EndX;i<=StartX;i++) {
					FreeField[i-1][StartY-1]=false;
				}
			}
		}
	}
	
	//funkcja sprawdzaj¹ca czy podany statek jest prosty - nieukoœny, i jest zak³adanej d³ugoœci
	private boolean isPossible(int StartX,int StartY,int EndX,int EndY,int length) {
		if(StartX == EndX) {
			if(Math.abs(StartY-EndY)+1==length) {
				return true;
			}else {
				return false;
			}
		}else if(StartY == EndY){
			if(Math.abs(StartX-EndX)+1==length) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	//funkcja restuj¹ca tablicê stanów s³u¿¹c¹ do weryfikacji ¿eby ustawiane statki nie zachodzi³y na siebie
	private void resetField() {
		FreeField = new boolean[MaxCoordinate][MaxCoordinate];
		for(int i=0; i<MaxCoordinate; i++) {
			for(int j=0; j<MaxCoordinate; j++) {
				FreeField[i][j] = true;
			}
		}
	}
	
	//handler guzika
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==SetShipButton) {
				setShip();
			}
		}
	}
}


