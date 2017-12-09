package cz.minesweeper4j.agents;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.actions.EAction;

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
	
	public Action advice() {
		return new Action(EAction.SUGGEST_SAFE_TILE);
	}
	
}
