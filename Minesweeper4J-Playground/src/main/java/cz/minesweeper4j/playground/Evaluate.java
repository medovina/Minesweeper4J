package cz.minesweeper4j.playground;

import java.io.FileNotFoundException;

import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;

public class Evaluate {
	public static void main(String[] args) throws FileNotFoundException {
		int masterRandomSeed = 10;
		
		int size = 40;   // width and height of board
		
		int density = 20;  // density percentage
		
		int mines = size * size * density / 100;
		
		for (int trial = 0 ; trial < 10 ; ++trial) {
    		MyAgent agent = new MyAgent();
    		agent.setSleepInterval(0);   // don't pause between moves
    		
    		MinesweeperResult result = Minesweeper.playAgent(
    				"MyAgent", size, size, mines, 600 * 1000, masterRandomSeed + trial, false, agent);
    		
    		if (result.getResult() != MinesweeperResultType.VICTORY)
    			throw new Error("failed");
    		
    		System.out.format("%d x %d, %d mines (%d%% density): solved in %d ms, hints = %d\n",
    		        size, size, mines, density, agent.getThinkTime(), result.getSafeTileSuggestions());
		}
	}
}