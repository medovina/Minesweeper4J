package cz.minesweeper4j.ui;

import java.awt.Color;
import java.awt.event.KeyListener;

import cz.cuni.amis.clear2d.engine.C2DPanelStandalone;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;
import cz.cuni.amis.clear2d.engine.math.Vector2;
import cz.cuni.amis.clear2d.engine.prefabs.FPS;
import cz.cuni.amis.clear2d.engine.prefabs.Label;
import cz.cuni.amis.clear2d.engine.prefabs.Quad;
import cz.cuni.amis.clear2d.engine.prefabs.Sprite;
import cz.minesweeper4j.simulation.agent.IAgent;
import cz.minesweeper4j.simulation.board.oop.Board;
import cz.minesweeper4j.simulation.board.oop.ETile;
import cz.minesweeper4j.simulation.board.oop.Tile;

public class MinesweeperPanel extends C2DPanelStandalone {

	/**
	 * Auto-generated.
	 */
	private static final long serialVersionUID = -2874781181897319530L;
	
	private Board board;
	
	private Sprite[][] boardView;

	protected IAgent agent;
	
	private MouseListener mouseListener;
	
	private Label victory;
	private Label boom;
	
	private Quad free;
	
	private Quad selection;
	
	protected boolean showReal = false;

	private KeyListener keyListener;
	
	private FPS fps;

	public MinesweeperPanel(Board board, IAgent agent) {
		super(board.width * 24, board.height * 24, new Color(217, 244, 255));
		
		initBoard(board);
		
		this.agent = agent;
		mouseListener = new MouseListener(this);
		addMouseListener(mouseListener);	
		addMouseMotionListener(mouseListener);
		
		this.victory = new Label(C2DFonts.inconcolata_12px_green, "VICTORY!", Color.BLACK, Color.GREEN);
		this.boom = new Label(C2DFonts.inconcolata_12px_red, "BOOM!", Color.BLACK, Color.RED);
		
		this.victory.pos = new Vector2(board.width * 12 - this.victory.cLabel.cBackground.getWidth() / 2, board.height * 12 - this.victory.cLabel.cBackground.getHeight() / 2);
		this.boom.pos = new Vector2(board.width * 12 - this.boom.cLabel.cBackground.getWidth() / 2, board.height * 12 - this.boom.cLabel.cBackground.getHeight() / 2);
		
		scene.root.addChild(this.victory);
		scene.root.addChild(this.boom);
		
		this.victory.setEnabled(false);
		this.boom.setEnabled(false);		
		
		free = new Quad();
		free.init(24, 24, new Color(0, 0, 0, 0), Color.WHITE);
		scene.root.addChild(free);
		free.setEnabled(false);
		
		selection = new Quad();
		selection.init(24, 24, new Color(0, 0, 0, 0), Color.YELLOW);
		scene.root.addChild(selection);
		selection.setEnabled(false);
		
//		fps = new FPS(C2DFonts.inconcolata_10px_black);
//		scene.root.addChild(fps);
	}

	private void initBoard(Board board) {
		this.board = board;
		
		boardView = new Sprite[board.width][board.height];
		
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				boardView[x][y] = new Sprite(getTexture(board.tile(x,  y)));
				boardView[x][y].pos = new Vector2(x * 24, y * 24);
				scene.root.addChild(boardView[x][y]);
			}
		}
	}
	
	private IDrawable getTexture(Tile tile) {
		if (showReal) return getRealTexture(tile);
		return getPlayerTexture(tile);
	}
	
	private TextureAtlasEnum getPlayerTextureEnum(Tile tile) {
		if (tile.visible) {
			if (tile.type == ETile.MINE) return TextureAtlasEnum.Mine;
			if (tile.mines == null || tile.mines == 0) {
				return TextureAtlasEnum.Empty;
			}
			switch (tile.mines) {
			case 1: return TextureAtlasEnum.N1;
			case 2: return TextureAtlasEnum.N2;
			case 3: return TextureAtlasEnum.N3;
			case 4: return TextureAtlasEnum.N4;
			case 5: return TextureAtlasEnum.N5;
			case 6: return TextureAtlasEnum.N6;
			case 7: return TextureAtlasEnum.N7;
			case 8: return TextureAtlasEnum.N8;
			default:
				throw new RuntimeException("Invalid number of nearby mines: " + tile.mines);
			}
		} else {
			if (tile.flag) return TextureAtlasEnum.Flag;
			return TextureAtlasEnum.Slot;
		}
	}
	
	private IDrawable getPlayerTexture(Tile tile) {
		return Ctx.atlas.getSubtexture(getPlayerTextureEnum(tile).texture);
	}
	
	private TextureAtlasEnum getRealTextureEnum(Tile tile) {
		if (tile.type == ETile.MINE) return TextureAtlasEnum.Mine;
		if (tile.flag) return TextureAtlasEnum.Flag;
		if (tile.mines == null || tile.mines == 0) {
			return TextureAtlasEnum.Empty;
		}
		switch (tile.mines) {
		case 1: return TextureAtlasEnum.N1;
		case 2: return TextureAtlasEnum.N2;
		case 3: return TextureAtlasEnum.N3;
		case 4: return TextureAtlasEnum.N4;
		case 5: return TextureAtlasEnum.N5;
		case 6: return TextureAtlasEnum.N6;
		case 7: return TextureAtlasEnum.N7;
		case 8: return TextureAtlasEnum.N8;
		default:
			throw new RuntimeException("Invalid number of nearby mines: " + tile.mines);
		}	
	}
	
	private IDrawable getRealTexture(Tile tile) {
		return Ctx.atlas.getSubtexture(getRealTextureEnum(tile).texture);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, boolean rightBtn) {
		if (board.boom) return;
		if (board.isVictory()) return;
		
		if (agent != null) {
			int tileX = (mouseX-4) / 24;
			int tileY = (mouseY-4) / 24;
			if (tileX < 0) tileX = 0;
			else if (tileX >= board.width) tileX = board.width;
			if (tileY < 0) tileY = 0;
			else if (tileY >= board.height) tileY = board.height;
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
			selection.setEnabled(true);
			selection.pos.x = tileX * 24;
			selection.pos.y = tileY * 24;
		}
	}
	
	public void updateBoardView() {
		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				boardView[x][y].cSprite.texture = getTexture(board.tile(x, y));
			}
		}
		
		if (board.boom) {
			boom.setEnabled(true);
			return;
		}
		
		if (board.isVictory()) {
			victory.setEnabled(true);
			return;
		}
		
		if (board.safeTilePos == null) {
			free.setEnabled(false);
		} else {
			free.setEnabled(true);
			free.pos.x = 24 * board.safeTilePos.x;
			free.pos.y = 24 * board.safeTilePos.y;
		}
		
	}

}
