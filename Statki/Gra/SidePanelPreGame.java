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

	private int[] MyShips;                  //tablica przechowuj�ca d�ugo�ci statk�w - jej d�ugo�� to ilo�� statk�w
	private int[][] Coordinates;            //tablica dwuwymiarowa przechowuj�ca wsp�rz�dne pocz�tku i ko�ca statk�w
	                                        //pierwszy wymiar tablicy to kt�ry statek, a drugi to po kolei: StartX, StartY, EndX, EndY
	private boolean[][] FreeField;          //tablica stan�w (pocz¹tkowo ca³a TRUE) na kt�rej b�dziemy zapami�tywa� jakie pola s� ju� zaj�te przez statki (TRUE = PUSTE)

	private JLabel YourShipsInfoLabel;      //tylko tekst "Twoje Statki:"

	private JTextArea AvailableShipsArea;   //TextArea prezentuj�ca list� posiadanych statk�w

	private JTextArea SetShipsInfoArea;     //tylko tekst "Ustaw swoje statki podaj�c wsp�rz�dne.."

	private JLabel SetTheShipLabel;         //Label prezentuj�cy, kt�ry obecnie statek ustawiamy

	private JTextArea StartCoordinates;     //TextArea do kt�rej wpisujemy pocz�tkowe wsp�rz�dne

	private JTextArea EndCoordinates;       //TextArea do kt�rej wpisujemy ko�cowe wsp�rz�dne

	private JButton SetShipButton;          //Guzik zatwierdzaj�cy wpisane wsp�rz�dne i zapisuj�cy je do 'Coordinates'

	private JLabel CoordinatesInfo;         //Label ukazuj�cy czy podane wsp�rz�dne s� poprawne

	private JButton acceptSettingButton;

	private SettingShipsFrame setShipsFrame;

	private GameWindow gameWindow;

	private int SizeX = 300;                //mo¿na sobie ustawiæ jakie chcesz
	private int SizeY = 500;

	private int MaxCoordinate = 10;         //maksymalna warto�� wsp�rz�dnej - wed�ug uznania, wystarczy tylko tu ustawi� (max= 26)
	private int CurrentShipIndex;           //pomocniczy index pozwalaj�cy okre�li� kt�ry statek obecnie ustawiamy

	//zwyk�y konstruktor
	public SidePanelPreGame(SettingShipsFrame setShipsFrame) {

		this.setSize(SizeX,SizeY); //ustawia rozmiar Panelu
		setFocusable(true);
		setDoubleBuffered(true);
		setLayout(null);
		this.setShipsFrame = setShipsFrame;

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
		AvailableShipsArea.setBounds(30*SizeX/300, 60*SizeY/700, 270*SizeX/300, 280*SizeY/700);
		AvailableShipsArea.setFont(myFont2);
		this.add(AvailableShipsArea);

		Font myFont3 = new Font("Arial",Font.BOLD,13*SizeX/300);
		SetShipsInfoArea = new JTextArea();
		SetShipsInfoArea.setEditable(false);
		SetShipsInfoArea.setText("Ustaw swoje statki podaj�c w pierwszym \noknie wsp�rz�dne pocz�tkowe,\na w drugim ko�cowe statku");
		SetShipsInfoArea.setFont(myFont3);
		SetShipsInfoArea.setBackground(new Color(200,200,200));
		SetShipsInfoArea.setBounds(10*SizeX/300, 365*SizeY/700, 280*SizeX/300, 75*SizeY/700);
		this.add(SetShipsInfoArea);

		SetTheShipLabel = new JLabel();
		SetTheShipLabel.setBounds(30*SizeX/300, 440*SizeY/700, 270*SizeX/300, 25*SizeY/700);
		SetTheShipLabel.setFont(myFont2);
		this.add(SetTheShipLabel);

		StartCoordinates = new JTextArea();
		StartCoordinates.setEditable(true);
		StartCoordinates.setFont(myFont);
		StartCoordinates.setBounds(25*SizeX/300, 480*SizeY/700, 50*SizeX/300, 30);
		this.add(StartCoordinates);

		EndCoordinates = new JTextArea();
		EndCoordinates.setEditable(true);
		EndCoordinates.setFont(myFont);
		EndCoordinates.setBounds(105*SizeX/300, 480*SizeY/700, 50*SizeX/300, 30);
		this.add(EndCoordinates);

		SetShipButton = new JButton();
		HandlerClass handler = new HandlerClass();
		SetShipButton.addActionListener(handler);
		SetShipButton.setText("Ustaw");
		SetShipButton.setEnabled(false);
		SetShipButton.setFocusable(false);
		SetShipButton.setBounds(180*SizeX/300, 480*SizeY/700, 100*SizeX/300, 30);
		this.add(SetShipButton);

		CoordinatesInfo = new JLabel();
		CoordinatesInfo.setFont(myFont2);
		CoordinatesInfo.setBounds(25*SizeX/300, 540*SizeY/700, 275*SizeX/300, 25*SizeY/700);
		this.add(CoordinatesInfo);

		acceptSettingButton = new JButton();
		//HandlerClass handler = new HandlerClass();
		acceptSettingButton.addActionListener(handler);
		acceptSettingButton.setText("Rozpocznij gr�!");
		acceptSettingButton.setEnabled(false);
		acceptSettingButton.setVisible(true);
		acceptSettingButton.setFocusable(false);
		acceptSettingButton.setBounds(150*SizeX/300, 580*SizeY/700, 130*SizeX/300, 30);
		this.add(acceptSettingButton);
	}

	//funkcja kt�ra zapisuje do Panelu jakie posiadamy statki, podajemy tablic� o d�ugo�ci
	//kt�ra reprezentuje ilo�� statk�w, a zawiera ona warto�ci d�ugo�ci statk�w
	public void setMyShips(int[] ships) {
		MyShips = new int[ships.length];
		for(int i=0; i<ships.length; i++) {
			MyShips[i] = ships[i];
		}
		String ShipList = "";
		for(int i=0; i<MyShips.length; i++) {
			ShipList = ShipList + "Statek o d�ugo�ci " + MyShips[i] + "\n";
		}
		AvailableShipsArea.setText(ShipList);

		SetTheShipLabel.setText("Ustaw statek o d�ugo�ci: "+MyShips[CurrentShipIndex]);

		Coordinates = new int[MyShips.length][4];

		SetShipButton.setEnabled(true);

	}

	//funkcja zwracaj�ca tablic� ze wsp�rz�dnymi pocz�tku i ko�ca statku
	public int[][] getCoordinates(){
		return Coordinates;
	}

	//funkcja uruchamiana po naci�ni�ciu guzika SetShipButton
	//zapisuje ona podane wsp�rz�dne (je�li s� poprawne) do tablicy 'Coordinates'
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
					if(isTouching(StartX,StartY,EndX,EndY)) {
						setTaken(StartX,StartY,EndX,EndY);
						CoordinatesInfo.setText("");
						Coordinates[CurrentShipIndex][0]=StartX;
						Coordinates[CurrentShipIndex][1]=StartY;
						Coordinates[CurrentShipIndex][2]=EndX;
						Coordinates[CurrentShipIndex][3]=EndY;
						CurrentShipIndex++;
						StartCoordinates.setText("");
						EndCoordinates.setText("");
						setShipsFrame.refreshPanel();
						System.out.println(A);
						System.out.println(B);
						CoordinatesInfo.setText("Poprawne umieszczenie");
						if(CurrentShipIndex < MyShips.length) {
							SetTheShipLabel.setText("Ustaw statek o d�ugo�ci: "+MyShips[CurrentShipIndex]);
						}else {
							SetTheShipLabel.setText("Wszystkie statki ustawione");
							acceptSettingButton.setVisible(true);
							acceptSettingButton.setEnabled(true);
							SetShipButton.setEnabled(false); //zapobiegamy pr�bie ustawienia statk�w po ustawieniu wszystkich statk�w
						}
					}else {
						CoordinatesInfo.setText("Nie mog� si� styka�");
					}
				}else {
					CoordinatesInfo.setText("Te pola ju� s� zaj�te");
				}
			}else {
				CoordinatesInfo.setText("Niepoprawny format statku");
			}
		}else {
			CoordinatesInfo.setText("Niepoprawne wsp�rz�dne");
		}
	}

	//funkcja sprawdzaj�ca czy podane wsp�rz�dne pola s� prawid�owe
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
					if((int)second>=49 && (int)second<=57) {
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

	//funkcja sprawdzaj�ca czy pola na kt�re chcemy umie�ci� statek nie s� zaj�te
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

	//funkcja kt�ra sprawi �e zapami�tamy pola kt�re s� zaj�te przez ten statek
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

	//funkcja sprawdzaj�ca czy podany statek jest prosty - nieukosny, i jest zak�adanej d�ugo�ci
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

	//funkcja resetuj�ca tablic� stan�w, do weryfikacji �eby ustawiane statki nie zachodzi�y na siebie
	private void resetField() {
		FreeField = new boolean[MaxCoordinate][MaxCoordinate];
		for(int i=0; i<MaxCoordinate; i++) {
			for(int j=0; j<MaxCoordinate; j++) {
				FreeField[i][j] = true;
			}
		}
	}

	private void makeGame()
	{
		//przesy³anie do serwera tablicy z ustawieniem statk�w
		//private int[][] Coordinates;   lub  private boolean[][] FreeField;
		setShipsFrame.setVisible(false);
		Message coord = new Message(getCoordinates());
		setShipsFrame.client.sendMessage(coord);

			String responce = setShipsFrame.client.receiveMessage();
		   System.out.println(responce);
		    if (responce .equals( "1"))
		{
			System.out.println("Ship placement fixed");
		    responce = setShipsFrame.client.receiveMessage();
		    System.out.println(responce);
		    if (responce.equals("2"))
		    {
			responce = setShipsFrame.client.receiveMessage();
		    System.out.println(responce);
		    boolean turn = Boolean.valueOf(responce);
			gameWindow = new GameWindow(setShipsFrame.client,  turn);
		     gameWindow.setVisible(true);
		    }

		}


	}

	//handler guzika
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==SetShipButton) {
				setShip();
			}
			if(event.getSource()==acceptSettingButton)
			{
				makeGame();
			}
		}
	}

	public int[][] returnFreeFields() {
		int[][] FieldStates = new int[MaxCoordinate+1][MaxCoordinate+1];
		for(int i=0;i<MaxCoordinate+1;i++) {
			for(int j=0;j<MaxCoordinate+1;j++) {
				FieldStates[i][j] = 0;
			}
		}
		for(int i=1;i<MaxCoordinate+1;i++) {
			for(int j=1;j<MaxCoordinate+1;j++) {
				if(FreeField[i-1][j-1]==false) {
					FieldStates[i][j]=1;
				}
			}
		}
		return FieldStates;
	}


	//funkcja sprawdzaj�ca czy statki si� stykaj��
		private boolean isTouching(int StartX, int StartY, int EndX, int EndY) {
			if(StartX == EndX) {
				if(EndY>StartY) {
					if(StartX==1) {
						if(StartY==1) {
							for(int i=0;i<EndY+1;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][EndY]==false) {
								return false;
							}else {
								return true;
							}
						}else if(EndY==MaxCoordinate){
							for(int i=StartY-2;i<MaxCoordinate;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][StartY-2]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=StartY-2;i<EndY+1;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][StartY-2]==false || FreeField[0][EndY]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartX==MaxCoordinate) {
						if(StartY==1) {
							for(int i=0;i<EndY+1;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][EndY]==false) {
								return false;
							}else {
								return true;
							}
						}else if(EndY==MaxCoordinate){
							for(int i=StartY-2;i<MaxCoordinate;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][StartY-2]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=StartY-2;i<EndY+1;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][StartY-2]==false || FreeField[MaxCoordinate-1][EndY]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartY==1) {
						for(int i=0;i<EndY+1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][EndY]==false) {
							return false;
						}else {
							return true;
						}
					}else if(EndY==MaxCoordinate) {
						for(int i=StartY-2;i<MaxCoordinate-1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][StartY-2]==false) {
							return false;
						}else {
							return true;
						}
					}else {
						for(int i=StartY-2;i<EndY+1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][StartY-2]==false || FreeField[StartX-1][EndY]==false) {
							return false;
						}else {
							return true;
						}
					}




				}else {





					if(StartX==1) {
						if(EndY==1) {
							for(int i=0;i<StartY+1;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][StartY]==false) {
								return false;
							}else {
								return true;
							}
						}else if(StartY==MaxCoordinate){
							for(int i=EndY-2;i<MaxCoordinate;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][EndY-2]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=EndY-2;i<StartY+1;i++) {
								if(FreeField[1][i]==false) {
									return false;
								}
							}
							if(FreeField[0][EndY-2]==false || FreeField[0][StartY]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartX==MaxCoordinate) {
						if(EndY==1) {
							for(int i=0;i<StartY+1;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][StartY]==false) {
								return false;
							}else {
								return true;
							}
						}else if(StartY==MaxCoordinate){
							for(int i=EndY-2;i<MaxCoordinate;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][EndY-2]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=EndY-2;i<StartY+1;i++) {
								if(FreeField[MaxCoordinate-2][i]==false) {
									return false;
								}
							}
							if(FreeField[MaxCoordinate-1][EndY-2]==false || FreeField[MaxCoordinate-1][StartY]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(EndY==1) {
						for(int i=0;i<StartY+1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][StartY]==false) {
							return false;
						}else {
							return true;
						}
					}else if(StartY==MaxCoordinate) {
						for(int i=EndY-2;i<MaxCoordinate-1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][EndY-2]==false) {
							return false;
						}else {
							return true;
						}
					}else {
						for(int i=EndY-2;i<StartY+1;i++) {
							if(FreeField[StartX-2][i]==false || FreeField[StartX][i]==false) {
								return false;
							}
						}
						if(FreeField[StartX-1][EndY-2]==false || FreeField[StartX-1][StartY]==false) {
							return false;
						}else {
							return true;
						}
					}


				}
			}else {
				if(EndX>StartX) {




					if(StartY==1) {
						if(StartX==1) {
							for(int i=0;i<EndX+1;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[EndX][0]==false) {
								return false;
							}else {
								return true;
							}
						}else if(EndX==MaxCoordinate){
							for(int i=StartX-2;i<MaxCoordinate;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[StartX-2][0]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=StartX-2;i<EndX+1;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[StartX-2][0]==false || FreeField[EndX][0]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartY==MaxCoordinate) {
						if(StartX==1) {
							for(int i=0;i<EndX+1;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[EndX][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}else if(EndX==MaxCoordinate){
							for(int i=StartX-2;i<MaxCoordinate;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[StartX-2][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=StartX-2;i<EndX+1;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[StartX-2][MaxCoordinate-1]==false || FreeField[EndX][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartX==1) {
						for(int i=0;i<EndX+1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[EndX][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}else if(EndX==MaxCoordinate) {
						for(int i=StartX-2;i<MaxCoordinate-1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[StartX-2][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}else {
						for(int i=StartX-2;i<EndX+1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[StartX-2][StartY-1]==false || FreeField[EndX][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}







				}else {





					if(StartY==1) {
						if(EndX==1) {
							for(int i=0;i<StartX+1;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[StartX][0]==false) {
								return false;
							}else {
								return true;
							}
						}else if(StartX==MaxCoordinate){
							for(int i=EndX-2;i<MaxCoordinate;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[EndX-2][0]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=EndX-2;i<StartX+1;i++) {
								if(FreeField[i][1]==false) {
									return false;
								}
							}
							if(FreeField[EndX-2][0]==false || FreeField[StartX][0]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(StartY==MaxCoordinate) {
						if(EndX==1) {
							for(int i=0;i<StartX+1;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[StartX][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}else if(StartX==MaxCoordinate){
							for(int i=EndX-2;i<MaxCoordinate;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[EndX-2][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}else {
							for(int i=EndX-2;i<StartX+1;i++) {
								if(FreeField[i][MaxCoordinate-2]==false) {
									return false;
								}
							}
							if(FreeField[EndX-2][MaxCoordinate-1]==false || FreeField[StartX][MaxCoordinate-1]==false) {
								return false;
							}else {
								return true;
							}
						}
					}else if(EndX==1) {
						for(int i=0;i<StartX+1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[StartX][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}else if(StartX==MaxCoordinate) {
						for(int i=EndX-2;i<MaxCoordinate-1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[EndX-2][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}else {
						for(int i=EndX-2;i<StartX+1;i++) {
							if(FreeField[i][StartY-2]==false || FreeField[i][StartY]==false) {
								return false;
							}
						}
						if(FreeField[EndX-2][StartY-1]==false || FreeField[StartX][StartY-1]==false) {
							return false;
						}else {
							return true;
						}
					}





				}
			}
		}
}
