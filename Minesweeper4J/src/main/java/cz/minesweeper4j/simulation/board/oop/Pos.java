package cz.minesweeper4j.simulation.board.oop;

public class Pos {

	public int x;
	public int y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Pos[" + x + "," + y + "]";
	}
	
}
