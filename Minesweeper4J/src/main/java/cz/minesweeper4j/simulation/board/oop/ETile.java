package cz.minesweeper4j.simulation.board.oop;

public enum ETile {
	
	MINE("M"), 
	FREE("."),
	UNKNOWN("?");
	
	public final String debugChar;
	
	private ETile(String debug) {
		this.debugChar = debug;
	}

}
