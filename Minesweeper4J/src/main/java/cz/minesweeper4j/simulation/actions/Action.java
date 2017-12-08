package cz.minesweeper4j.simulation.actions;

import cz.minesweeper4j.simulation.board.oop.Board;

public class Action {

	public EAction type;
	
	public int tileX;
	
	public int tileY;
	
	public Action() {		
	}

	public Action(EAction type, int tileX, int tileY) {
		this.type = type;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	public boolean isPossible(Board board) {
		if (tileX < 0) return false;
		if (tileY < 0) return false;
		if (tileX >= board.width) return false;
		if (tileY >= board.height) return false;
		if (type == null) return false;
		
		switch (type) {
		case OPEN:
			return !board.tile(tileX, tileY).visible;
		case FLAG:
			return !board.tile(tileX, tileY).visible && !board.tile(tileX, tileY).flag;
		case UNFLAG:
			return !board.tile(tileX, tileY).visible && board.tile(tileX, tileY).flag;
		default:
			 throw new RuntimeException("INVALID ACTION TYPE: " + type);
		}
	}
	
}
