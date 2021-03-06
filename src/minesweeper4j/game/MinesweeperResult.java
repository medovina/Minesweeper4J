package minesweeper4j.game;

public class MinesweeperResult {
	
	public static enum MinesweeperResultType {
		
		/**
		 * Simulation ended with agent winning the game.
		 */
		VICTORY(0),
		
		/**
		 * Simulation ended with agent died by uncovering a bomb. BOOM!
		 */
		DEATH(1),
		
		/**
		 * Simulation ended with timeout; agent failed to finish the game.
		 */
		TIMEOUT(2),
		
		/**
		 * Simulation ended with an agent exception; agent failed. 
		 */
		AGENT_EXCEPTION(3),
		
		/**
		 * Simulation ended with simulation exception; simulation failed.
		 */
		SIMULATION_EXCEPTION(4),
		
		/**
		 * Simulation has been terminated from the outside.
		 */
		TERMINATED(5);
		
		private int exitValue;

		private MinesweeperResultType(int exitValue) {
			this.exitValue = exitValue;
		}

		public int getExitValue() {
			return exitValue;
		}
		
		public static MinesweeperResultType getForExitValue(int exitValue) {
			for (MinesweeperResultType value : MinesweeperResultType.values()) {
				if (value.exitValue == exitValue) return value;
			}
			return null;
		}

	}
	
	private String id = null;
	
	private IAgent agent = null;
	
	private String level = null;
	
	private MinesweeperResultType result = null;
	
	private Throwable exception;
	
	private int steps = 0;
	
	private long simStartMillis = 0;
	
	private long simEndMillis = 0;
	
	private int safeTileSuggestions = 0;
	
	public MinesweeperResult() {		
	}

	/**
	 * Assigned ID given to this simulation.
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Agent that was running in simulation.
	 * @return
	 */
	public IAgent getAgent() {
		return agent;
	}

	public void setAgent(IAgent agent) {
		this.agent = agent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Result of the simulation.
	 * @return
	 */
	public MinesweeperResultType getResult() {
		return result;
	}

	public void setResult(MinesweeperResultType result) {
		this.result = result;
	}

	/**
	 * How many steps an agent performed.
	 * @return
	 */
	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * Time the simulation started in milliseconds (obtained via {@link System#currentTimeMillis()}.
	 * @return
	 */
	public long getSimStartMillis() {
		return simStartMillis;
	}

	public void setSimStartMillis(long simStartMillis) {
		this.simStartMillis = simStartMillis;
	}

	/**
	 * Time the simulation ended in milliseconds (obtained via {@link System#currentTimeMillis()}.
	 * @return
	 */
	public long getSimEndMillis() {
		return simEndMillis;
	}

	public void setSimEndMillis(long simEndMillis) {
		this.simEndMillis = simEndMillis;
	}
	
	/**
	 * How long the simulation run in milliseconds.
	 * @return
	 */
	public long getSimDurationMillis() {
		return simEndMillis - simStartMillis;
	}

	/**
	 * Exception caught during the simulation; 
	 * filled in case of {@link #getResult()} == {@link MinesweeperResultType#AGENT_EXCEPTION} or {@link MinesweeperResultType#SIMULATION_EXCEPTION}.  
	 * @return
	 */
	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable execption) {
		this.exception = execption;
	}
		
	/**
	 * How many times the agent has been suggested with a safe tile; typically the minimum number is 1 (the first suggestion at the beginning of the game).
	 * @return
	 */
	public int getSafeTileSuggestions() {
		return safeTileSuggestions;
	}

	public void setSafeTileSuggestions(int safeTileSuggestions) {
		this.safeTileSuggestions = safeTileSuggestions;
	}

	@Override
	public String toString() {
		return "MinesweeperResult[" + getResult() + "|steps=" + steps + ",#advices=" + safeTileSuggestions + "]";
	}
	
}
