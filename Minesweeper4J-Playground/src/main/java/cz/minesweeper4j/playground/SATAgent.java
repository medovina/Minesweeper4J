package cz.minesweeper4j.playground;

import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.agents.SATAgentBase;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public class SATAgent extends SATAgentBase {

	@Override
	protected void sleep() throws InterruptedException {
		Thread.sleep(500);
	}
	
	@Override
	protected void encodeProblem(Board board, ISolver solver) {		
		// TODO: given te board, encode the problem for the solver
	}

	@Override
	protected Action problemSatisfiable(Board board, ModelIterator solver) {
		while (true) {
			int[] nextModel = solver.model();
			// TODO: query your model
			
			try {
				if (!solver.isSatisfiable()) {
					break;
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				break;
			}
		}
		
		// TODO: produce an action given the results of while cycle
		return null;
	}

	
	public static void main(String[] args) {		
		IAgent agent = new SATAgent(); 
		
		//Minesweeper.playAgent("SATAgent", 10, 10, 10, 30 * 60 * 1000, 1, true, agent);	
		Minesweeper.playAgent("SATAgent", 5, 5, 5, 30 * 60 * 1000, 1, true, agent);		
	}
	
}
