package edu.brown.cs.roguelike.engine.proc;

import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.entities.Jewel;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;

public class JewelGenerator implements ItemGenerator {

	RandomGen rand = new RandomGen(System.nanoTime());
	
	@Override
	public void populateLevel(Level level) throws ConfigurationException {
			
			int roomNum;
		for (int i = 0 ; i < getItemCount(); i++) {
			roomNum = rand.getRandom(level.getRooms().size());
			Room r = level.getRooms().get(roomNum);
			addItem(level,r,new Jewel());
		}
	}
	

	
	private int getItemCount() {
		return rand.getRandom(4, 25);
	}

	private void addItem(Level l, Room r, Stackable item) {
			int mX = rand.getRandom(r.min.x, r.max.x);
			int mY = rand.getRandom(r.min.y, r.max.y);
			Tile t = l.getTiles()[mX][mY];
			t.getStackables().add(item);
	}
	
}

