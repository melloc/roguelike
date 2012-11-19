package edu.brown.cs.roguelike.engine.config.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.config.MonsterTemplate;

public class ConfigTest {
	
	@Test
	public void test() throws ConfigurationException, 
	JsonGenerationException, JsonMappingException, IOException {
		Config c = new Config("../config-test");
		
		MonsterTemplate mt1 = new MonsterTemplate("Rat", 'R', "Red", 10);
		MonsterTemplate mt2 = new MonsterTemplate("Mangy Dog", 'D',"Blue", 20);
		
		ArrayList<MonsterTemplate> mts = new ArrayList<MonsterTemplate>();
		mts.add(mt1);
		mts.add(mt2);
		
		ObjectMapper om = new ObjectMapper();
		om.writeValue(new File("../config-test/monsters.cfg"), mts);

		// load the monster template
		ArrayList<MonsterTemplate> mts_loaded = c.loadMonsterTemplate();
		
		assertEquals(mts_loaded, mts);
	}

}
