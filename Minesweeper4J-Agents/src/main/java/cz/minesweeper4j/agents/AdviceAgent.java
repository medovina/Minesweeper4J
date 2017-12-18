package cz.minesweeper4j.agents;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

/**
 * Agent that uses all advises until the board can be solved. Always wins.
 * @author Jimmy
 */
public class AdviceAgent extends ArtificialAgent {
			
	/**
	 * See {@link ArtificialAgent#think(Board)} and {@link ArtificialAgent#observe(Board)} for things it is doing
	 * automatically for you.
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think, may be null during the first think tick
	 */
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		// USE ADVICE AS MUCH AS POSSIBLE
		if (board.safeTilePos != null) {
			if (!board.tile(board.safeTilePos).visible) {
				// OPEN SAFE POS
				return actions.open(board.safeTilePos);
			} else { 
				// ASK FOR ANOTHER ADVICE
				return actions.advice();
			} 
		}
		
		throw new RuntimeException("Should not reach here; we should get all possible advices.");
	}
	
	public static void main(String[] args) {
		IAgent agent = new AdviceAgent(); 
		
		int masterRandomSeed = 10;
		
		MinesweeperResult result = Minesweeper.playAgent("AdviceAgent", 15, 15, 20, 60 * 1000, masterRandomSeed, true, agent);
		System.out.println(result);
	}

}
