package cz.minesweeper4j;

import java.util.Random;

import cz.cuni.amis.clear2d.utils.Sanitize;
import cz.minesweeper4j.simulation.agent.IAgent;

public class MinesweeperConfig {
	
	/**
	 * Can be used to mark unique name of the simulation.
	 */
	public String id = "Minesweeper";
	
	/**
	 * Board width.
	 */
	public int width;
	
	/**
	 * Board height.
	 */
	public int height;
	
	/**
	 * Random seed that has been used to initialize {@link #random}.
	 */
	public int randomSeed;
	
	/**
	 * Random used for generating mines and suggesting a tile.
	 */
	public Random random = new Random(1);
	
	/**
	 * How many mines to generate.
	 */
	public int totalMines;
	
	/**
	 * Timeout for the game; positive number == timeout in effect; otherwise no timeout.
	 */
	public long timeoutMillis = 0;
	
	/**
	 * TRUE == start Minesweeper visualized; FALSE == start Minesweeper headless.
	 */
	public boolean visualization = false;
	
	/**
	 * Instance of the agent that should play the game.
	 */
	public IAgent agent;
		
	/**
	 * Validates the configuration; throws {@link RuntimeException} if config is found invalid. 
	 */
	public void validate() {
		if (id == null) throw new RuntimeException("ID is null.");
		if (id.length() == 0) throw new RuntimeException("ID is of zero length.");
		id = Sanitize.idify(id);
		if (agent == null) throw new RuntimeException("Agent is null.");
		if (width <= 0) throw new RuntimeException("width == " + width + " <= 0");
		if (height <= 0) throw new RuntimeException("height == " + height + " <= 0");
		if (totalMines <= 0) throw new RuntimeException("totalMines == " + totalMines + " <= 0");		
	}

}
