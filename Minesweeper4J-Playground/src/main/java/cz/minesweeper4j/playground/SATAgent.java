package cz.minesweeper4j.playground;

import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.agents.AdviceAgent;
import cz.minesweeper4j.agents.SATAgentBase;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

/**
 * Agent ready to have SAT solver stuffed with the problem and used.
 *
 * Note that without anything, this agent behaves the same as {@link AdviceAgent}.
 * 
 * @author Jimmy
 */
public class SATAgent extends SATAgentBase {

	public SATAgent() {
		// Lower to make the agent play faster...
		sleepInterleveMillis = 500;
	}
	
	@Override
	protected void satEncodeProblem(ISolver solver, Board board, Board previousBoard) {
		// TODO: given the board, encode the problem for the solver
		//		 see: http://www.sat4j.org/howto.php		
		//       note that you have interesting information in this.unknowns and this.border pre-computed
		
	}

	@Override
	protected Action satProblemSatisfiable(ModelIterator solver, Board board, Board previousBoard) {
		while (true) {
			int[] nextModel = solver.model();
			// TODO: query/process the model
			
			try {
				// IS NEXT MODEL AVAILABLE?
				if (!solver.isSatisfiable()) {
					// NO
					// => stop model iteration...
					break;
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				break;
			}
		}
		
		// TODO: produce an action given the results of while cycle
		//       if you return null, the agent will auto-ask for advice
		return null;
	}

	
	public static void main(String[] args) {		
		IAgent agent = new SATAgent(); 
		
		// switch true to false to disable visualization...
		Minesweeper.playAgent("SATAgent", 5, 5, 5, 30 * 60 * 1000, 1, true, agent);
		
		//Minesweeper.playAgent("SATAgent", 10, 10, 10, 30 * 60 * 1000, 1, true, agent);	
	}
	
}
