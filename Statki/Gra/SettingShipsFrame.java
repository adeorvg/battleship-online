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
	
	private ShipsPanel settingShipsPanel;
	
	private SidePanelPreGame sideShipsPanel;
	
	private Dimension settingShipsFrameSize;
	
    protected GridBagLayout gBL;
    
    protected GridBagConstraints gBC;
	
    private int frameWidth;
    
    private int frameHeight;
	
	public SettingShipsFrame()
	{
		setTitle("Ustaw swoje statki");
		frameWidth = 800;
		frameHeight = 500;
		settingShipsFrameSize = new Dimension(frameWidth, frameHeight);
		setPreferredSize(settingShipsFrameSize);
		gBL = new GridBagLayout(); 
    	this.setLayout(gBL);
		pack();
		setLocationRelativeTo(null);
		createUI();		
		
		//getContentPane().add(settingShipsPanel);
		//getContentPane().add(sideShipsPanel);
		//settingShipsPanel.repaint();
	}

	
    private void createUI()
    {    	
    	gBC = new GridBagConstraints();

    	sideShipsPanel = new SidePanelPreGame();
		sideShipsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
		    	BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));   
		sideShipsPanel.setBackground(new Color(200,200,200));
		   			    
		settingShipsPanel = new ShipsPanel(frameWidth,frameHeight,"Ustaw statki");
		settingShipsPanel.setBackground(new Color(8,127,198));		

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
