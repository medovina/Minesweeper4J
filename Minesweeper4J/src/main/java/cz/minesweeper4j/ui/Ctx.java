package cz.minesweeper4j.ui;

import java.io.InputStream;

import cz.cuni.amis.clear2d.Clear2D;
import cz.cuni.amis.clear2d.Clear2DConfig;
import cz.cuni.amis.clear2d.engine.fonts.C2DFonts;
import cz.cuni.amis.clear2d.engine.textures.TextureAtlas;
import cz.cuni.amis.clear2d.engine.textures.TextureAtlasResource;

public class Ctx {
	
	public static Clear2D engine;
	
	public static TextureAtlas atlas;
	
	public static boolean inited = false;
	
	public static void init() {
		if (inited) return;
		inited = true;
		
		Clear2DConfig cfg = new Clear2DConfig();
		cfg.fps = 30;
		
		C2DFonts.init();
		
		InputStream xmlStream = Ctx.class.getClassLoader().getResourceAsStream("cz/minesweeper4j/ui/atlas/sprites.xml");
		atlas = new TextureAtlasResource(xmlStream, "cz/minesweeper4j/ui/atlas/");
		try { xmlStream.close(); } catch (Exception e) {}
		
		engine = Clear2D.engine;		
		engine.start(cfg);		
	}
	
	public static void die() {
		engine.stop();
		engine = null;
		inited = false;
	}

}
