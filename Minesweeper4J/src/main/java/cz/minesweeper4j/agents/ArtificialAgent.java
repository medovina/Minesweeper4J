package cz.minesweeper4j.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.board.oop.Board;
import cz.minesweeper4j.simulation.board.oop.Pos;
import cz.minesweeper4j.simulation.board.oop.Tile;

/**
 * An agent that auto-computes within {@link #observe(Board)}:
 * 1) positions of unknown tiles,
 * 2) positions of unknown tiles on the "known border", i.e., tiles that has at least one {@link Tile#visible} tile.
 * 
 * It contains a routine for checking whether the board is not solved.
 * 
 * @author Jimmy
 */
public abstract class ArtificialAgent extends ArtificialAgentBase {
	
	/**
	 * To be used for generating random numbers.
	 */
	protected Random random = new Random(System.currentTimeMillis());
	
	/**
	 * Sleep interval between actions...
	 */
	protected int sleepInterleveMillis = 300;
	
	/**
	 * List of tiles that are currently not {@link Tile#visible}.
	 */
	protected List<Pos> unknowns;
	
	/**
	 * List of tiles that are currently not {@link Tile#visible} but they have at least one {@link Tile#visible} neighbour tile.
	 */
	protected List<Pos> border;
	
	@Override
	public void observe(Board board) {
		super.observe(board);
		
		// COMPUTE UNKNOWN & BORDER SPACES
		if (unknowns == null) unknowns = new ArrayList<Pos>(board.width * board.height);
		else unknowns.clear();
		if (border == null) border = new ArrayList<Pos>(board.width * board.height);
		else border.clear();
		
		
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				Tile tile = board.tile(x, y);
				if (!tile.visible) {
					// ADD UKNOWN
					unknowns.add(new Pos(x, y));
					
					// TEST WHETHER IT IS A BORDER TILE
					boolean isBorder = false;
					for (int dX = -1; dX < 2; ++dX) {
						if (isBorder) break;
						for (int dY = -1; dY < 2; ++dY) {
							int tX = tile.tileX + dX;
							int tY = tile.tileY + dY;
							if (tX >= 0 && tX < board.width && tY >= 0 && tY < board.height) {
								Tile nextTile = board.tile(tX, tY);
								if (nextTile.visible) {
									border.add(new Pos(x, y));
									isBorder = true;
									break;
								}
							}
							
						}				
					}	
					
				}
			}
		}
		
	}

	@Override
	protected final Action think(Board board, Board previousBoard) {
		System.out.println("--- " + getClass().getSimpleName() + " THINK ---");
		
		// SLEEPING
		if (sleepInterleveMillis > 0) {
			System.out.println("Sleeping for " + sleepInterleveMillis + "ms...");
			try {
				Thread.sleep(sleepInterleveMillis);
			} catch (InterruptedException e) {
				return null;
			}
			System.out.println("Woke up!");
		}
		
		// BOARD STATE
		board.printBoard();
		
		// ALWAYS USE AN ADVICE
		if (board.safeTilePos != null && !board.tile(board.safeTilePos).visible) {
			return actions.open(board.safeTilePos);
		}
		
		// CHECK WHETHER THE BOARD IS COMPLETELY SOLVABLE, IF ONLY MINES, FLAG THEM 
		if (unknowns.size() == board.totalMines) {
			if (sleepInterleveMillis > 100) {
				sleepInterleveMillis = 100;
			}
			for (Pos pos : unknowns) {
				if (!board.tile(pos).flag) {
					return actions.flag(pos);
				}
			}
			// ???
			throw new RuntimeException("Should not reach here; board.totalMines invalid?");
		}
		
		// DO THE THINKING
		return thinkImpl(board, previousBoard);
	}
	
	/**
	 * Think over the 'board' and produce an 'action'; preferably using {@link #actions}.
	 * 
	 * Things already guaranteed:
	 * 1) board is not fully solvable yet
	 * 2) we do not have any new advice; if you want one, issue {@link #actions}.advice().
	 * 
	 * Thinks already computed:
	 * 1) {@link #unknowns}
	 * 2) {@link #border}
	 * 
	 * @param board current state of the board
	 * @param previousBoard a board from previous think, may be null during the first think tick
	 * @return
	 */
	protected abstract Action thinkImpl(Board board, Board previousBoard);
	
}
