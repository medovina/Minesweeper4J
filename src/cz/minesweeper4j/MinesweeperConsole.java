package cz.minesweeper4j;

import static java.lang.System.out;

import java.io.*;
import java.util.Random;

import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;
import cz.minesweeper4j.simulation.agent.IAgent;

public class MinesweeperConsole {
	private static int width = 9;
	private static int height = 9;
	
	private static int minesCount = 10;
	
	private static int randomSeed = 1;
	
	private static long timeoutMillis = -1;
	
	private static boolean visualization = true;
	
	private static String agentClassString = "cz.minesweeper4j.agents.HumanAgent";
	
	private static Class<?> agentClass;
	
	private static IAgent agent;
	
	private static String id = "Minesweeper";
	
	private static String resultFileString;
	
	private static File resultFile;
	
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
	
	private static void sanityChecks() {
        if (resultFileString != null) {
            resultFile = new File(resultFileString);
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
        }
		
		try {
			agentClass = Class.forName(agentClassString);
		} catch (ClassNotFoundException e) {
			fail("Failed to find class for name: " + agentClassString);
		}
		
		Object agentObject = null;
		try {
			agentObject = agentClass.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Failed to instantiate class: " + agentClassString, e);
        } 
        
		if (!IAgent.class.isAssignableFrom(agentObject.getClass())) {
			fail("Class does not implement IAgent: " + agentClassString);
		}
		agent = (IAgent)agentObject; 
	}
	
	private static MinesweeperResult run() {
		MinesweeperConfig config = new MinesweeperConfig();
		
		config.agent = agent;
		config.id = id;
		config.width = width;
		config.height = height;
		config.random = new Random(randomSeed);
		config.totalMines = minesCount;
		config.timeoutMillis = timeoutMillis;
		config.visualization = visualization;
		
		MinesweeperResult result = Minesweeper.playConfig(config);
        
        if (resultFile != null)
		    outputResult(result, resultFile);
		
		return result;
	}
	
	private static void outputResult(MinesweeperResult result, File resultFile) {
		System.out.println("Outputting result: " + result);
		FileOutputStream output = null;		
		boolean header = !resultFile.exists();
		try {
			output = new FileOutputStream(resultFile, true);
		} catch (FileNotFoundException e) {
			fail("Failed to append to the result file: " + resultFile.getAbsolutePath());
		}
		try {
			PrintWriter writer = new PrintWriter(output);
		
			if (header) {
				writer.println("id;width;height;minesCount;randomSeed;agent;result;steps;advices;playTimeMillis");
			}
			writer.println(
                result.getId() + ";" + width + ";" + height + ";" + minesCount + ";" +
                randomSeed + ";" + agentClassString + ";" + result.getResult() + ";" +
                result.getSteps() + ";" + result.getSafeTileSuggestions() + ";" +
                result.getSimDurationMillis());
			
			writer.close();
			
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}		
    }
    
    static void usage() {
        out.println("usage: minesweeper [<agent-classname>] [<option> ...]");
        out.println("options:");
        out.println("  -height <num> : board height");
        out.println("  -id <string> : ID to report in results");
        out.println("  -mines <num> : number of mines");
        out.println("  -result <path> : result filename");
        out.println("  -seed <num> : random seed");
        out.println("  -timeout <num> : timeout in milliseconds");
        out.println("  -visual : show visualization");
        out.println("  -width : board width");
        out.println();
        out.println("predefined board sizes:");
        out.println("  -easy: 9 x 9, 10 mines (default)");
        out.println("  -medium: 16 x 16, 40 mines");
        out.println("  -hard: 30 x 16, 99 mines");
    }

	public static void main(String[] args) {
        for (int i = 0 ; i < args.length ; ++i)
            if (args[i].startsWith("-"))
                switch (args[i]) {
                    case "-easy":
                        width = height = 9;
                        minesCount = 10;
                        break;
                    case "-hard":
                        width = 30;
                        height = 16;
                        minesCount = 99;
                        break;
                    case "-height":
                        height = Integer.parseInt(args[++i]);
                        break;
                    case "-id":
                        id = args[++i];
                        break;
                    case "-medium":
                        width = height = 16;
                        minesCount = 40;
                        break;
                    case "-mines":
                        minesCount = Integer.parseInt(args[++i]);
                        break;
                    case "-result":
                        resultFileString = args[++i];
                        break;
                    case "-seed":
                        randomSeed = Integer.parseInt(args[++i]);
                        break;
                    case "-timeout":
                        timeoutMillis = Integer.parseInt(args[++i]);
                        break;
                    case "-visual":
                        visualization = true;
                        break;
                    case "-width":
                        width = Integer.parseInt(args[++i]);
                        break;
                    default:
                        usage();
                        return;
                }
            else
                agentClassString = args[i];
        
        sanityChecks();
        
        MinesweeperResult result = run();

	    if (result == null) System.exit(MinesweeperResultType.TERMINATED.getExitValue()+1);
	    
	    System.exit(result.getResult().getExitValue());	    	    
	}
}
