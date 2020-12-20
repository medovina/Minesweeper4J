package cz.minesweeper4j;

import static java.lang.System.out;

import java.io.*;

import cz.minesweeper4j.agents.ArtificialAgent;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;
import cz.minesweeper4j.simulation.agent.IAgent;

public class MinesweeperConsole {
	private static void fail(String errorMessage) {
		fail(errorMessage, null);
	}

	private static void fail(String errorMessage, Throwable e) {
		System.out.println("ERROR: " + errorMessage);
		if (e != null) {
            System.out.println();
			e.printStackTrace();
			System.out.println("");
        }
        System.exit(1);
	}
    
    static IAgent makeAgent(String className) {
        Class<?> agentClass = null;

		try {
			agentClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			fail("Failed to find class for name: " + className);
		}
		
		Object agentObject = null;
		try {
			agentObject = agentClass.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Failed to instantiate class: " + className, e);
        } 
        
		if (!IAgent.class.isAssignableFrom(agentObject.getClass())) {
			fail("Class does not implement IAgent: " + className);
		}
		return (IAgent) agentObject; 
    }

	private static File makeResultFile(String resultFileString) {
        if (resultFileString == null)
            return null;

        File resultFile = new File(resultFileString);
        System.out.println("-- result file: " + resultFileString + " --> " + resultFile.getAbsolutePath());
        
        if (!resultFile.exists()) {
            System.out.println("---- result file does not exist, will be created");
        } else {
            if (!resultFile.isFile()) {
                fail("Result file is not a file!!");
            } else {
                System.out.println("---- result file exists, will be appended to");
            }
        }		
        
        if (!resultFile.getParentFile().exists()) {
            System.out.println("---- creating parent directories for " + resultFile.getAbsolutePath());
            resultFile.getParentFile().mkdirs();
            if (!resultFile.getParentFile().exists()) {
                fail("Failed to create parent directories for " + resultFile.getAbsolutePath());
            }
        }

        return resultFile;
	}
	
    private static void outputResult(MinesweeperConfig config, String agentClass,
                                     MinesweeperResult result, File resultFile) {
		System.out.println("Outputting result: " + result);
		boolean header = !resultFile.exists();

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(resultFile, true))) {
			if (header) {
				writer.println("id;width;height;minesCount;randomSeed;agent;result;steps;hints;playTimeMillis");
			}
			writer.println(
                result.getId() + ";" + config.width + ";" + config.height + ";" + config.totalMines + ";" +
                config.randomSeed + ";" + agentClass + ";" + result.getResult() + ";" +
                result.getSteps() + ";" + result.getSafeTileSuggestions() + ";" +
                result.getSimDurationMillis());
		} catch (FileNotFoundException e) {
            fail("Failed to append to the result file: " + resultFile.getAbsolutePath());
        }
    }

	public static EvaluateResults runGames(
        MinesweeperConfig config, int masterSeed, String className, int games) {
        
		long totalTime = 0;
		int totalHints = 0;
		
		for (int i = 0 ; i < games ; ++i) {
    		ArtificialAgent agent = (ArtificialAgent) makeAgent(className);
            agent.setSleepInterval(0);   // don't pause between moves
            config.agent = agent;
            
            int seed = masterSeed + i;
            config.setSeed(seed);
    		MinesweeperResult result = Minesweeper.playConfig(config);
    		
    		if (result.getResult() != MinesweeperResultType.VICTORY) {
                System.out.println("error: failed to solve the level");
                return null;
            }
    		
    		System.out.format("seed %d: solved in %d ms, hints = %d\n",
                seed, agent.getThinkTime(), result.getSafeTileSuggestions());
    		
    		totalTime += agent.getThinkTime();
    		totalHints += result.getSafeTileSuggestions();
		}
        
        EvaluateResults results = new EvaluateResults();
        results.avgTime = totalTime / games;
        results.avgHints = 1.0 * totalHints / games;
		System.out.format("average over %d runs: time = %d ms, hints = %.1f\n",
                games, results.avgTime, results.avgHints);
        return results;
    }
    
    static void usage() {
        out.println("usage: minesweeper [<agent-classname>] [<option> ...]");
        out.println("options:");
        out.println("  -density <num> : fraction of squares with mines (default is 0.2)");
        out.println("  -id <string> : ID to report in results");
        out.println("  -result <path> : result filename");
        out.println("  -seed <num> : random seed");
        out.println("  -sim <num> : simulate a series of games without visualization");
        out.println("  -size <num> [<num>] : board width and height");
        out.println("  -timeout <num> : timeout in milliseconds");
        out.println();
        out.println("predefined board sizes:");
        out.println("  -easy: 9 x 9, 10 mines (default if no size is specified)");
        out.println("  -medium: 16 x 16, 40 mines");
        out.println("  -hard: 30 x 16, 99 mines");
    }

	public static void main(String[] args) {
        MinesweeperConfig config = new MinesweeperConfig();
	
        int width = 0, height = 0;
    	int numMines = 0;
        double density = -1;
        String agentClass = null;
        String resultFileString = null;
        int seed = -1;
        int simGames = 0;

        for (int i = 0 ; i < args.length ; ++i)
            if (args[i].startsWith("-"))
                switch (args[i]) {
                    case "-density":
                        density = Float.parseFloat(args[++i]);
                        break;
                    case "-easy":
                        width = height = 9;
                        numMines = 10;
                        break;
                    case "-hard":
                        width = 30;
                        height = 16;
                        numMines = 99;
                        break;
                    case "-id":
                        config.id = args[++i];
                        break;
                    case "-medium":
                        width = height = 16;
                        numMines = 40;
                        break;
                    case "-result":
                        resultFileString = args[++i];
                        break;
                    case "-seed":
                        seed = Integer.parseInt(args[++i]);
                        break;
                    case "-sim":
                        simGames = Integer.parseInt(args[++i]);
                        break;
                    case "-size":
                        width = Integer.parseInt(args[++i]);
                        if (i + 1 < args.length && Character.isDigit(args[i + 1].charAt(0)))
                            height = Integer.parseInt(args[++i]);
                        else
                            height = width;
                        break;
                    case "-timeout":
                        config.timeoutMillis = Integer.parseInt(args[++i]);
                        break;
                    default:
                        usage();
                        return;
                }
            else
                agentClass = args[i];

        if (width == 0) {       // no size specified
            if (density != -1) {
                System.out.println("error: must specify size with -density");
                return;
            }
            width = height = 9;
            numMines = 10;
        } else if (numMines == 0) {
            if (density == -1)
                density = 0.2;
            if (density < 0 || density > 1.0) {
                System.out.println("error: density must be between 0.0 and 1.0");
                return;
            }
            numMines = (int) Math.round(density * width * height);
        }
        
		config.width = width;
        config.height = height;
        config.totalMines = numMines;
        System.out.printf("board size is %d x %d, %d mines\n", width, height, numMines);
        
        if (simGames > 0) {
            if (agentClass == null) {
                out.println("error: must specify agent class with -sim");
                return;
            }
            runGames(config, seed == -1 ? 0 : seed, agentClass, simGames);
        }
        else {
            if (agentClass == null)
                agentClass = "cz.minesweeper4j.agents.HumanAgent";
            config.agent = makeAgent(agentClass);
            
            if (seed >= 0)
                config.setSeed(seed);
            config.visualization = true;

            File resultFile = makeResultFile(resultFileString);
            
    		MinesweeperResult result = Minesweeper.playConfig(config);
        
            if (resultFile != null)
                outputResult(config, agentClass, result, resultFile);
            
    	    if (result == null) System.exit(MinesweeperResultType.TERMINATED.getExitValue()+1);
	    
            System.exit(result.getResult().getExitValue());
        }
	}
}
