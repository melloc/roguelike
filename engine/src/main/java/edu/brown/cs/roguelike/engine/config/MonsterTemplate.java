package edu.brown.cs.roguelike.engine.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Intermediate monster data format
 * 
 * 
 * @author lelberty
 *
 */
public class MonsterTemplate {
	public final String name;
	public final char character;
	public final String color;
	public final int startHp;

	@JsonCreator
	public MonsterTemplate(
			@JsonProperty("name") String name, 
			@JsonProperty("char") char character,
			@JsonProperty("color") String color, 
			@JsonProperty("startHp") int startHp) {
		this.name = name;
		this.character = character;
		this.color = color;
		this.startHp = startHp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + startHp;
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
		MonsterTemplate other = (MonsterTemplate) obj;
		if (character != other.character)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startHp != other.startHp)
			return false;
		return true;
	}
	
	
}
