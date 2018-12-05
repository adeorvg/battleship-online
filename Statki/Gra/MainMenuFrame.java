package Gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuFrame extends JFrame
{
	private Dimension mainMenuFrameSize;
	
	private JLabel nameOfGame;
	
	private JPanel mainMenuPanel;
	
	private JButton newGameBtn;
	
	private JButton aboutGameBtn;
	
	private JButton highScoreBtn;
	
	private JButton quitBtn;
	
	public MainMenuFrame()
	{
		setTitle("Battleships");
		mainMenuFrameSize = new Dimension(800, 500);
		setPreferredSize(mainMenuFrameSize);
		pack();
		setLocationRelativeTo(null);
		createUI();
	}
	
	private void createUI()
	{
		mainMenuPanel = new JPanel();
		nameOfGame = new JLabel("BATTLESHIPS!", SwingConstants.CENTER);
		nameOfGame.setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
		mainMenuPanel.add(nameOfGame);
		newGameBtn = new JButton("Nowa Gra!");
		newGameBtn.setFocusable(false);
		mainMenuPanel.add(newGameBtn);
		aboutGameBtn = new JButton("O grze");
		aboutGameBtn.setFocusable(false);
		mainMenuPanel.add(aboutGameBtn);
		highScoreBtn = new JButton("Ranking graczy");
		highScoreBtn.setFocusable(false);
		mainMenuPanel.add(highScoreBtn);
		quitBtn = new JButton("Wyjœcie");
		quitBtn.setFocusable(false);
		mainMenuPanel.add(quitBtn);
		mainMenuPanel.setBackground(new Color(8,127,198));
		mainMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));
		getContentPane().add(mainMenuPanel);
	}
	
}
