package edu.brown.cs.roguelike.engine.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Intermediate Weapon Name Format
 * 
 * 
 * @author jte
 *
 */
public class WeaponNameTemplate {
	public final int tier;
	public final String[] prefixes;
	public final String[] materials;
	public final String[] suffixes;


	@JsonCreator
	public WeaponNameTemplate(
			@JsonProperty("tier") int tier, 
			@JsonProperty("prefixes") String[] prefixes, 
			@JsonProperty("materials") String[] materials, 
			@JsonProperty("suffixes") String[] suffixes) {
			
		this.prefixes = prefixes;
		this.materials = materials;
		this.suffixes = suffixes;
		this.tier = tier;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tier;
		result = prime * result + ((prefixes == null) ? 0 : prefixes.hashCode());
		result = prime * result + ((materials == null) ? 0 : materials.hashCode());
		result = prime * result + ((suffixes == null) ? 0 : suffixes.hashCode());

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
		WeaponNameTemplate other = (WeaponNameTemplate) obj;
		if (tier != other.tier)
			return false;
		if (prefixes == null) {
			if (other.prefixes != null)
				return false;
		} else if (!prefixes.equals(other.prefixes))
			return false;
		if (suffixes == null) {
			if (other.suffixes != null)
				return false;
		} else if (!suffixes.equals(other.suffixes))
			return false;
		if (materials == null) {
			if (other.materials != null)
				return false;
		} else if (!materials.equals(other.materials))
			return false;
		return true;
	}
	
	
}
