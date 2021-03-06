package edu.brown.cs.roguelike.engine.proc;

import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.level.Level;

public interface MonsterGenerator {
	public void populateLevel(Level level) throws ConfigurationException;
}
