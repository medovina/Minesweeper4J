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
		
		int runs = 10;
		long totalTime = 0;
		int totalHints = 0;
		
		for (int trial = 0 ; trial < runs ; ++trial) {
    		MyAgent agent = new MyAgent();
    		agent.setSleepInterval(0);   // don't pause between moves
    		
    		MinesweeperResult result = Minesweeper.playAgent(
    				"MyAgent", size, size, mines, 600 * 1000, masterRandomSeed + trial, false, agent);
    		
    		if (result.getResult() != MinesweeperResultType.VICTORY)
    			throw new Error("failed");
    		
    		System.out.format("%d x %d, %d mines (%d%% density): solved in %d ms, hints = %d\n",
    		        size, size, mines, density, agent.getThinkTime(), result.getSafeTileSuggestions());
    		
    		totalTime += agent.getThinkTime();
    		totalHints += result.getSafeTileSuggestions();
		}
		
		System.out.format("average over %d runs: time = %d ms, hints = %.1f\n",
				runs, totalTime / runs, 1.0 * totalHints / runs);
	}
}
