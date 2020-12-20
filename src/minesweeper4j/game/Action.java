package minesweeper4j.game;

public class Action {

	public EAction type;
	
	public int tileX;
	
	public int tileY;
	
	public Action() {		
	}

	public Action(EAction type) {
		this.type = type;
		this.tileX = -1;
		this.tileY = -1;
	}
	
	public Action(EAction type, int tileX, int tileY) {
		this.type = type;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	public boolean isPossible(Board board) {
		if (type == EAction.SUGGEST_SAFE_TILE) return true;
		
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
	
	@Override
	public String toString() {
		return "Action[" + type + (type == EAction.SUGGEST_SAFE_TILE ? "" : "|" + tileX + "," + tileY) + "]";
	}
	
}
