package cz.minesweeper4j.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;

public class MinesweeperFrame extends JFrame {
	private static final long serialVersionUID = 0;
    
    MinesweeperPanel panel;
	private KeyListener keyListener;

	public MinesweeperFrame(Board board, IAgent agent) {
        super("Minesweeper4J");
        panel = new MinesweeperPanel(board, agent);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					getPanel().showReal = false;
					getPanel().updateBoardView();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					getPanel().showReal = true;
					getPanel().updateBoardView();
				}
				getPanel().agent.keyPressed(e);
			}
		};
		
		addKeyListener(keyListener);
		
		setResizable(false);
	}
	
	public MinesweeperPanel getPanel() {
		return panel;
	}

}
