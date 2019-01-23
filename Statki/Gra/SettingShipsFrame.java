package Gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class SettingShipsFrame extends JFrame
{
  public ClientSocket client;

	private ShipsPanel settingShipsPanel;

	private SidePanelPreGame sideShipsPanel;

	private Dimension settingShipsFrameSize;

    protected GridBagLayout gBL;

    protected GridBagConstraints gBC;

    private int frameWidth;

    private int frameHeight;

    public int[][] freeFields;

    protected GameBoard gBoard;

	public SettingShipsFrame(ClientSocket Client)
	{
		this.client = Client;
    String GameID = this.client.receiveMessage();
    System.out.println(GameID);
		setTitle("Ustaw swoje statki, id gry: "+GameID);
		frameWidth = 800;
		frameHeight = 500;
		settingShipsFrameSize = new Dimension(frameWidth, frameHeight);
		setPreferredSize(settingShipsFrameSize);
		gBL = new GridBagLayout();
    	this.setLayout(gBL);
    	resetField();

    	pack();
		setLocationRelativeTo(null);
		createUI();
		//resetBoardField();

		//getContentPane().add(settingShipsPanel);
		//getContentPane().add(sideShipsPanel);
		//settingShipsPanel.repaint();
	}

	public void refreshPanel()
	{
		freeFields = sideShipsPanel.returnFreeFields();
		gBoard.changeFields(freeFields);
		gBoard.repaint();
	}

	private void resetField()
	{
		freeFields = new int[11][11];
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				freeFields[i][j] = 0;
			}
		}
	}

	/*
	private void resetBoardField()
	{
		gBoard.fields = new int[11][11];
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				gBoard.fields[i][j] = 0;
			}
		}
	}
	*/
    private void createUI()
    {
    	gBC = new GridBagConstraints();

    	gBoard = new GameBoard(350,350);
    	settingShipsPanel = new ShipsPanel(frameWidth,frameHeight,"Ustaw statki",gBoard);
		settingShipsPanel.setBackground(new Color(8,127,198));

    	sideShipsPanel = new SidePanelPreGame(this);

    	int[] ships = {2,1};
    	sideShipsPanel.setMyShips(ships);
		sideShipsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
		    	BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		sideShipsPanel.setBackground(new Color(200,200,200));



		settingShipsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

    	gBC.fill = GridBagConstraints.BOTH;
    	gBC.weightx = 400;
    	gBC.weighty = 1.1;
    	gBC.gridx = 0;
    	gBC.gridy = 0;
    	this.add(settingShipsPanel, gBC);

       gBC.fill = GridBagConstraints.BOTH;
       gBC.weightx = 400;
       gBC.weighty = 1.1;
       gBC.gridx = 1;
       gBC.gridy = 0;
       this.add(sideShipsPanel, gBC);


        this.setVisible(true);
    }

}
