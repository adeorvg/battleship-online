package centralServer;

import java.util.Arrays;
import java.util.stream.IntStream;

class ReceivedMessage {
	// ng - new game; sp - ships placement info; jg - join game; sh - shot
	// validContentType = {"ng", "sp", "jg", "sh"};
	private int clientID = 0;
	private String contentType = "";
	private String content = "";
	private int joiningGameID = 0;
	private int[][] shipsCoordinates = new int[2][4];
	private String target = "";
	private String[] validContentType = { "ng", "sp", "jg", "sh" };

	public ReceivedMessage(String input) {
		initialiseMessage(input);
		convertStringContentToAttributes(content);
	}

	private void initialiseMessage(String input) {
		String[] parsedInput = new String[3];
		if (input != null)
			parsedInput = input.split("#");
		if (parsedInput.length > 1 && parsedInput[0] != null ) {
			try {
				clientID = Integer.parseInt(parsedInput[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (parsedInput.length > 1 && parsedInput[1] != null )
			contentType = parsedInput[1];
		if (parsedInput.length > 2 && parsedInput[2] != null )
			content = parsedInput[2];
		}

	private void convertStringContentToAttributes(String content) {
			switch (contentType) {
			
			case "jg":
				try {
					joiningGameID = Integer.parseInt(content);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				break;
			case "sp":
				shipsCoordinates = stringToTwoDimArray(content);
				break;
			case "sh":
				target = content;
				break;
			}
	}

	private static int[][] stringToTwoDimArray(String string) {
		char[] chars = string.toCharArray();
		int[][] twoDimArray = new int[chars.length/4][4];
		if (chars.length > 0 && chars.length % 4 == 0) {
			twoDimArray = new int[chars.length / 4][4];
			for (int i = 0; i < chars.length / 4; i++) {
				for (int j = 0; j < 4; j++) {
					twoDimArray[i][j] = Character.getNumericValue(chars[i * 4 + j]);
				}
			}
		}
		return twoDimArray;
	}

	public boolean isClientIdValid() {
		return (clientID > 0);
	}

	public boolean isContentTypeValid() {
		return Arrays.asList(validContentType).contains(contentType);
	}

	public boolean isJoiningGameIdValid() {
		return (joiningGameID > 0);
	}

	public boolean isShipsCoordinatesValid() {
		boolean validLen = shipsCoordinates[0].length == 4 && shipsCoordinates.length <= 10;
		boolean validCells = true;
		for (int[] row : shipsCoordinates) {
			for (int cell : row) {
				if (cell < 1 || cell > 10)
					validCells = false;
			}
		}
		return validLen && validCells;
	}

	public boolean isTargetValid() {
		System.out.println(target);
		boolean validLen = target.length() == 2;
		int value = (int)(target.charAt(0)) - 64;
		int num = (int)(target.charAt(1)) - 48;
		System.out.println(value);
		System.out.println(num);
		boolean validNumber = num >= 1 && num <= 10;
		return validLen &&  validNumber;
	}

	public String getContentType() {
		return contentType;
	}

	public int getClientID() {
		return clientID;
	}

	public int getJoiningGameID() {
		return joiningGameID;
	}

	public int[][] getShipsCoordinates() {
		return shipsCoordinates;
	}
	
	public String getTarget() {
		return target;
	}
	public String getContent()
	{
		return content;
	}
}


