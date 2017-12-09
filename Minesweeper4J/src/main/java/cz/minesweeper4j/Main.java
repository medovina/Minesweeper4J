package cz.minesweeper4j;

public class Main {
	
	public static void main(String[] args) {
		//Minesweeper.playHuman(10, 10, 10, 10);
		//Minesweeper.playHuman(15, 15, 40, 10);
		
		// TIMEOUT: 2 minutes
		Minesweeper.playHuman(15, 15, 40, 2 * 60 * 1000, 10);
	}

}
