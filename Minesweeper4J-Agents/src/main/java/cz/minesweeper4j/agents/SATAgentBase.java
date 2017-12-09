package cz.minesweeper4j.agents;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

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
		ISolver solver = newSATSolver(board);		
		
		// ENCODE THE PROBLEM
		encodeProblem(board, solver);
		
		IProblem problem = (IProblem)solver;
		
		// ITERATE OVER MODELS
		while (true) {
			// RUNNING THE SOLVER!
			
			if (problem.nVars() <= 0) {
				// NO PROBLEM ENCODED!
				break;
			}
			
			// CHECK SATISFIABILITY
			try {
				if (!problem.isSatisfiable()) {
					break;
				}
			} catch (TimeoutException e) {
				break;
			}
			
			// READ MODEL
			int[] result;			
			result = problem.model();				
			if (result != null) {
				// try to decode what to do given the model
				Action action = decodeAction(board, solver, result);
				if (action != null) {
					return action;
				}
			} else {
				// NO MORE SOLUTIONS
				break;
			}
		}
		
		// FAIL SAFE
		// -- no solution found
		// => return some default...
		return noSolutionFound(board);
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
	protected ISolver newSATSolver(Board board) {
		ISolver satSolver = SolverFactory.newDefault();
		satSolver.setTimeout(3600); // 1 hour timeout
		return satSolver;
	}

	/**
	 * Encode the problem goven the 'board' into the 'solver', see http://www.sat4j.org/howto.php
	 * @param satSolver
	 */
	protected abstract void encodeProblem(Board board, ISolver solver);

	/**
	 * Try to extract "what to do" given the "result" computed by "solver".
	 * @param solver
	 * @param result non-null, see {@link IProblem#findModel()}
	 * @return null == cannot do anything, non-null == do this action
	 */
	protected abstract Action decodeAction(Board board, ISolver solver, int[] result);

	/**
	 * No solution given the problem encoding done in {@link #encodeProblem(Board, ISolver)} found; provide some default.
	 * @param board
	 * @return
	 */
	protected Action noSolutionFound(Board board) {
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
