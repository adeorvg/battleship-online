package Gra;

public class AppStart {
	
	private static MainMenuFrame mainMenu;
	
	private static SettingShipsFrame shipsFrame;	//do testów
	
	private static GameWindow gameWindow;
	
	private static ClientSocket Client;
	
	public static void makeMainMenu()
	{   
		mainMenu = new MainMenuFrame();
		mainMenu.setVisible(true);
		
	}
	
}

