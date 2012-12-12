package edu.brown.cs.roguelike.engine.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.lanterna.terminal.Terminal.Color;


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
	public final Color color;
	public final int startHp;
	public final int attack;
	public final int defense;
	public final int tier;
	public final int moveCost;

	@JsonCreator
	public MonsterTemplate(
			@JsonProperty("name") String name, 
			@JsonProperty("char") char character,
			@JsonProperty("color") String color, 
			@JsonProperty("startHp") int startHp,
			@JsonProperty("attack") int attack,
			@JsonProperty("defense") int defense,
			@JsonProperty("tier") int tier,
			@JsonProperty("moveCost") int moveCost) {
		this.name = name;
		this.character = character;
		
		// parse color
		String c = color.toLowerCase();
		
		if (c.equals("black")) { this.color = Color.BLACK; }
		else if (c.equals("blue")) { this.color = Color.BLUE; }
		else if (c.equals("cyan")) { this.color = Color.CYAN; }
		else if (c.equals("green")) { this.color = Color.GREEN; }
		else if (c.equals("magenta")) { this.color = Color.MAGENTA; }
		else if (c.equals("red")) { this.color = Color.RED; }
		else if (c.equals("white")) { this.color = Color.WHITE; }
		else if (c.equals("yellow")) { this.color = Color.YELLOW; }
		else { this.color = Color.DEFAULT; } 

		this.startHp = startHp;
		this.attack = attack;
		this.defense = defense;
		this.tier = tier;
		this.moveCost = moveCost;
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
		
		if (attack != other.attack)
			return false;
		if (defense != other.defense)
			return false;
		if (tier != other.tier)
			return false;
		
		return true;
	}
	
	
}
