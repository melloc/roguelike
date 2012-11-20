package edu.brown.cs.roguelike.engine.proc;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;

public class RandomMonsterGenerator implements MonsterGenerator {

	RandomGen rand = new RandomGen(System.nanoTime());
	
	@Override
	public void populateLevel(Level level) {
		int roomNum;
		for (int i = 0 ; i < getMonsterCount(); i++) {
			roomNum = rand.getRandom(level.getRooms().size());
			Room r = level.getRooms().get(roomNum);
			addMonster(level,r,getRandomMonster());
		}
	}
	
	private Monster getRandomMonster() {
		//TODO: Get a random monster
		switch(rand.getRandom(4)) {
		
		case 0:
			return new Monster('D',Color.CYAN);
		case 1:
			return new Monster('g',Color.GREEN);
		case 2:
			return new Monster('U',Color.MAGENTA);
		 default:
			return new Monster('M',Color.RED);
		}
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
