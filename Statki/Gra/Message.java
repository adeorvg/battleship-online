package Gra;

//message length is always constant
//format: 2chars content type; 1char #; 40chars content; 
//lacking chars are filled with #
public class Message {
	//ng - new game; sp - ships placement info; jg - join game; sh - shot
	//validContentType = {"ng", "sp", "jg", "sh"}; 
	private String contentType;
	private String content;
	private static final int length = 43;
	
	//default message, creating new game
	public Message(){ 
		contentType = "ng";
		content = "";
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
	
	//shot, eg: a4
	public Message(String target){ 
		if (target.length()<=2) {
			contentType = "sh";
			content = target;
		}
	}
	
	private static String fillContentWithHash(String content) {
		int diff = length - content.length();
		StringBuilder sb = new StringBuilder();
		sb.append(content);
		for (int i=0; i<diff; i++) {
			sb.append("#");
		}
		String contentFilled = sb.toString();
		return contentFilled;
	}
	
	//rows first, left to right
	private static String twoDimArrayToString(int[][] twoDimArray) {
		StringBuilder sb = new StringBuilder();
		for (int j=0; j<twoDimArray[0].length; j++) {
			for (int i=0; i<twoDimArray.length; i++) {
				sb.append(twoDimArray[i][j]);
			}
		}
		String convertedArray = sb.toString();
		return convertedArray;
	}
	
	public boolean isReady() {
		if (this.toString().length() == 43) {
			return true;
		} else return false;
	}
	
	public String toString() {
		return contentType + "#" + fillContentWithHash(content);
	}

}
