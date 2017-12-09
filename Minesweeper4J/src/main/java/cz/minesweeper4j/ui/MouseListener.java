package cz.minesweeper4j.ui;

import java.awt.event.MouseEvent;

import cz.minesweeper4j.simulation.agent.IAgent;

public class MouseListener implements java.awt.event.MouseListener{

	private MinesweeperPanel panel;

	public MouseListener(MinesweeperPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		panel.clicked(e.getX(), e.getY(), e.getButton() != MouseEvent.BUTTON1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
