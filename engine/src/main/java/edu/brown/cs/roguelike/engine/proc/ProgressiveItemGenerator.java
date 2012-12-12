package edu.brown.cs.roguelike.engine.proc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.config.WeaponNameTemplate;
import edu.brown.cs.roguelike.engine.config.WeaponTemplate;
import edu.brown.cs.roguelike.engine.entities.Jewel;
import edu.brown.cs.roguelike.engine.entities.Potion;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.entities.Stats;
import edu.brown.cs.roguelike.engine.entities.Weapon;
import edu.brown.cs.roguelike.engine.entities.events.ChangeHP;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;

public class ProgressiveItemGenerator implements ItemGenerator {

	private static final float lowerChance = 0.05f;
	private static final float higherChance = 0.10f;
	private static final float prefixChance = .75f;
	private static final float suffixChance = .75f;

	
	RandomGen rand = new RandomGen(System.nanoTime());
	ArrayList<WeaponNameTemplate> weaponNameTemplates;
	ArrayList<WeaponTemplate> weaponTemplates;

	Hashtable<Integer,WeaponNameTemplate> weaponNameTiers = new Hashtable<Integer,WeaponNameTemplate>();
	Hashtable<Integer,List<WeaponTemplate>> weaponTiers = new Hashtable<Integer,List<WeaponTemplate>>();

	
	@Override
	public void populateLevel(Level level) throws ConfigurationException {

		Config c = new Config("../config");
		weaponNameTemplates = c.loadWeaponNameTemplate();
		weaponTemplates = c.loadWeaponTemplate();


		//Divide into tiers
		for(WeaponNameTemplate t : weaponNameTemplates) {
			weaponNameTiers.put(t.tier, t);
		}
		
		for(WeaponTemplate t : weaponTemplates) {
			List<WeaponTemplate> tierList = weaponTiers.get(t.tier);
			if(tierList == null) {
				tierList = new ArrayList<WeaponTemplate>();
			}
			tierList.add(t);
			weaponTiers.put(t.tier, tierList);
		}
		
		
		
		int roomNum;
		for (int i = 0 ; i < getItemCount(); i++) {
			roomNum = rand.getRandom(level.getRooms().size());
			Room r = level.getRooms().get(roomNum);
			addItem(level,r,generateRandomItem(level));
		}
	}



	private Stackable generateRandomItem(Level level) {
		switch(rand.getRandom(1,2)) {
		case 1:
			return new Potion(Color.RED,new ChangeHP(5));
		case 2: 
			return	generateWeapon(level);
		default:
			return new Jewel();
		}
	}



	private Weapon generateWeapon(Level level) {
		int tier = level.getTier();
		int tierDiff = level.getTierDiff();

		
		if(Math.random() <= lowerChance/tierDiff)
			tier -= 1;
		else if(Math.random() <= higherChance*tierDiff)
			tier += 1;
		
		if(tier == 0) {
			tier = 1;
		}
		if(tier > Math.min(weaponNameTiers.size(),weaponTiers.size())) {
			tier = weaponNameTiers.size();
		}
		WeaponNameTemplate adjs = weaponNameTiers.get(tier);
		
		String prefix = "";
		String suffix = "";
		String material = "";
		
		if(adjs.prefixes.length > 0) {
			if(Math.random() < prefixChance) {
				prefix = adjs.prefixes[rand.getRandom(0,adjs.prefixes.length-1)];
			}
		}
		if(adjs.suffixes.length > 0) {
			if(Math.random() < suffixChance) {
				suffix = adjs.suffixes[rand.getRandom(0,adjs.suffixes.length-1)];
			}
		}
		if(adjs.materials.length > 0) {
				material = adjs.materials[rand.getRandom(0,adjs.materials.length-1)];
		}
		
		List<WeaponTemplate> weaponList = weaponTiers.get(tier);
		WeaponTemplate  w = weaponList.get(rand.getRandom(weaponList.size()));
		
		String desc = (prefix == "" ? prefix : prefix+" ") + 
				(material == "" ? material : material+" ") + w.name + (suffix == "" ? suffix : " "+suffix);
		
		return new Weapon(new Stats(10,0,0), desc, w.damageType);
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

