package cz.minesweeper4j.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.*;

public class MinesweeperPanel extends JPanel {
	private static final long serialVersionUID = 0;
	
	private Board board;
	
	protected IAgent agent;
	
	private MouseListener mouseListener;
	
    protected boolean showReal = false;
    
    Sprites sprites = new Sprites();

    int cursorX = -1, cursorY = -1;

	public MinesweeperPanel(Board board, IAgent agent) {
        setBackground(new Color(217, 244, 255));
        
        setPreferredSize(new Dimension(board.width * 24, board.height * 24));
		
		this.board = board;
		
		this.agent = agent;
		mouseListener = new MouseListener(this);
		addMouseListener(mouseListener);	
		addMouseMotionListener(mouseListener);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        for (int x = 0 ; x < board.width ; ++x)
            for (int y = 0 ; y < board.height ; ++y) {
                Tile t = board.tiles[x][y];
                BufferedImage i;
                if (t.visible || showReal)
                    if (t.type == ETile.MINE)
                        i = sprites.mine;
                    else if (t.type == ETile.FREE)
                        if (t.mines == null || t.mines == 0)
                            i = sprites.empty;
                        else i = sprites.nums[t.mines];
                    else throw new Error("unknown type");
                else
                    i = t.flag ? sprites.flag : sprites.hidden;
                
                g.drawImage(i, 24 * x, 24 * y, null);
            }

        if (board.safeTilePos != null) {
            g.setColor(Color.WHITE);
            g.drawRect(24 * board.safeTilePos.x, 24 * board.safeTilePos.y, 24, 24);
        }

        if (cursorX >= 0) {
            g.setColor(Color.YELLOW);
            g.drawRect(24 * cursorX, 24 * cursorY, 24, 24);
        }
    }

	protected void mouseClicked(int mouseX, int mouseY, boolean rightBtn) {
		if (board.boom) return;
		if (board.isVictory()) return;
		
		if (agent != null) {
			int tileX = (mouseX-4) / 24;
			int tileY = (mouseY-4) / 24;
			if (tileX < 0) tileX = 0;
			else if (tileX >= board.width) tileX = board.width-1;
			if (tileY < 0) tileY = 0;
			else if (tileY >= board.height) tileY = board.height-1;
			agent.tileClicked(tileX, tileY, rightBtn);
		}
	}
	
	protected void mouseMoved(int mouseX, int mouseY) {
		if (board.boom) return;
		if (board.isVictory()) return;
		
		if (agent != null) {
			int tileX = (mouseX-4) / 24;
			int tileY = (mouseY-4) / 24;
			if (tileX < 0) tileX = 0;
			else if (tileX >= board.width) tileX = board.width;
			if (tileY < 0) tileY = 0;
            else if (tileY >= board.height) tileY = board.height;

            cursorX = tileX;
            cursorY = tileY;
            repaint();
		}
	}
	
	public void updateBoardView() {
		repaint();
	}

}
