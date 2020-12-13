import cz.minesweeper4j.Minesweeper;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;

public class Evaluate {
	public static EvaluateResults test(int runs, int size) {
		int masterRandomSeed = 10;
		
		int density = 20;  // density percentage
		
		int mines = size * size * density / 100;
		
		long totalTime = 0;
		int totalHints = 0;
		
		for (int trial = 0 ; trial < runs ; ++trial) {
    		MyAgent agent = new MyAgent();
    		agent.setSleepInterval(0);   // don't pause between moves
    		
    		MinesweeperResult result = Minesweeper.playAgent(
    				"MyAgent", size, size, mines, 600 * 1000, masterRandomSeed + trial, false, agent);
    		
    		if (result.getResult() != MinesweeperResultType.VICTORY) {
                System.out.println("error: failed to solve the level");
                return null;
            }
    		
    		System.out.format("%d x %d, %d mines (%d%% density): solved in %d ms, hints = %d\n",
    		        size, size, mines, density, agent.getThinkTime(), result.getSafeTileSuggestions());
    		
    		totalTime += agent.getThinkTime();
    		totalHints += result.getSafeTileSuggestions();
		}
        
        EvaluateResults results = new EvaluateResults();
        results.avgTime = totalTime / runs;
        results.avgHints = 1.0 * totalHints / runs;
		System.out.format("average over %d runs: time = %d ms, hints = %.1f\n",
                runs, results.avgTime, results.avgHints);
        return results;
    }
    
	public static void test(String[] args) {
        test(50, 10);
    }
}
