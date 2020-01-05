package cz.minesweeper4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;
import cz.minesweeper4j.simulation.agent.IAgent;

public class MinesweeperConsole {
	
	private static final char ARG_WIDTH_SHORT = 'w';
	
	private static final String ARG_WIDTH_LONG = "width";
	
	private static final char ARG_HEIGHT_SHORT = 'h';
	
	private static final String ARG_HEIGHT_LONG = "height";
	
	private static final char ARG_MINES_COUNT_SHORT = 'm';
	
	private static final String ARG_MINES_COUNT_LONG = "mines-count";
	
	private static final char ARG_RANDOM_SEED_SHORT = 'r';
	
	private static final String ARG_RANDOM_SEED_LONG = "random-seed";
	
	private static final char ARG_TIMEOUT_MILLIS_SHORT = 't';
	
	private static final String ARG_TIMEOUT_MILLIS_LONG = "timeout-millis";
	
	private static final char ARG_VISUALIZATION_SHORT = 'v';
	
	private static final String ARG_VISUALIZATION_LONG = "visualization";
	
	private static final char ARG_AGENT_SHORT = 'a';
	
	private static final String ARG_AGENT_LONG = "agent";
	
	private static final char ARG_ID_SHORT = 'i';
	
	private static final String ARG_ID_LONG = "id";
	
	private static final char ARG_RESULT_FILE_SHORT = 'r';
	
	private static final String ARG_RESULT_FILE_LONG = "result-file";
	
	private static JSAP jsap;

	private static int width;
	
	private static int height;
	
	private static int minesCount;
	
	private static int randomSeed;
	
	private static long timeoutMillis;
	
	private static boolean visualiztion;
	
	private static String agentClassString;
	
	private static Class<?> agentClass;
	
	private static IAgent agent;
	
	private static String id;
	
	private static String resultFileString;
	
	private static File resultFile;
	
	private static JSAPResult config;
	
	private static boolean headerOutput = false;

	private static void fail(String errorMessage) {
		fail(errorMessage, null);
	}

	private static void fail(String errorMessage, Throwable e) {
		header();
		System.out.println("ERROR: " + errorMessage);
		System.out.println();
		if (e != null) {
			e.printStackTrace();
			System.out.println("");
		}		
        System.out.println("Usage: java -jar minesweeper.jar ");
        System.out.println("                " + jsap.getUsage());
        System.out.println();
        System.out.println(jsap.getHelp());
        System.out.println();
        throw new RuntimeException("FAILURE: " + errorMessage);
	}

	private static void header() {
		if (headerOutput) return;
		System.out.println();
		System.out.println("=====================");
		System.out.println("Minesweeper4J Console");
		System.out.println("=====================");
		System.out.println();
		headerOutput = true;
	}
		
	private static void initJSAP() throws JSAPException {
		jsap = new JSAP();
		
        FlaggedOption opt1 = new FlaggedOption(ARG_VISUALIZATION_LONG)
	    	.setStringParser(JSAP.BOOLEAN_PARSER)
	    	.setRequired(false) 
	    	.setDefault("true")
	    	.setShortFlag(ARG_VISUALIZATION_SHORT)
	    	.setLongFlag(ARG_VISUALIZATION_LONG);    
	    opt1.setHelp("Turn on/off (true/false) visualization.");
	
	    jsap.registerParameter(opt1);
	    
	    FlaggedOption opt11 = new FlaggedOption(ARG_AGENT_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(false) 
	    	.setDefault("cz.minesweeper4j.agents.HumanAgent")
	    	.setShortFlag(ARG_AGENT_SHORT)
	    	.setLongFlag(ARG_AGENT_LONG);    
	    opt11.setHelp("Agent FQCN, e.g.: cz.minesweeper4j.agents.HumanAgent");
	
	    jsap.registerParameter(opt11);
	    
	    FlaggedOption opt2 = new FlaggedOption(ARG_ID_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(false)
	    	.setDefault("Minesweeper")
	    	.setShortFlag(ARG_ID_SHORT)
	    	.setLongFlag(ARG_ID_LONG);    
	    opt2.setHelp("Simulation ID echoed into CSV.");
	    
	    jsap.registerParameter(opt2);
	    
	    FlaggedOption opt3 = new FlaggedOption(ARG_TIMEOUT_MILLIS_LONG)
	    	.setStringParser(JSAP.LONG_PARSER)
	    	.setRequired(false)
	    	.setDefault("-1")
	    	.setShortFlag(ARG_TIMEOUT_MILLIS_SHORT)
	    	.setLongFlag(ARG_TIMEOUT_MILLIS_LONG);    
	    opt3.setHelp("Timeout for the level in milliseconds; -1 to disable.");
	    
	    jsap.registerParameter(opt3);
	    
	    FlaggedOption opt31 = new FlaggedOption(ARG_HEIGHT_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(true) 	    	
	    	.setShortFlag(ARG_HEIGHT_SHORT)
	    	.setLongFlag(ARG_HEIGHT_LONG);    
	    opt31.setHelp("Board height.");
	
	    jsap.registerParameter(opt31);
	    
	    FlaggedOption opt32 = new FlaggedOption(ARG_RESULT_FILE_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(false)
	    	.setDefault("./results/Minesweeper-Results.csv")
	    	.setShortFlag(ARG_RESULT_FILE_SHORT)
	    	.setLongFlag(ARG_RESULT_FILE_LONG);    
	    opt32.setHelp("File where to append the result. File will be created if does not exist.");
	    
	    jsap.registerParameter(opt32);
	    
	    FlaggedOption opt33 = new FlaggedOption(ARG_MINES_COUNT_LONG)
	    	.setStringParser(JSAP.STRING_PARSER)
	    	.setRequired(false)
	    	.setShortFlag(ARG_MINES_COUNT_SHORT)
	    	.setLongFlag(ARG_MINES_COUNT_LONG);    
	    opt33.setHelp("Total number of mines to randomly place on the board.");
	
	    jsap.registerParameter(opt33);
    
	    FlaggedOption opt6 = new FlaggedOption(ARG_WIDTH_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(true)
	    	.setShortFlag(ARG_WIDTH_SHORT)
	    	.setLongFlag(ARG_WIDTH_LONG);    
	    opt6.setHelp("Board width.");
	
	    jsap.registerParameter(opt6);
	    
	    FlaggedOption opt7 = new FlaggedOption(ARG_RANDOM_SEED_LONG)
	    	.setStringParser(JSAP.INTEGER_PARSER)
	    	.setRequired(false)
	    	.setDefault("1")
	    	.setShortFlag(ARG_RANDOM_SEED_SHORT)
	    	.setLongFlag(ARG_RANDOM_SEED_LONG);    
	    opt7.setHelp("Random seed to use to randomly place mines.");
	
	    jsap.registerParameter(opt6);
   	}

	private static void readConfig(String[] args) {
		System.out.println("Parsing command arguments.");
		
		try {
	    	config = jsap.parse(args);
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    	System.out.println("");
	    	e.printStackTrace();
	    	throw new RuntimeException("FAILURE!");
	    }
		
		if (!config.success()) {
            String error = "Invalid arguments specified.";

            @SuppressWarnings("unchecked")
			Iterator<String> errorIter = config.getErrorMessageIterator();
			if (!errorIter.hasNext()) {
				error += "\n-- No details given.";
			} else {
				while (errorIter.hasNext()) {
					error += "\n-- " + errorIter.next();
				}
			}
			fail(error);
    	}

		width = config.getInt(ARG_WIDTH_LONG);

		height = config.getInt(ARG_HEIGHT_LONG);
		
		randomSeed = config.getInt(ARG_RANDOM_SEED_LONG);
		
		minesCount = config.getInt(ARG_MINES_COUNT_LONG);
		
		timeoutMillis = config.getLong(ARG_TIMEOUT_MILLIS_LONG);
		
		resultFileString = config.getString(ARG_RESULT_FILE_LONG);
		
		id = config.getString(ARG_ID_LONG);
		
		visualiztion = config.getBoolean(ARG_VISUALIZATION_LONG);
		
		agentClassString = config.getString(ARG_AGENT_LONG);
	}
	
	private static void sanityChecks() {
		System.out.println("Sanity checks...");
		
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
		
		try {
			agentClass = Class.forName(agentClassString);
		} catch (ClassNotFoundException e) {
			fail("Failed to find class for name: " + agentClassString, e);
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
		
	    System.out.println("Sanity checks OK!");
	}
	
	private static MinesweeperResult run() {
		System.out.println("Running MINESWEEPER!");
		
		MinesweeperConfig config = new MinesweeperConfig();
		
		config.agent = agent;
		config.id = id;
		config.width = width;
		config.height = height;
		config.random = new Random(randomSeed);
		config.totalMines = minesCount;
		config.timeoutMillis = timeoutMillis;
		config.visualization = visualiztion;
		
		MinesweeperResult result = Minesweeper.playConfig(config);
		
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
			writer.println(result.getId() + ";" + width + ";" + height + ";" + minesCount + ";" + randomSeed + ";" + agentClassString + ";" + result.getResult() + ";" + result.getSteps() + ";" + result.getSafeTileSuggestions() + ";" + result.getSimDurationMillis());
			
			writer.flush();
			writer.close();
			
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}		
	}

	// ==============
	// TEST ARGUMENTS
	// ==============
	
	public static String[] getTestArgs() {
		return new String[] {
				  "-w", "10"    // board width
				, "-h", "10"    // board height
				, "-m", "10"    // mines count
				, "-r", "10"    // random seed
				, "-t", "20000" // timeout, -1 to disable
				, "-a", "cz.minesweeper4j.agents.HumanAgent"
				, "-v", "true"  // visualization
				, "-i", "human" // id of simulation				
		};
	}
	
	public static String[] getArgs(MinesweeperConfig config, File resultFile) {
		List<String> args = new ArrayList<String>();
		
		args.add("-w"); args.add("" + config.width); // board width
		
		args.add("-h"); args.add("" + config.height); // board height
		
		args.add("-m"); args.add("" + config.totalMines);   // mines count
		
		args.add("-r"); args.add("" + config.randomSeed);   // random seed
		
		args.add("-t"); args.add(String.valueOf(config.timeoutMillis)); // timeout
		
		args.add("-a"); args.add(config.agent.getClass().getName()); // class name
		
		args.add("-v"); args.add(String.valueOf(config.visualization)); // visualization
		
		if (config.id != null) {
			args.add("-i"); args.add(config.id); // simulation id
		}
		
		return (String[]) args.toArray(new String[0]);
	}
	
	public static String[] getArgs(MinesweeperConfig config, Class<?> agentClass, File resultFile) {
		List<String> args = new ArrayList<String>();
		
		args.add("-w"); args.add("" + config.width); // board width
		
		args.add("-h"); args.add("" + config.height); // board height
		
		args.add("-m"); args.add("" + config.totalMines);   // mines count
		
		args.add("-r"); args.add("" + config.randomSeed);   // random seed
		
		args.add("-t"); args.add(String.valueOf(config.timeoutMillis)); // timeout
		
		args.add("-a"); args.add(agentClass == null ? config.agent.getClass().getName() : agentClass.getName()); // class name
		
		args.add("-v"); args.add(String.valueOf(config.visualization)); // visualization
		
		if (config.id != null) {
			args.add("-i"); args.add(config.id); // simulation id
		}
		
		args.add("-r"); args.add(resultFile.getAbsolutePath());   // result file
				
		return (String[]) args.toArray(new String[0]);
	}
		
	public static void main(String[] args) throws JSAPException {
		if (args == null || args.length == 0) {
			Main.main(args);
			return;
		}
		
		// -----------
		// FOR TESTING
		// -----------
		//args = getTestArgs();		
		
		// --------------
		// IMPLEMENTATION
		// --------------
		
		MinesweeperResult result = null;
		
		try {
			initJSAP();

			header();
		    
		    readConfig(args);
		    
		    sanityChecks();
		    
		    result = run();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	    
	    System.out.println("---// FINISHED //---");

	    if (result == null) System.exit(MinesweeperResultType.TERMINATED.getExitValue()+1);
	    
	    System.exit(result.getResult().getExitValue());	    	    
	}

}
