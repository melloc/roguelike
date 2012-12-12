package edu.brown.cs.roguelike.engine.proc;

import java.util.ArrayList;

import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.config.MonsterTemplate;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;

public class RandomMonsterGenerator implements MonsterGenerator {


	ArrayList<MonsterTemplate> templates;
	RandomGen rand = new RandomGen(System.nanoTime());
	
	@Override
	public void populateLevel(Level level) throws ConfigurationException {
		int roomNum;
		
		Config c = new Config("../config");
		templates = c.loadMonsterTemplate();
		
		for (int i = 0 ; i < getMonsterCount(); i++) {
			roomNum = rand.getRandom(level.getRooms().size());
			Room r = level.getRooms().get(roomNum);
			addMonster(level,r,getRandomMonster(level));
		}
	}
	
	private Monster getRandomMonster(Level level) {
		return (new Monster(templates.get(rand.getRandom(templates.size())), level));
	}
	
	private int getMonsterCount() {
		//TODO: Produce a number of monsters
		return 6;
	}

	private void addMonster(Level l, Room r, Monster m) {
		// Register the Monster with the level's EntityManager.
		l.getManager().register(m);

		//TODO: Randomly place a monster on a tile in the room
		boolean placedMonster = false;
		do {
			int mX = rand.getRandom(r.min.x, r.max.x);
			int mY = rand.getRandom(r.min.y, r.max.y);
			Tile t = l.getTiles()[mX][mY];
			if(t.getEntity() == null) {
				t.setEntity(m);
				m.setLocation(t);
				placedMonster = true;
			}
		}
		while(!placedMonster);
	}
	
}
