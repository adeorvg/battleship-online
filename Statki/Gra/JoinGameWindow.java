package Gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class JoinGameWindow extends JFrame implements ListSelectionListener
{
	public int gameId;

	public ClientSocket client;

	private Dimension creatingGameFrameSize;

	private JPanel	creatingGamePanel;

	private JButton creatingGameBtn;

	private JButton joiningGameBtn;

	private JTextArea idInput;

	private DefaultListModel<String> gamesListModel;

	private JList<String> gamesList;

	private SettingShipsFrame shipsFrame;

	private String[] games = { "112", "456"};

	public JoinGameWindow(ClientSocket client)
	{
		this.client = client;
		setTitle("Do³±cz do gry");
		creatingGameFrameSize = new Dimension(400, 450);
		setPreferredSize(creatingGameFrameSize);
		pack();
		setLocationRelativeTo(null);
		//setLayout(null);
		createUI();
		initGamesModel();
	}

	private void createUI()
	{
		creatingGamePanel = new JPanel();
		creatingGamePanel.setBackground(new Color(8,127,198));

		HandlerClass handler = new HandlerClass();
		creatingGameBtn = new JButton("Utwórz grê");
		creatingGameBtn.addActionListener(handler);
		creatingGamePanel.add(creatingGameBtn);

		gamesListModel = new DefaultListModel<>();
		gamesList  = new JList<>(gamesListModel);
		gamesList.setAlignmentX(LEFT_ALIGNMENT);
		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesList.addListSelectionListener(this);

		JScrollPane scrollGamesList = new JScrollPane(gamesList);
		scrollGamesList.setAlignmentX(LEFT_ALIGNMENT);
		scrollGamesList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollGamesList.setPreferredSize(new Dimension(300,200));
		creatingGamePanel.add(scrollGamesList);

		idInput = new JTextArea();
		idInput.setPreferredSize(new Dimension(150,25));
		creatingGamePanel.add(idInput);

		joiningGameBtn = new JButton("Do³±cz do gry");
		joiningGameBtn.addActionListener(handler);
		creatingGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
		creatingGamePanel.add(joiningGameBtn);
		this.add(creatingGamePanel);
	}

	private void makeSettingShipsWindow()
	{
		this.setVisible(false);
		shipsFrame = new SettingShipsFrame(client);
		shipsFrame.setVisible(true);
	}

	private class HandlerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==creatingGameBtn) {
				Message newGame = new Message();
				client.sendMessage(newGame);
				String responce =client.receiveMessage();
				System.out.println(responce);
			  if (responce.equals( "1"))

		{
			System.out.println("New game fixed");
			makeSettingShipsWindow();
		}
		else
		{
			System.out.println("New game failed");
		}

			}
			if(event.getSource() == joiningGameBtn)
			{
				getIdFromInput();
				Message joinGame = new Message(gameId);
				System.out.println("Trying to join game "+String.valueOf(gameId));
				client.sendMessage(joinGame);
				String responce =client.receiveMessage();
		       if (responce.equals( "1"))
		   {
			System.out.println("Join game fixed");
			makeSettingShipsWindow();
		  }
		else
		{
			System.out.println("Join game failed");
		}
			}

		}
	}

	//pole do wpisania tekstu do wyboru id gry do testï¿½w
	private void getIdFromInput()
	{
		String x = idInput.getText();
		gameId=Integer.valueOf(x);
		idInput.setText("");
	}

	//funkcja do ï¿½adowania z serwera na listï¿½ moï¿½liwych gier
	private void initGamesModel()
	{
		for(String s : games)
		{
			gamesListModel.addElement(s);
		}
	}

	//pobiera id zaznaczonej gry
	private void getSelectedGame()
	{
		int isSelected;
		isSelected = gamesList.getSelectedIndex();
		if(isSelected==-1)
		{

		}
		else
		{
			gameId = Integer.valueOf(gamesList.getSelectedValue());
		}
	}

	//handler listy
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object source = e.getSource();

		if(source == gamesList)
		{
			getSelectedGame();
			return;
		}

	}

}
