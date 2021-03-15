package Gra;

//format: 2chars content type, rest for content
//Each contructor for different type of message
public class Message {
	//ng - new game; sp - ships placement info; jg - join game; sh - shot
	private String contentType;
	public String content;
	
	//default message, creating new game
	public Message(){ 
		contentType = "ng";
		content = "1"; 
	}
	public Message(String ContentType, String Content )
	{
		content=Content;
		contentType=ContentType;
	}
	
	//join game, with a gameID specified by a user
	public Message(int gameID){ 
		String gameIDstr = gameID + "";
		if (gameIDstr.length() <= 20){
			contentType = "jg";
			content = gameIDstr;
		}
	}
	
	//send ships placement
	public Message(int[][] Coordinates){ 
		if (Coordinates[0].length<=10 && Coordinates.length <= 10) {
			contentType = "sp";
			content = twoDimArrayToString(Coordinates);
		}
	}
	
	//shot
	public Message(String target){ 
		if (target.length()==2) {
			contentType = "sh";
			content = target;
		}
	}
	
	//rows first, left to right
	private static String twoDimArrayToString(int[][] twoDimArray) {
		StringBuilder sb = new StringBuilder();
		for (int j=0; j<twoDimArray.length; j++) {
			for (int i=0; i<twoDimArray[0].length; i++) {
				sb.append(twoDimArray[j][i]);
			}
		}
		String convertedArray = sb.toString();
		return convertedArray;
	}
	
	public boolean isReady() {
		if (this.toString().length() > 1) {
			return true;
		} else return false;
	}
	
	public String toString() {
		return contentType + "#" + content; 
	}
	public String getContent()
	{
		return content;
	}
	

}
