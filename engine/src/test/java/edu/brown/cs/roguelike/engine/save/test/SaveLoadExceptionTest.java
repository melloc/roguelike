package edu.brown.cs.roguelike.engine.save.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.proc.RoomGenerator;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;
import edu.brown.cs.roguelike.engine.save.SaveManager;

public class SaveLoadExceptionTest {
	
	/**
	 * Tests a round trip save-and-load
	 */
	@Test
	public void roundTrip() throws SaveLoadException {
		
		String saveFile = "testSave.rog";
		
		SaveManager sm = new SaveManager(saveFile);
		
		RoomGenerator rg = new RoomGenerator();
		
		Level level = rg.generateLevel(new Vec2i(50,200));
		
		sm.saveLevel(level);
		
		Level loadedLevel = sm.loadLevel();
		
		assertEquals(level, loadedLevel);
	}
}
