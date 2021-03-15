package Gra;

import java.awt.Color;

import javax.swing.JPanel;


public class Square extends JPanel
{
	
	protected typeOfSquare squareType;
	
	protected int positionX;
	
	protected int positionY;
	
	public typeOfSquare getTypeOfSquare(int number)
	{
		switch(number){
		
			case 4:
				return typeOfSquare.GREY;	//pole sprawdzone/pud³o
			case 3:
				return typeOfSquare.BLACK;	//pole statku zniszczonego
			case 2:
				return typeOfSquare.RED;	//pole statku trafionego
			case 1:	
				return typeOfSquare.BLUE;	//pole statku ¿ywego
			case 0:
				return typeOfSquare.WHITE;	//pole puste/niezajête/niesprawdzone
			default:
				return typeOfSquare.WHITE;
		}
	}
	
	public Color getColorFromType()
	{
		return squareType.getColor();
	}
	
	public Square(int x, int y,int type)
	{
		squareType = getTypeOfSquare(type);
		positionX = x;
		positionY = y;
		
	}
	
	
    public enum typeOfSquare 
    {	
    	GREY(new Color(120,120,120)),
    	BLACK(Color.black),
    	RED(Color.red),
    	BLUE(Color.blue),
    	WHITE(Color.white);    
        
    	protected Color color;
    	
    	typeOfSquare(Color color)
    	{
    		this.color = color;
    	}
    	
        public Color getColor() {
            return color;
        }
    	
    }
      
}
