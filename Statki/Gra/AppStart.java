package Gra;

public class AppStart {
	
	private static MainMenuFrame mainMenu;
	
	private static SettingShipsFrame shipsFrame;	//do testów
	
	private static GameWindow gameWindow;
	
	public static void makeMainMenu()
	{
		mainMenu = new MainMenuFrame();
		mainMenu.setVisible(true);
		shipsFrame = new SettingShipsFrame();
		shipsFrame.setVisible(true);
		gameWindow = new GameWindow();
		gameWindow.setVisible(true);
		
	}
	
}

