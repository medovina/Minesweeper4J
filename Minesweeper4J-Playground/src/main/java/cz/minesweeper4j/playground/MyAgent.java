package cz.minesweeper4j.playground;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.agents.ArtificialAgent;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public class MyAgent extends ArtificialAgent {
	
	/* Code your custom agent here.  The existing dummy implementation always asks for advice.
     *
     * The Board object passed to thinkImpl gives you the current board state.  Be sure to
     * notice that the superclass ArtificialAgent has some protected fields with additional
     * useful board state information.
     */
			
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		return actions.advice();
	}
	
	public static void main(String[] args) {
		IAgent agent = new MyAgent(); 
		
		int masterRandomSeed = 10;
		
		MinesweeperResult result = Minesweeper.playAgent("MyAgent", 15, 15, 20, 60 * 1000, masterRandomSeed, true, agent);
		System.out.println("---// FINISHED //---");
		System.out.println(result);
	}

}
