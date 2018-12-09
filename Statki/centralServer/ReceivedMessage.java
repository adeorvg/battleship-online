package centralServer;

class ReceivedMessage {
	//ng - new game; sp - ships placement info; jg - join game; sh - shot
	//validContentType = {"ng", "sp", "jg", "sh"}; 
	private int clientID = 0;
	private String contentType = "";
	private String[] content = new String[1];
	private int joiningGameID = 0;
	private int[][] shipsCoordinates = new int[1][1];
	private boolean shipHitted = false;
	private boolean shipDestroyed = false;
	private String target = "";
	
	public ReceivedMessage(String input){ 
		initialiseMessage(input);
		convertStringContentToAttributes(content);
	}
	
	private void initialiseMessage(String input) {
		String[] parsedInput = new String[3];
		if (input != null)  parsedInput = input.split("#");
		if (parsedInput[0] != null ) {
			try {
				clientID = Integer.parseInt(parsedInput[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (parsedInput[1] != null ) contentType = parsedInput[1];
		if (parsedInput[2] != null ) content = parsedInput[2].split("*");
	}
	
	private void convertStringContentToAttributes(String[] content) {
		if (content.length == 1) {
			switch(contentType) {
			case "jg":
				try {
					joiningGameID = Integer.parseInt(content[0]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				break;
			case "sp":
				stringToTwoDimArray(content[0]);
			}
		} else if (content.length > 2) {
			if (content[0] != null) {
				shipHitted = (content[0].equals("1")) ? true : false;
			}
			if (content[1] != null) {
				shipDestroyed = (content[1].equals("1")) ? true : false;
			}
			if (content[2] != null) {
				target = content[2];
			}
		}
	}
	
	private static int[][] stringToTwoDimArray(String string){
			StringBuilder sb = new StringBuilder();
			for (int j=0; j<twoDimArray[0].length; j++) {
				for (int i=0; i<twoDimArray.length; i++) {
					sb.append(twoDimArray[i][j]);
				}
			}
			String convertedArray = sb.toString();
			return convertedArray;
	}
	
	private boolean isValid() {
		
	}
}
