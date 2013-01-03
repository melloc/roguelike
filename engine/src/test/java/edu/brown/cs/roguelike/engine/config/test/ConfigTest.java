package edu.brown.cs.roguelike.engine.config.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs.roguelike.engine.config.Config;
import edu.brown.cs.roguelike.engine.config.Config.ConfigType;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.config.ContextTemplate;
import edu.brown.cs.roguelike.engine.config.MonsterTemplate;

public class ConfigTest {
	
	@Test
	public void test() throws ConfigurationException, 
	JsonGenerationException, JsonMappingException, IOException {
		
		File cd = new File("../config-test");
		if (!cd.exists()) cd.mkdir();
		if (!cd.isDirectory()) { cd.delete(); cd.mkdir(); }
		
		File mf = new File("../config-test/" + 
				Config.REQUIRED_FILES.get(ConfigType.MONSTER) + Config.CFG_EXT);
		
		if (mf.exists()) mf.delete(); 
		
		mf.createNewFile();
		
		Config c = new Config("../config-test");
		
		MonsterTemplate mt1 = new MonsterTemplate("Rat", 'R', "Red", 10, 3, 11, 1, 2);
		MonsterTemplate mt2 = new MonsterTemplate("Mangy Dog", 'D',"Blue", 20, 3, 5, 1, 2);
		
		ArrayList<MonsterTemplate> mts = new ArrayList<MonsterTemplate>();
		mts.add(mt1);
		mts.add(mt2);
		
		ObjectMapper om = new ObjectMapper();
		om.writeValue(new File("../config-test/monsters.cfg"), mts);

		// load the monster template
		ArrayList<MonsterTemplate> mts_loaded = c.loadMonsterTemplate();
		
		assertEquals(mts_loaded, mts);
	}

    @Test
    public void testLoadKeys() throws ConfigurationException, 
	JsonGenerationException, JsonMappingException, IOException {
		
		Config c = new Config("../config-test");
		
		// load the monster template
		ArrayList<ContextTemplate> ctx_loaded = c.loadContextTemplate();
        ContextTemplate game = ctx_loaded.get(0);
		
		assertEquals(game.context, "game");
        assertEquals(game.bindings.get("h"), "left");
        assertEquals(game.bindings.get("j"), "down");
        assertEquals(game.bindings.get("k"), "up");
        assertEquals(game.bindings.get("l"), "right");
	}


}
