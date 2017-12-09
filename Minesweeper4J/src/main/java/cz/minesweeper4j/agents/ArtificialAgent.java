package cz.minesweeper4j.agents;

import java.awt.event.KeyEvent;

import cz.minesweeper4j.simulation.actions.Action;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public abstract class ArtificialAgent implements IAgent {

	private Action action;
	
	private Board board;
		
	private ThinkThread thread;
	
	private Object mutex = new Object();
	
	private RuntimeException agentException;
	
	protected ArtificialAgentActions actions = new ArtificialAgentActions();

	@Override
	public void newBoard() {
		action = null;
		board = null;
		agentException = null;
	}

	@Override
	public void observe(Board board) {
		this.board = board;
	}

	@Override
	public Action act() {
		// HAVE ACTION?
		if (action != null) {
			// => EXECUTE
			Action action = this.action;
			this.action = null;
			return action;
		}
		
		// OTHERWISE THINK!
		synchronized(mutex) {
			if (agentException != null) {
				throw agentException;
			}
			ensureThinkThread();
			if (action == null) {
				thread.think = true;
			}
			if (action != null) {
				// => EXECUTE
				Action action = this.action;
				this.action = null;
				return action;
			}
		}
		return null;
	}
	
	protected abstract Action think(final Board board);
	
	@Override
	public void tileClicked(int tileX, int tileY, boolean rightBtn) {		
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
	}

	@Override
	public void victory() {
		stopThinkThread();		
	}
	
	@Override
	public void died() {
		stopThinkThread();		
	}
	
	@Override
	public void stop() {
		stopThinkThread();
	}
	
	private void ensureThinkThread() {
		synchronized(mutex) {
			if (thread != null && thread.running) return;
			thread = new ThinkThread();
			thread.start();
		}
	}
	
	private void stopThinkThread() {
		synchronized(mutex) {
			if (thread != null) {
				thread.shouldRun = false;
				thread.interrupt();
				thread = null;
			}
		}
	}
	
	protected class ThinkThread extends Thread {
		
		public boolean running = true;
		
		public boolean shouldRun = true;
		
		public boolean think = false;
		
		public ThinkThread() {
			super("ThinkThread");
		}
		
		@Override
		public void run() {
			try {
				while (shouldRun && !interrupted()) {
					while (!think) {
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							throw new RuntimeException("Interrupted on sleep");
						}
					}
					Action thinkAction = think(board);
					synchronized(mutex) {
						if (ArtificialAgent.this.thread == this) {
							action = thinkAction;
						}
						think = false;
					}
				}
			} catch (Exception e) {
				synchronized(mutex) {
					agentException = new RuntimeException("ThinkThread failed.", e);
				}
			} finally {
				running = false;
			}
		}
		
	}

	
}
