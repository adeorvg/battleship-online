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
	private int[][] shipsCoordinates = new int[1][1];
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
			int len = parsedInput.length;
		int jumpToIndex = len==2? -1 : 0;
		if (parsedInput[0] != null && len==3) {
			try {
				clientID = Integer.parseInt(parsedInput[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (parsedInput[1 + jumpToIndex] != null)
			contentType = parsedInput[1 + jumpToIndex];
		if (parsedInput[2 + jumpToIndex] != null)
			content = parsedInput[2 + jumpToIndex];
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
				stringToTwoDimArray(content);
				break;
			case "sh":
				target = content;
				break;
			}
	}

	private static int[][] stringToTwoDimArray(String string) {
		char[] chars = string.toCharArray();
		int[][] twoDimArray = new int[1][1];
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
		boolean validLen = target.length() == 2;
		char letter = target.charAt(0);
		int value = Character.getNumericValue(letter);
		boolean validLetter = value >= 10 && value <= 19; // oznacza litery od a do j
		char num = target.charAt(1);
		boolean validNumber = num >= 1 && num <= 10;
		return validLen && validLetter && validNumber;
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
}
