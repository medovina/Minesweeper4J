package cz.minesweeper4j;

import java.util.Random;

import cz.minesweeper4j.agents.HumanAgent;
import cz.minesweeper4j.simulation.MinesweeperResult;
import cz.minesweeper4j.simulation.MinesweeperResult.MinesweeperResultType;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public class Minesweeper {
		
	private MinesweeperConfig config;
	
	private Board board;
	private IAgent agent;
	private MinesweeperSim game;
	
	public Minesweeper(MinesweeperConfig config) {
		this.config = config;
	}
	
	/**
	 * Resets everything except {@link #sprites} and {@link #results}.
	 */
	public void reset() {
		if (game != null) {
			game.stopGame();
			game = null;
		}		
		board = null;
	}
	
	public MinesweeperResult play() {
		config.validate();
		
		board = new Board(config.width, config.height);
		board.placeRandomMines(config.random, config.totalMines);
		
		agent = config.agent;
		
		game = new MinesweeperSim(config.id, board, agent, config.timeoutMillis, config.visualization, config.random);
		
		game.startGame();
		try {
			game.waitFinish();
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted on game.waitFinish()");
		}
		if (game.getResult() != null) {
			MinesweeperResult result = game.getResult();
			if (result.getResult() == MinesweeperResultType.SIMULATION_EXCEPTION) {
				throw new RuntimeException("Game failed.", result.getExecption());
			}
		}
		
		return game.getResult();		
	}
	
	// ==============
	// STATIC METHODS
	// ==============
	
	public static MinesweeperResult playConfig(MinesweeperConfig config) {
		Minesweeper minesweeper = new Minesweeper(config);
		return minesweeper.play();
	}
	
	public static MinesweeperResult playAgent(String id, int width, int height, int totalMines, long timeoutMillis, int randomSeed, boolean visualization, IAgent agent) {
		MinesweeperConfig config = new MinesweeperConfig();
		
		config.id = id;
		config.width = width;
		config.height = height;
		config.randomSeed = randomSeed;
		config.random = new Random(randomSeed);
		config.totalMines = totalMines;
		config.agent = agent;
		config.visualization = visualization;
		config.timeoutMillis = timeoutMillis;
		
		return playConfig(config);
	}
	
	public static MinesweeperResult playHuman(int width, int height, int totalMines, long timeoutMillis, int randomSeed) {
		return playAgent("Human-" + width + "x" + height + "-M:" + totalMines + "-R:" + randomSeed, width, height, totalMines, timeoutMillis, randomSeed, true, new HumanAgent());		
	}
	
	public static MinesweeperResult playHuman(int width, int height, int totalMines, int randomSeed) {
		return playHuman(width, height, totalMines, -1, randomSeed);		
	}
	
	// ===========
	// MAIN METHOD
	// ===========
	
	public static void main(String[] args) {
		playHuman(10, 10, 10, 10);
	}

}
