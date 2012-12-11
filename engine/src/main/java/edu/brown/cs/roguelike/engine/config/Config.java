package edu.brown.cs.roguelike.engine.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * 
 * Responsible for loading configuration files, and for demarshalling
 * objects such as monsters.
 * 
 * @author liam
 *
 */
public class Config {

	public enum ConfigType {
		MONSTER,WEAPON_ADJ,WEAPON_TYPE
	}

	public static final String CFG_EXT = ".cfg";

	/**
	 * All of the required configuration files
	 */
	public static final HashMap<ConfigType,String> REQUIRED_FILES = 
			new HashMap<ConfigType, String>();

	static {
		REQUIRED_FILES.put(ConfigType.MONSTER, "monsters");
		REQUIRED_FILES.put(ConfigType.WEAPON_ADJ, "weapon_adjs");
		REQUIRED_FILES.put(ConfigType.WEAPON_TYPE, "weapon_types");
	}

	private File dir;

	public Config(String configDir) throws ConfigurationException {
		this.dir = new File(configDir);
		validateDir();
	}

	/**
	 * Superficially validates the config directory, making sure
	 * that it contains all the required files, and that they are non-empty.
	 * Does not validate their data. This is a simply fail-fast convenience
	 * 
	 * Also validates the requiredFiles static array to make sure
	 * there are no duplicates, as this will cause the rest of validation
	 * to be inaccurate
	 */
	private void validateDir() throws ConfigurationException {

		if (!dir.isDirectory()) 
			throw new ConfigurationException(
					"Provided configuration directory "
							+ dir.getAbsolutePath() + " is not a directory");


		// get all configuration files 
		File[] configFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(CFG_EXT);
			}
		});

		// do we have all necessary config files?
		HashSet<String> filesPresent = new HashSet<String>();

		String missingFiles = "";

		for (File f : configFiles) {
			String fileStr = f.getName().replaceAll(CFG_EXT, "");
			filesPresent.add(fileStr);
		}

		for (String f : REQUIRED_FILES.values()) {
			if (!filesPresent.contains(f)) missingFiles += f+", ";
		}

		// if we're missing any config files, throw exception
		if (!missingFiles.isEmpty())
			throw new ConfigurationException(
					"missing configuration file(s): " + missingFiles); 

	}

	/**
	 * Build an array of MonsterTemplates used for the instantiation of 
	 * monsters
	 * 
	 * @return ArrayList<MonsterTemplate> array of monster templates for
	 * each monster in the configuration file
	 * @throws ConfigurationException 
	 */
	public ArrayList<MonsterTemplate> loadMonsterTemplate() throws ConfigurationException {

		ObjectMapper om = new ObjectMapper();

		ArrayList<MonsterTemplate> ret = null;

		try {

			TypeFactory t = TypeFactory.defaultInstance();

			String file = dir.getAbsolutePath() + "/" +
					REQUIRED_FILES.get(ConfigType.MONSTER).concat(CFG_EXT);

			ret = om.readValue(
					new File(file), t.constructCollectionType(ArrayList.class,MonsterTemplate.class));

		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
		return ret;
	}


	/**Builds an array of WeaponTemplates used for the instantion of Weapons 
	 *	Wepon templates represent the type of weapon and its damage type
	 **/
	public ArrayList<WeaponTemplate> loadWeaponTemplate() throws ConfigurationException {

		ObjectMapper om = new ObjectMapper();

		ArrayList<WeaponTemplate> ret = null;

		try {

			TypeFactory t = TypeFactory.defaultInstance();

			String file = dir.getAbsolutePath() + "/" +
					REQUIRED_FILES.get(ConfigType.WEAPON_TYPE).concat(CFG_EXT);

			ret = om.readValue(
					new File(file), t.constructCollectionType(ArrayList.class,WeaponTemplate.class));

		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
		return ret;
	}

	/**Builds an array of WeaponNameTemplates used for the instantion of Weapons 
	 *
	 **/
	public ArrayList<WeaponNameTemplate> loadWeaponNameTemplate() throws ConfigurationException {

		ObjectMapper om = new ObjectMapper();

		ArrayList<WeaponNameTemplate> ret = null;

		try {

			TypeFactory t = TypeFactory.defaultInstance();

			String file = dir.getAbsolutePath() + "/" +
					REQUIRED_FILES.get(ConfigType.WEAPON_ADJ).concat(CFG_EXT);

			ret = om.readValue(
					new File(file), t.constructCollectionType(ArrayList.class,WeaponNameTemplate.class));

		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
		return ret;
	}
}