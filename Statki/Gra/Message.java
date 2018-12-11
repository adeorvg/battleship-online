package Gra;

//format: 2chars content type, rest for content
//Each contructor for different type of message
public class Message {
	//ng - new game; sp - ships placement info; jg - join game; sh - shot
	private String contentType;
	private String content;
	
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
	
	//shot and info if last ship was hitted or destroyed
	public Message(String target, boolean lastShipHitted, boolean lastShipDestroyed){ 
		if (target.length()==2) {
			contentType = "sh";
			String hitted;
			String destroyed;
			hitted = lastShipHitted ? "1" : "0";
			destroyed = lastShipDestroyed ? "1" : "0";
			content = hitted + "*" + destroyed + "*" + target;
		}
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
		if (this.toString().length() > 1) {
			return true;
		} else return false;
	}
	
	public String toString() {
		return contentType + "#" + content; 
	}

}
