package cz.minesweeper4j.agents;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.board.oop.Board;

/**
 * Agent using SAT4J, see: http://www.sat4j.org/howto.php
 * @author Jimmy
 *
 */
public abstract class SATAgentBase extends ArtificialAgent {

	protected boolean askedForAdvice = false;
	
	@Override
	public void newBoard() {
		super.newBoard();
		askedForAdvice = false;
	}
	
	@Override
	protected Action think(Board board) {
		// NEW ACTION!
		try {
			sleep();
		} catch (InterruptedException e) {
			return null;
		}
		
		// USE THE ADVICE IF AVAILABLE!
		if (board.safeTilePos != null && !board.tile(board.safeTilePos).visible) {
			return actions.open(board.safeTilePos);
		}
		
		// CREATE NEW SAT SOLVER
		ModelIterator solver = new ModelIterator(newSATSolver(board));		
		
		// ENCODE THE PROBLEM
		encodeProblem(board, solver);
		
		if (solver.nVars() <= 0) {
			// NO PROBLEM ENCODED!
			return problemNotSatisfiable(board);
		}
			
		// CHECK SATISFIABILITY
		boolean satisfiable = false;
		try {
			satisfiable = solver.isSatisfiable();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		if (satisfiable) {
			Action action = problemSatisfiable(board, solver);
			if (action != null) return action;
			return noActionReasonable(board);
		} else {
			return problemNotSatisfiable(board);
		}
	}
	
	/**
	 * Called at the beginning of {@link #think(Board)} to interleave actions for GUI.
	 * @throws InterruptedException 
	 */
	protected void sleep() throws InterruptedException {		
	}
	
	/**
	 * Creates new instance of the SAT solver.
	 * @param board
	 * @return
	 */
	protected ModelIterator newSATSolver(Board board) {
		ISolver satSolver = SolverFactory.newDefault();
		satSolver.setTimeout(3600); // 1 hour timeout
		return new ModelIterator(satSolver);
	}

	/**
	 * Encode the problem goven the 'board' into the 'solver', see http://www.sat4j.org/howto.php
	 * @param satSolver
	 */
	protected abstract void encodeProblem(Board board, ISolver solver);
	
	/**
	 * The problem {@link #encodeProblem(Board, ISolver)}ed is satisfiable; generate action what to do.
	 * If you return 'null', we fallback to {@link #noActionReasonable(Board)}.
	 * @param board
	 * @param solver
	 * @return
	 */
	protected abstract Action problemSatisfiable(Board board, ModelIterator solver);
	

	/**
	 * No solution given the problem encoding done in {@link #encodeProblem(Board, ISolver)} found; provide some default.
	 * @param board
	 * @return
	 */
	protected Action problemNotSatisfiable(Board board) {
		if (!askedForAdvice) {
			askedForAdvice = true;
			return actions.advice();
		}
		
		if (board.safeTilePos == null) {
			// NO MORE ADVICES POSSIBLE
			return null;
		}
		
		// ASK FOR NEXT ADVICE AS DEFAULT...
		return actions.advice();
	}
	
	/**
	 * Called if the problem is satisfiable, but you cannot reason any action out of it.
	 * @param board
	 * @return
	 */
	protected Action noActionReasonable(Board board) {
		if (!askedForAdvice) {
			askedForAdvice = true;
			return actions.advice();
		}
		
		if (board.safeTilePos == null) {
			// NO MORE ADVICES POSSIBLE
			return null;
		}
		
		// ASK FOR NEXT ADVICE AS DEFAULT...
		return actions.advice();
	}

}
