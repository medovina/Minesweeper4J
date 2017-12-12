package cz.minesweeper4j.agents;

import alice.tuprolog.Prolog;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.board.oop.Board;

public abstract class PrologAgentBase extends ArtificialAgent {

	protected Prolog prolog;
	
	private boolean newBoard;
	
	@Override
	public void newBoard() {
		if (prolog == null) {
			prolog = new Prolog();
		} else {
			prolog.clearTheory();
		}
		newBoard = true;
		prepareProlog(prolog);
	}
	
	@Override
	public void observe(Board board) {
		super.observe(board);
		if (newBoard) {
			prologNewBoard(prolog, board);
		} else {
			prologUpdateBoard(prolog, board, previousBoard);
		}
	}
	
	/**
	 * STEP 1
	 * Initialize Prolog theory by loading your predicates and/or initial facts.  
	 * @param prolog
	 * @see https://www.programcreek.com/java-api-examples/index.php?api=alice.tuprolog.Prolog
	 */
	protected abstract void prepareProlog(Prolog prolog);
	
	/**
	 * STEP 2
	 * New board observed for the first time.
	 * @param prolog2
	 * @param board
	 */
	protected abstract void prologNewBoard(Prolog prolog, Board board);
	

	/**
	 * STEP 3
	 * Board has been updated, adapt your Prolog theory accordingly.
	 * @param prolog2
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 */
	protected abstract void prologUpdateBoard(Prolog prolog, Board board, Board previousBoard);

	/**
	 * STEP 4
	 * Use Prolog to come up with some action; if NULL is returned, 
	 * @param prolog
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 * @return
	 */
	protected abstract Action prologThink(Prolog prolog, Board board, Board previousBoard);

	/**
	 * STEP 5
	 * In case Prolog cannot come up with an action in {@link #prologThink(Prolog, Board)}, do some action. Default use {@link ArtificialAgentActions#advice()} and
	 * uncover new areas.
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 * @return
	 */
	protected Action prologReasoningFailed(Board board, Board previousBoard) {
		if (board.safeTilePos != null && !board.tile(board.safeTilePos).visible) {
			return actions.open(board.safeTilePos);
		}
		return actions.advice();
	}
	
	/**
	 * See {@link ArtificialAgent#think(Board)} and {@link ArtificialAgent#observe(Board)} for things it is doing
	 * automatically for you.
	 * 
	 * Then it calls {@link #prologThink(Prolog, Board)}, if that does not produce an action (returns null) then it calls {@link #prologReasoningFailed(Board)} 
	 * to produce an action.
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think iteration, never nulls
	 */
	@Override
	protected Action thinkImpl(Board board, Board previousBoard) {
		Action action = prologThink(prolog, board, previousBoard);
		if (action == null) {
			action = prologReasoningFailed(board, previousBoard);
		}
			
		return action;
	}
	
}
