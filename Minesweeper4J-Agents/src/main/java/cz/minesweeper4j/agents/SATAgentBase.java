package cz.minesweeper4j.agents;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.board.oop.Board;

/**
 * Agent prepared to use SAT4J, see: http://www.sat4j.org/howto.php
 * 
 * @author Jimmy
 */
public abstract class SATAgentBase extends ArtificialAgent {

	/**
	 * See {@link ArtificialAgent#think(Board)} and {@link ArtificialAgent#observe(Board)} for things it is doing
	 * automatically for you.
	 * 
	 * Then it calls {@link #satNewSolver(Board)} then {@link #satEncodeProblem(Board, ISolver)} that should fill in the problem into the solver
	 * and finally if the problem is {@link ISolver#isSatisfiable()} it calls {@link #satProblemSatisfiable(Board, ModelIterator)} to produce an action.
	 * If no action is produced, it returns an action produced by {@link #satFailed(Board)}.
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 */
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		// CREATE NEW SAT SOLVER
		ModelIterator solver = satNewSolver(board, previousBoard);		
		
		// ENCODE THE PROBLEM
		satEncodeProblem(solver, board, previousBoard);
		
		if (solver.nVars() <= 0) {
			// NO PROBLEM ENCODED!
			return satFailed(board, previousBoard);
		}
			
		// CHECK SATISFIABILITY
		boolean satisfiable = false;
		try {
			satisfiable = solver.isSatisfiable();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		if (satisfiable) {
			Action action = satProblemSatisfiable(solver, board, previousBoard);
			if (action != null) return action;
		}
		
		return satFailed(board, previousBoard);
	}
		
	/**
	 * STEP 1
	 * Creates new instance of the SAT solver.
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 * @return a new SAT solver
	 */
	protected ModelIterator satNewSolver(Board board, Board previousBoard) {
		ISolver satSolver = SolverFactory.newDefault();
		satSolver.setTimeout(3600); // 1 hour timeout
		return new ModelIterator(satSolver);
	}

	/**
	 * STEP 2
	 * Encode the problem goven the 'board' into the 'solver', see http://www.sat4j.org/howto.php
	 * 
	 * @param satSolver
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 */
	protected abstract void satEncodeProblem(ISolver solver, Board board, Board previousBoard);
	
	/**
	 * STEP 3
	 * The problem {@link #satEncodeProblem(Board, ISolver)}ed is satisfiable; generate action what to do.
	 * If you return 'null', we fallback to {@link #noActionReasonable(Board)}.
	 * 
	 * @param solver
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 * @return an action to perform or null if no action can be inferred safely
	 */
	protected abstract Action satProblemSatisfiable(ModelIterator solver, Board board, Board previousBoard);
	
	/**
	 * STEP 4 - in case of failures
	 * Could not find a solution; try next advice...
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 * @return an action to perform
	 */
	protected Action satFailed(Board board, Board previousBoard) {
		if (board.safeTilePos != null && !board.tile(board.safeTilePos).visible) {
			return actions.open(board.safeTilePos);
		}
		return actions.advice();
	}
	
}
