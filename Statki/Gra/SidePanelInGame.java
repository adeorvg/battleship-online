package Gra;

import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SidePanelInGame extends JPanel{


	private GameWindow Game_Window;
	private String MyNick = "";
	private String OpponentNick = "";
	private String MyRank = "";
	private String OpponentRank = "";
	private String [] cY={"A","B","C","D","E","F","G","H"," I","J"};

	private int turns=0;
	private String change="";

	private JLabel PlayersLabel;        //Label wywietlaj¹cy kto gra np. "MyNick (MyRank) VS. OpponentNick (OpponentRank)"
	private JLabel GameTimeLabel;       //Label prezentuj¹cy czas trwania ca³aej gry
	private JLabel PlayerRoundLabel;    //Label wywietlaj¹cy czyja kolej
	private JLabel ShotInfoLabel;       //Label z komunikatem o oddanie strza³u
	private JTextArea CoordinatesArea;  //textArea do której wpisujemy wspó³rzêdne do strza³u
	private JButton MakeAShotButton;    //guzik zatwierdzaj¹cy oddanie strza³u
	private JLabel TimeLeftLabel;       //Label z informacj¹ ile czasu pozosta³o na oddanie strza³u

	private int[][] newField;			//tablica nowych wartoci pól o zmienionym rozmiarze z 10 do 11
	private int[][] fields;

	private int CoordinateX;            //wspó³rzêdna X w postaci int - gracz widzi j¹ jako literê
	private int CoordinateY;            //wspó³rzêdna Y w postaci int
	private boolean isOkay;             //stan oznaczaj¹cy czy obecnie podane wspó³rzêdne s¹ podane
	private boolean MyTurn;             //stan s³u¿¹cy tylko weryfikacji czy powinno siê odliczaæ czas pozosta³y na strza³

	private JTextArea ChatArea;         //okno tekstu w roli czatu;
	private JTextArea MessageArea;      //okno do wspisywania wiadomoci do wys³ania
	private JButton SendMessageButton;  //guzik który wysy³a wiadomoæ

	private int SizeX = 300;            //mo¿na sobie ustawiæ jakie chcesz
	private int SizeY = 700;

	private int MaxCoordinate = 10;      //maksymalna wartoæ wspó³rzêdnej - wed³ug uznania, wystarczy tylko tu ustawiæ (max 26)
	private int TimeForShot = 20;       //czas na oddanie strza³u - wed³ug uznania, wystarczy tylko tu zmieniæ
	private int TimeLeft;

	private Timer timer;                //timer do odliczania czasu
	private long CurrentTime = 0;       //obecny czas gry w sekundach

	//zwyk³y konstruktor
	public SidePanelInGame(GameWindow game_window) {
		//Client of user
		this.Game_Window = game_window;

		this.setSize(SizeX,SizeY); //ustawia rozmiar Panelu
		setFocusable(true);
		setDoubleBuffered(true);
		setLayout(null);

		PlayersLabel = new JLabel();
        PlayersLabel.setVisible(false);
		GameTimeLabel = new JLabel("Czas gry: 0:00");
		PlayerRoundLabel = new JLabel();
		ShotInfoLabel = new JLabel("Oddaj strza³");
		CoordinatesArea = new JTextArea();
		MakeAShotButton = new JButton("Shoot");
		TimeLeft = TimeForShot;
		TimeLeftLabel = new JLabel("Pozostały czas na oddanie strzału: " + TimeLeft);
		isOkay = false;
		MyTurn = false;
		ChatArea = new JTextArea();
		MessageArea = new JTextArea();
		SendMessageButton = new JButton("Send");

		CoordinatesArea.setEditable(true);
		ChatArea.setEditable(false);
		MessageArea.setEditable(true);

		this.setBackground(Color.ORANGE);

		ChatArea.setBounds(10*SizeX/300, 390*SizeY/700, 270*SizeX/300, 200*SizeY/700);
		this.add(ChatArea);

		SendMessageButton.setBounds(220*SizeX/300, 615*SizeY/700, 65*SizeX/300, 25*SizeY/700);
		this.add(SendMessageButton);

		MessageArea.setBounds(10*SizeX/300, 610*SizeY/700, 200*SizeX/300, 35*SizeY/700);
		this.add(MessageArea);

		Font myFont = new Font("Arial",Font.PLAIN,13*SizeX/300);
		PlayersLabel.setFont(myFont);
		PlayersLabel.setBounds(10*SizeX/300, 5*SizeY/700, 290*SizeX/300, 20*SizeY/700);
		this.add(PlayersLabel);

		Font myFont2 = new Font("Arial",Font.BOLD,18*SizeX/300);
		GameTimeLabel.setFont(myFont2);
		GameTimeLabel.setBounds(35*SizeX/300, 35*SizeY/700, 230*SizeX/300, 25*SizeY/700);
		this.add(GameTimeLabel);

		Font myFont3 = new Font("Arial Black", Font.BOLD, 18*SizeX/300);
		PlayerRoundLabel.setFont(myFont3);
		PlayerRoundLabel.setForeground(Color.blue);
		PlayerRoundLabel.setBounds(30*SizeX/300, 85*SizeY/700, 290*SizeX/300, 25*SizeY/700);
		this.add(PlayerRoundLabel);

		ShotInfoLabel.setFont(myFont);
		ShotInfoLabel.setBounds(10*SizeX/300, 130*SizeY/700, 280*SizeX/300, 15*SizeY/700);
		this.add(ShotInfoLabel);

		CoordinatesArea.setFont(myFont3);
		CoordinatesArea.setBounds(30*SizeX/300, 150*SizeY/700, 50*SizeX/300, 30*SizeY/700);
		this.add(CoordinatesArea);

		MakeAShotButton.setBounds(100*SizeX/300, 155*SizeY/700, 70*SizeX/300, 20*SizeY/700);
		this.add(MakeAShotButton);

		TimeLeftLabel.setFont(myFont);
		TimeLeftLabel.setBounds(10*SizeX/300, 190*SizeY/700, 280*SizeX/300, 15*SizeY/700);
		this.add(TimeLeftLabel);

		HandlerClass handler = new HandlerClass();
		MakeAShotButton.addActionListener(handler);
		SendMessageButton.addActionListener(handler);
	}

	//przypisanie
	public void setMyNick(String nick) {
		this.MyNick = nick;
	}

	//przypisanie
	public void setOpponentNick(String nick) {
		this.OpponentNick = nick;
	}

	//przypisanie
	public void setMyRank(String rank) {
		this.MyRank = rank;
	}

	//przypisanie
	public void setOpponentRank(String rank) {
		this.OpponentRank = rank;
	}

	//przypisanie Labelu na podstawie podanych nicków i rang
	public void setPlayersLabel() {
		this.PlayersLabel.setText(MyNick + " (" + MyRank + ") VS. " + OpponentNick + " (" + OpponentRank + ")");
	}

	//funkcja do ustawienia czy teraz jest moja kolej czy przeciwnika
	public void setMyRound(boolean isMy) {
		System.out.println("Set my round begin");
		if (turns==0)
		{
			isMy=Game_Window.turn;
			 String MyArray = Game_Window.client.receiveMessage();
			System.out.println(MyArray);
			if (MyArray != null )
			{
			int[][] myFields = StringToArray(MyArray);
			Game_Window.refreshMyGamePanel(myFields);
			}
		}
		turns=turns+1;

		if(turns==1 && isMy==true)
		   {
			PlayerRoundLabel.setText("TWOJA KOLEJ");
			CoordinatesArea.setText("");
			ShotInfoLabel.setVisible(true);
			CoordinatesArea.setVisible(true);
			MakeAShotButton.setVisible(true);
			TimeLeftLabel.setVisible(true);
			isOkay = false;
			ShotInfoLabel.setText("Oddaj Strza�");
			MyTurn = true;
			System.out.println("Set my round end");
			}
		else if(turns==1&&isMy==false)
		   {
			CoordinatesArea.setText("");
			PlayerRoundLabel.setText("KOLEJ PRZECIWNIKA");
			ShotInfoLabel.setVisible(false);
			CoordinatesArea.setVisible(false);
			MakeAShotButton.setVisible(false);
			TimeLeftLabel.setVisible(false);
			MyTurn = false;
			TimeLeft = TimeForShot;
			TimeLeftLabel.setText("Pozostały czas na oddanie strza��u: " + TimeLeft);
			System.out.println("Set my round end");
			String ok=Game_Window.client.receiveMessage();
			if (ok.equals("3"))
			{ setMyRound(true);}
			}
		else if(isMy == true&&turns>1) {
			System.out.println(turns+" turn - "+isMy);
			CoordinatesArea.setText("");
			String MyArray = Game_Window.client.receiveMessage();
			System.out.println(MyArray);
			if (MyArray != null )
			{
			int[][] myFields = StringToArray(MyArray);
			Game_Window.refreshMyGamePanel(myFields);
			System.out.println("Refreshing Board");
			}
			PlayerRoundLabel.setText("TWOJA KOLEJ");
			ShotInfoLabel.setVisible(true);
			CoordinatesArea.setVisible(true);
			MakeAShotButton.setVisible(true);
			TimeLeftLabel.setVisible(true);
			isOkay = false;
			ShotInfoLabel.setText("Oddaj Strzał");
			MyTurn = true; // UWAGA NA TO: PRZYPISANIE TRUE URUCHAMIA ODLICZANIE CZASU POZOSTA£EGO NA STRZA£

			System.out.println("Set my round end");
		}
		else if(isMy == false&&turns>1) {
			System.out.println(turns+" turn - "+isMy);
			CoordinatesArea.setText("");
			String  Array = Game_Window.client.receiveMessage();
			if (Array != null)
			{
			Array=Array.replace('1', '0');
			int[][] OpponentFields = StringToArray(Array);
			Game_Window.refreshOpponentGamePanel(OpponentFields);
			}
			PlayerRoundLabel.setText("KOLEJ PRZECIWNIKA");
			ShotInfoLabel.setVisible(false);
			CoordinatesArea.setVisible(false);
			MakeAShotButton.setVisible(false);
			TimeLeftLabel.setVisible(false);
			MyTurn = false;
			TimeLeft = TimeForShot;
			TimeLeftLabel.setText("Pozostały czas na oddanie strza�u: " + TimeLeft);

			System.out.println("Set my round end");

			String ok=Game_Window.client.receiveMessage();
			if (ok.equals("3"))
			{ setMyRound(true);}
			}
	}

	//funkcje zwracaj¹ce wspó³rzêdne strza³u, jeli nie zosta³y podane poprawnie, zostanie zwrócone 0
	public int getCoordinateX() {
		if(isOkay==true) {
			return CoordinateX;
		}else {
			return 0;
		}
	}

	public int getCoordinateY() {
		if(isOkay==true) {
			return CoordinateY;
		}else {
			return 0;
		}
	}

	//funkcja uruchamiaj¹ca timer
	public void startTimeCounting() {
		//timer = new Timer();
    //timer.scheduleAtFixedRate(new ScheduleTask(), 0, 1000); //okres wynosi 1000 ms czyli 1s
	}

	//funkcja uruchamiaj¹ca odliczanie czasu pozosta³ego na oddanie strza³u
	public void outOfTime() {
		setMyRound(false);
		// tutaj ma siê zadziaæ co co wiadczy o wyczerpaniu czasu na oddanie strza³u
	}

	//funkcja wczytuj¹ca podane wspó³rzêdne strza³u
	public void makeAShot() {
		String Coord = CoordinatesArea.getText();
		if(Coord.length()==2) {
			char first = Coord.charAt(0);
			char second = Coord.charAt(1);
			if((int)first>=65 && (int)first<65+MaxCoordinate) {
				CoordinateX = (int)first - 64;
				if(MaxCoordinate>=10) {
					if((int)second>=49 && (int)second<49+MaxCoordinate) {
						CoordinateY = (int)second - 48;
						isOkay = true;

						Message shot = new Message(Coord);
						Game_Window.client.sendMessage(shot);
						System.out.println("Some shot");
						ShotInfoLabel.setText("Oddaj Strza�� (PRAWID�OWE WSP.)");
						CoordinatesArea.setText("");
						change=Game_Window.client.receiveMessage();
						if (change.equals("3"))
						{
						MyTurn = false;
						setMyRound(false);
						}

					}else {

						System.out.println(Coord);
						isOkay = false;
						ShotInfoLabel.setText("Oddaj Strza� (NIEPRAWID�OWE WSP.)");
					}
				}else {
					if((int)second>=49 && (int)second<57) {
						CoordinateY = (int)second - 48;
						isOkay = true;
						ShotInfoLabel.setText("Oddaj Strza�� (PRAWID�OWE WSP.)");
						Message shot = new Message(Coord);
						Game_Window.client.sendMessage(shot);
						CoordinatesArea.setText("");
						change=Game_Window.client.receiveMessage();
						if (change.equals("3"))
						{
						MyTurn = false;
						setMyRound(false);
						}

					}else {
						isOkay = false;
						System.out.println(Coord);
						ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
					}
				}
			}else {

				System.out.println(Coord);
				isOkay = false;
				ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
			}
		}else if(MaxCoordinate>=10 && Coord.length()==3) {
			char first = Coord.charAt(0);
			char second = Coord.charAt(1);
			char third = Coord.charAt(2);
			if((int)first>=65 && (int)first<65+MaxCoordinate) {
				CoordinateX = (int)first - 64;
				if((int)second>=49 && (int)second<=57) {
					if((int)third>=48 && (int)third<=57) {
						int num = ((int)second-48)*10 + ((int)third-48);
						if(num<=MaxCoordinate) {
							CoordinateY = num;
							isOkay = true;
							Message shot = new Message(Coord);
						    Game_Window.client.sendMessage(shot);
							ShotInfoLabel.setText("Oddaj Strzał (PRAWID�OWE WSP.)");

						    change=Game_Window.client.receiveMessage();
						if (change.equals("3"))
						{
						MyTurn = false;
						setMyRound(false);
						}
						}else {

							System.out.println(Coord);
							isOkay = false;
							ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
						}
					}else {

						System.out.println(Coord);
						isOkay = false;
						ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
					}
				}else {

					System.out.println(Coord);
					isOkay = false;
					ShotInfoLabel.setText("Oddaj Strza³ (NIEPRAWID�OWE WSP.)");
				}
			}else {

				System.out.println(Coord);
				isOkay = false;
				ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
			}
		}else {

			System.out.println(Coord);
			isOkay = false;
			ShotInfoLabel.setText("Oddaj Strzał (NIEPRAWID�OWE WSP.)");
		}
	}

	//funkcja wysy³aj¹ca wiadomoæ która zosta³a wpisana do MessageArea
	public void sendMessage() {
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	}

	//funkcja odwie¿aj¹ca Label przedstawiaj¹cy czas trwania gry
	private void refreshTime() {
		int mins = (int) (CurrentTime/60);
		int secs = (int) Math.floorMod(CurrentTime, 60);
		if(secs<10 && secs>=0) {
			GameTimeLabel.setText("Czas Gry: "+mins+":0"+secs);
		}else {
			GameTimeLabel.setText("Czas Gry: "+mins+":"+secs);
		}

		TimeLeftLabel.setText("Pozostały czas na oddanie strza�u: " + TimeLeft);
	}

	//handler guzików
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==MakeAShotButton) {
				makeAShot();
			}
			if(event.getSource()==SendMessageButton) {
				sendMessage();
			}
		}
	}

	//Tasker do obs³ugi timera, run() siê odpala co tyle czasu ile okrelono w timerze
	private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
        	CurrentTime++;
        	if(MyTurn) {
        		if(TimeLeft>0) {
        			TimeLeft--;
        		}else {
        			outOfTime();
        		}
        	}
        	refreshTime();
        }
	}
	public int[][] returnFields()
	{
		newField = new int[11][11];
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				if(i==0 || j==0)
				{
					newField[i][j] = 0;
				}
				else
				{
					newField[i][j] = fields[i-1][j-1];
				}

				//System.out.println(fields[i][j]);
			}
		}
		return newField;
	}
		public int[][] StringToArray(String message)
	{  int[][] fields = new int[11][11];
		int k = 0;
		for (int j=0; j<11; j++) {
			for (int i=0; i<11; i++) {
				fields[j][i] = Character.getNumericValue(message.charAt(k)) ;
				k++;
			}
		}
		return fields;
		}
}
