import cz.minesweeper4j.agents.ArtificialAgent;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.board.oop.Board;

public class MyAgent extends ArtificialAgent {
	
	/* Code your custom agent here.  The existing dummy implementation always asks for a hint.
     *
     * The Board object passed to thinkImpl gives you the current board state.  Be sure to
     * notice that the superclass ArtificialAgent has some protected fields with additional
     * useful board state information.
     */
			
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		return actions.advice();
	}
	
}
