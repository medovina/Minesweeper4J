package cz.minesweeper4j.playground;

import java.util.ArrayList;
import java.util.List;

import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.agents.AdviceAgent;
import cz.minesweeper4j.agents.SATAgentBase;
import cz.minesweeper4j.simulation.MinesweeperResult;
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
		// Lower (any time) to make the agent play faster...
		sleepInterleveMillis = 500;
	}
	
	@Override
	protected void satEncodeProblem(ISolver solver, Board board, Board previousBoard) {
		// TODO: given the board, encode the problem for the solver
		//		 see: http://www.sat4j.org/howto.php		
		//       note that you have interesting information in this.unknowns and this.border pre-computed
		
		// CHECK DOCUMENTATION FOR THE FOLLOWING THINGS OUT... 
		// -- might come in handy
		// -- they are precomputed for you every "think"
		//
		// this.borderNumbers
		// this.borderUnknowns
		// this.unknowns
	}

	@Override
	protected Action satProblemSatisfiable(ModelIterator solver, Board board, Board previousBoard) {
		List<int[]> models = new ArrayList<int[]>();
		boolean timeout = false;
		while (true) {
			int[] nextModel = solver.model();
			models.add(nextModel);
			try {
				// IS NEXT MODEL AVAILABLE?
				if (!solver.isSatisfiable()) {
					// NO
					// => stop model iteration...
					break;
				}
			} catch (TimeoutException e) {
				timeout = true;
				e.printStackTrace();
				break;
			}
		}
		
		if (!timeout) {
			// TODO: produce an action given the results of while cycle
			//       if you return null, the agent will auto-ask for advice
			return null;
		} else {
			// SAT failed to find a solution...
			// If you return null, the agent will auto-ask for advice
			return null;
		}
	}

	
	public static void main(String[] args) {		
		IAgent agent = new SATAgent(); 
		
		MinesweeperResult result;
		
		// switch true to false to disable visualization...
		result = Minesweeper.playAgent("SATAgent", 5, 5, 5, 30 * 60 * 1000, 1, true, agent);
		
		//result = Minesweeper.playAgent("SATAgent", 10, 10, 10, 30 * 60 * 1000, 1, true, agent);
		
		System.out.println("---// FINISHED //---");
		System.out.println(result);
	}
	
}
