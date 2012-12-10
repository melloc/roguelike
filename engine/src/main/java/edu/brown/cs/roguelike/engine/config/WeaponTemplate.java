package edu.brown.cs.roguelike.engine.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.entities.DamageType;


/**
 * Intermediate Weapon Type Format
 * 
 * 
 * @author jte
 *
 */
public class WeaponTemplate {
	public final int tier;
	public final String name;
	public final DamageType damageType;


	@JsonCreator
	public WeaponTemplate(
			@JsonProperty("tier") int tier, 
			@JsonProperty("name") String name, 
			@JsonProperty("type") String type) {

			
		this.name = name;
		this.tier = tier;
		
		String t= type.toLowerCase();
		if (t.equals("slash")) { this.damageType = DamageType.SLASH; }
		else if (t.equals("blunt")) { this.damageType = DamageType.BLUNT; }
		else if (t.equals("pierce")) { this.damageType = DamageType.PIERCE; }
		else if (t.equals("magic")) { this.damageType = DamageType.MAGIC; }
		else if (t.equals("fire")) { this.damageType = DamageType.FIRE; }
		else if (t.equals("cold")) { this.damageType = DamageType.COLD; }
		else if (t.equals("light")) { this.damageType = DamageType.LIGHT; }
		else if (t.equals("dark")) { this.damageType = DamageType.DARK; }
		else if (t.equals("wind")) { this.damageType = DamageType.WIND; }
		else if (t.equals("earth")) { this.damageType = DamageType.EARTH; }
		else { this.damageType = DamageType.BLUNT; } 
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tier;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeaponTemplate other = (WeaponTemplate) obj;
		if (tier != other.tier)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
