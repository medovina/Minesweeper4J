package minesweeper4j.agents;

import minesweeper4j.Minesweeper;
import minesweeper4j.game.Action;
import minesweeper4j.game.Board;
import minesweeper4j.game.IAgent;
import minesweeper4j.game.MinesweeperResult;

/**
 * This agent first asks for advises until non-zero positions start to be advised.
 * @author Jimmy
 *
 */
public class RandomAgent2 extends ArtificialAgent {
	
	/**
	 * See {@link ArtificialAgent#think(Board)} and {@link ArtificialAgent#observe(Board)} for things it is doing
	 * automatically for you.
	 * 
	 * First it asks for advice about all "areas" then it clicks randomly.
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 */
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		// USE ADVICE AS MUCH AS POSSIBLE
		if (board.safeTilePos != null) {
			if (!board.tile(board.safeTilePos).visible) {
				// OPEN SAFE POS
				return actions.open(board.safeTilePos);
			} else 
			if (board.tile(board.safeTilePos).mines == 0) {
				// ASK FOR ANOTHER ADVICE
				return actions.advice();
			} 
			// stop asking, try random
		}
		
		// RANDOM CLICK
		return actions.open(unknowns.get(random.nextInt(unknowns.size())));		
	}
	
	public static void main(String[] args) {
		IAgent agent = new RandomAgent2(); 
		
		MinesweeperResult result = Minesweeper.playAgent("RandomAgent", 10, 10, 10, 60 * 1000, 1, true, agent);
		System.out.println("---// FINISHED //---");
		System.out.println(result);
	}

}
