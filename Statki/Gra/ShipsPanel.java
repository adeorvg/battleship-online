package Gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShipsPanel extends JPanel
{
	
    private int panelHeight;

    private int panelWidth;
    
    private Dimension panelSize;
    
    private JLabel text;
    
	//protected GameBoard settingBoard;
    
	public ShipsPanel(int panelW, int panelH, String tx, GameBoard gBoard)
	{
		panelWidth = panelW;
		panelHeight = panelH;
		panelSize = new Dimension(panelWidth - 400, panelHeight);
        setPreferredSize(panelSize);
        text = new JLabel(tx);
        text.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 20));
		add(text);
		//settingBoard = gBoard;
        gBoard.setBackground(new Color(90,90,90));
		//settingBoard.setBounds(10, 20, 1000, 10);
        add(gBoard);
        gBoard.repaint();

	}
	

}
