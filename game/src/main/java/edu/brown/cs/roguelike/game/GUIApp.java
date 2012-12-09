package edu.brown.cs.roguelike.game;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.game.Game;
import edu.brown.cs.roguelike.engine.graphics.Application;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.save.SaveManager;

public class GUIApp extends Application {

	private final static Vec2i SCREEN_SIZE = new Vec2i(80,30);
	
	private SaveManager sm;
	private BSPLevelGenerator lg;
	
	public GUIApp() {
		super("demo");
		this.sm = new SaveManager("mainSave");
		this.lg = new BSPLevelGenerator();
	}
	
	public SaveManager getSaveManager() { return sm; }
	public BSPLevelGenerator getLevelGenerator() { return lg; }

	@Override
	protected boolean initialize(Vec2i screenSize) {
		RogueGame rg = null;
		try {
			// Attempt to load a save game
			Game g = sm.loadGame();
			if (g instanceof RogueGame) rg = (RogueGame)g;
			this.layers.push(new MainLayer(
					this, rg, SCREEN_SIZE, "Succesful Load"));
			return true;
		} catch (Exception e) {
			rg = new RogueGame();
			this.layers.push(new MainLayer(this, rg, SCREEN_SIZE,
					"No save game found. Press 'N' to generate a new level."));
			return false;
		}
	}
	
}
