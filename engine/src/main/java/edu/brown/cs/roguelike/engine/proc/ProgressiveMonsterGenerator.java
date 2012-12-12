package edu.brown.cs.roguelike.engine.proc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.config.MonsterTemplate;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;

public class ProgressiveMonsterGenerator implements MonsterGenerator {

	//CONSTANTS
	private static final int minRoomCount = 4;
	private static final int maxRoomCount = 6;
	
	private static final float lowerChance = 0.05f;
	private static final float higherChance = 0.02f;
	
	//END CONSTANTS
	
	ArrayList<MonsterTemplate> templates;
	Hashtable<Integer,List<MonsterTemplate>> tiers = new Hashtable<Integer,List<MonsterTemplate>>();
	HashSet<Room> populatedRooms;
	
	RandomGen rand = new RandomGen(System.nanoTime());

	private String configDir;

	public ProgressiveMonsterGenerator(String configDir) {
		this.configDir = configDir;
	}

	@Override
	public void populateLevel(Level level) throws ConfigurationException {
		int roomNum;

		Config c = new Config(configDir);
		templates = c.loadMonsterTemplate();
		
		//Divide into tiers
		for(MonsterTemplate t : templates) {
			List<MonsterTemplate> tierList = tiers.get(t.tier);
			if(tierList == null) {
				tierList = new ArrayList<MonsterTemplate>();
			}
			tierList.add(t);
			tiers.put(t.tier, tierList);
		}
		
		populatedRooms = new HashSet<Room>();
		int roomCounter = 0;
		int roomsToPopulate = getRoomCount();
		while(roomCounter < roomsToPopulate)
		{
			roomNum = rand.getRandom(level.getRooms().size());
			Room r = level.getRooms().get(roomNum);
			if(! populatedRooms.contains(r)) {
				addMonster(level,r,getRandomMonster(level));
				populatedRooms.add(r);
				roomCounter++;
			}
			if(populatedRooms.size() == level.getRooms().size()) {
				populatedRooms.clear(); //Clears the set in the case where the number of rooms is less then the ammount we want to populate
			}
		}

      
	}

	/**Gets a random monster that is applicable to the level:
	 * 
	 * Roll Order:
	 * 	lower tier;
	 * 	higher tier;
	 * 	
	 *  else base;
	 * 
	 * @return A random monster
	 */
	private Monster getRandomMonster(Level level) {
		
		int tier = level.getTier();
		int tierDiff = level.getTierDiff();

		
		if(Math.random() <= lowerChance/tierDiff)
			tier -= 1;
		else if(Math.random() <= higherChance*tierDiff)
			tier += 1;
		
		if(tier == 0) {
			tier = 1;
		}
		if(tier > tiers.size()) {
			tier = tiers.size();
		}
		List<MonsterTemplate> tierList = tiers.get(tier);
		
		return (new Monster(tierList.get(rand.getRandom(tierList.size()))));
	}

	/**Produces the number of rooms to populate**/
	private int getRoomCount() {
		return rand.getRandom(minRoomCount,maxRoomCount);
	}

	/**Adds a monster to a random tile in the room**/
	private void addMonster(Level l, Room r, Combatable m) {
		//TODO: Randomly place a monster on a tile in the room
		boolean placedMonster = false;
		do {
			int mX = rand.getRandom(r.min.x, r.max.x);
			int mY = rand.getRandom(r.min.y, r.max.y);
			Tile t = l.getTiles()[mX][mY];
			if(t.getEntity() == null) {
				t.setEntity(m);
				l.getManager().register(m);
				placedMonster = true;
			}
		}
		while(!placedMonster);
	}

}
