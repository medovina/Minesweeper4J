package cz.minesweeper4j.playground;

import alice.tuprolog.Prolog;
import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.agents.AdviceAgent;
import cz.minesweeper4j.agents.PrologAgentBase;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

/**
 * Agent ready to have PROLOG stuffed with the theory and used.
 *
 * Note that without anything, this agent behaves the same as {@link AdviceAgent}.
 * 
 * @author Jimmy
 */
public class PrologAgent extends PrologAgentBase {

	public PrologAgent() {
		// Lower to make the agent play faster...
		sleepInterleveMillis = 500;
	}
	
	@Override
	protected void prepareProlog(Prolog prolog) {
		// TODO: load up initial predicates and/or facts
		//       see: https://www.programcreek.com/java-api-examples/index.php?api=alice.tuprolog.Prolog
	}

	@Override
	protected void prologNewBoard(Prolog prolog, Board board) {
		// TODO: the new board has been observed for the first time; the board is completely unknown
	}

	@Override
	protected void prologUpdateBoard(Prolog prolog, Board board, Board previousBoard) {
		// TODO: board info changed, you may infer delta information
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				if (board.tile(x, y).visible != board.tile(x,y).visible) {
					// NEW TILE UNCOVERED!
				}
			}
		}
	}

	@Override
	protected Action prologThink(Prolog prolog, Board board, Board previousBoard) {
		// TODO: use Prolog to come up with an action; return NULL if you cannot reason anything out
        //       see: https://www.programcreek.com/java-api-examples/index.php?api=alice.tuprolog.Prolog
		//       note that you have interesting information in this.unknowns and this.border pre-computed
		return null;
	}
	
	public static void main(String[] args) {		
		IAgent agent = new SATAgent(); 
		
		Minesweeper.playAgent("PrologAgent", 5, 5, 5, 30 * 60 * 1000, 1, true, agent);
		//Minesweeper.playAgent("PrologAgent", 10, 10, 10, 30 * 60 * 1000, 1, true, agent);	
	}

}
