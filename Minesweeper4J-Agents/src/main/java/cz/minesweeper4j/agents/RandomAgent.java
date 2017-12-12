package cz.minesweeper4j.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;
import cz.minesweeper4j.simulation.board.oop.Pos;
import cz.minesweeper4j.simulation.board.oop.Tile;

public class RandomAgent extends ArtificialAgent {
	
	private Random random = new Random(System.currentTimeMillis());
	
	/**
	 * Sleep interval between actions...
	 */
	public long sleepInterleveMillis = 500;

	@Override
	protected Action think(Board board) {
		System.out.println("--- RANDOM AGENT.THINK ---");
		printBoard(board);
		
		if (sleepInterleveMillis > 0) {
			try {
				Thread.sleep(sleepInterleveMillis);
			} catch (InterruptedException e) {
				return null;
			}
		}
		
		// USE ADVICE AS MUCH AS POSSIBLE
		if (board.safeTilePos != null) {
			if (!board.tile(board.safeTilePos).visible) {
				// OPEN SAFE POS
				return actions.open(board.safeTilePos);
			} else {
				// ASK FOR ANOTHER ADVICE
				return actions.advice();
			}
		}
		
		// COMPUTE UNKNOWN SPACES
		List<Pos> unknowns = new ArrayList<Pos>(); 
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				if (!board.tile(x, y).visible) {
					unknowns.add(new Pos(x, y));
				}
			}
		}
		
		// IF ONLY MINES, FLAG THEM 
		if (unknowns.size() == board.totalMines) {
			// A MIRACLE!
			for (Pos pos : unknowns) {
				if (!board.tile(pos).flag) {
					return actions.flag(pos);
				}
			}
			
			// ???
			throw new RuntimeException("Should not reach here; board.totalMines invalid?");
		}
		
		// RANDOM CLICK
		return actions.open(unknowns.get(random.nextInt(unknowns.size())));		
	}
	
	protected void printBoard(Board board) {
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				Tile tile = board.tile(x, y);
				if (tile.visible) {					
					System.out.print(tile.mines);
				} else {
					System.out.print("?");
				}
				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		IAgent agent = new RandomAgent(); 
		
		MinesweeperResult result = Minesweeper.playAgent("RandomAgent", 10, 10, 10, 60 * 1000, 1, false, agent);
		System.out.println(result);
	}

}
