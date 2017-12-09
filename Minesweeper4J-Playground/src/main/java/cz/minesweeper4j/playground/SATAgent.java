package cz.minesweeper4j.playground;

import org.sat4j.specs.ISolver;

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
	}

	@Override
	protected Action decodeAction(Board board, ISolver solver, int[] result) {
		return null;
	}

	
	public static void main(String[] args) {		
		IAgent agent = new SATAgent(); 
		
		//Minesweeper.playAgent("SATAgent", 10, 10, 10, 30 * 60 * 1000, 1, true, agent);	
		Minesweeper.playAgent("SATAgent", 20, 20, 10, 30 * 60 * 1000, 1, true, agent);		
	}
	
}
