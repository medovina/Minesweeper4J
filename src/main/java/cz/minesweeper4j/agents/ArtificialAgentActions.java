package cz.minesweeper4j.agents;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.actions.EAction;
import cz.minesweeper4j.simulation.board.oop.Pos;

public class ArtificialAgentActions {

	public Action open(int tileX, int tileY) {
		return new Action(EAction.OPEN, tileX, tileY);
	}
	
	public Action flag(int tileX, int tileY) {
		return new Action(EAction.FLAG, tileX, tileY);
	}
	
	public Action unflag(int tileX, int tileY) {
		return new Action(EAction.UNFLAG, tileX, tileY);
	}
	
	public Action open(Pos pos) {
		return new Action(EAction.OPEN, pos.x, pos.y);
	}
	
	public Action flag(Pos pos) {
		return new Action(EAction.FLAG, pos.x, pos.y);
	}
	
	public Action unflag(Pos pos) {
		return new Action(EAction.UNFLAG, pos.x, pos.y);
	}
	
	public Action advice() {
		return new Action(EAction.SUGGEST_SAFE_TILE);
	}
	
}
