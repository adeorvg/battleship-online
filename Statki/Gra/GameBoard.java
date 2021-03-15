package Gra;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class GameBoard extends JPanel
{

    private int panelHeight;

    private int panelWidth;
	
    private Dimension boardSize;
    
    protected static Square[][] pola;
    
    public int[][] fields;
    
    
	public GameBoard(int panelW, int panelH)
	{
		panelWidth = panelW;
		panelHeight = panelH;
		resetField();
		
		boardSize = new Dimension(panelWidth, panelHeight);
        setPreferredSize(boardSize);
        makeSquares();
	}
	
	private void resetField() 
	{
		fields = new int[11][11];
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				fields[i][j] = 0;
			}
		}
	}
	
    private void makeSquares()
    {
        pola = new Square[11][11];
        int startY=14;
        int startX=4;

        for(int n=0;n<11;n++)
        {
            startX=4;
            for(int m=0;m<11;m++) 
            {
                pola[n][m] = new Square(startX, startY, fields[n][m]);
                startX += 32;
                if(m==0) startX-=5;
            }
            startY+= 32;
            if(n==0) startY-=16;
        }
        
    }
	
	public void paintComponent(Graphics g)
	{
		   super.paintComponent(g);
		   Dimension size2 = new Dimension();
		   size2 = getSize();
		   
		   panelWidth = size2.width;
		   panelHeight = size2.height;
		   
		   for (int n = 0; n < 11; n++) {
	            for (int m = 0; m < 11; m++) {
	                drawSquares(g, n, m); 
	            }
	        }
	}
	
	public void changeFields(int[][] freeFields)
	{
		fields = new int[11][11];
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				fields[i][j] = freeFields[i][j];
				//System.out.println(fields[i][j]);
			}
		}
	}
	
	private void drawSquares(Graphics g, int m, int n)
	{
		makeSquares();
		String[] alfa = new String[] {"A","B","C","D","E","F","G","H"," I","J"};
		Square pole = pola[m][n];
		//System.out.println(fields[n][m]);
		g.setColor(pole.getColorFromType());
		if(m==0 && m!=n)
		{
			g.drawString(Integer.toString(n),pole.positionX + 8,pole.positionY + 10);
		}
		else if(n==0 && m!=n)
		{
			g.drawString(alfa[m-1],pole.positionX + 10,pole.positionY + 20);
		}
		else if(m==0 && n==0)
		{
			
		}
		else
		{
			g.fillRect(pole.positionX, pole.positionY, 27, 27);
		}
	}
	
	
}
