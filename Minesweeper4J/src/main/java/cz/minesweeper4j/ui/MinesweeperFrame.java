package cz.minesweeper4j.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cz.cuni.amis.clear2d.engine.C2DFrame;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public class MinesweeperFrame extends C2DFrame {

	/**
	 * Auto-generated.
	 */
	private static final long serialVersionUID = 5421140837229980680L;
	
	private KeyListener keyListener;

	public MinesweeperFrame(Board board, IAgent agent) {
		super("Minesweeper4J", new MinesweeperPanel(board, agent));
		
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					getPanel().showReal = false;
					getPanel().updateBoard();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					getPanel().showReal = true;
					getPanel().updateBoard();
				}
			}
		};
		
		addKeyListener(keyListener);
	}
	
	public MinesweeperPanel getPanel() {
		return (MinesweeperPanel)panel;
	}

}
